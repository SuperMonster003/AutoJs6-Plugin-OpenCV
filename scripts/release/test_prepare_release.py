# -*- coding: utf-8 -*-

from __future__ import annotations

import importlib.util
import json
import sys
import tempfile
import unittest
from pathlib import Path


MODULE_PATH = Path(__file__).with_name("prepare_release.py")
SPEC = importlib.util.spec_from_file_location("prepare_release", MODULE_PATH)
assert SPEC is not None and SPEC.loader is not None
prepare_release = importlib.util.module_from_spec(SPEC)
sys.modules[SPEC.name] = prepare_release
SPEC.loader.exec_module(prepare_release)


class PrepareReleaseTest(unittest.TestCase):

    @classmethod
    def setUpClass(cls) -> None:
        cls.root = MODULE_PATH.resolve().parents[2]
        cls.version = prepare_release.current_version(cls.root)

    def create_source(self, directory: Path) -> dict[str, bytes]:
        payloads: dict[str, bytes] = {}
        for architecture, name in prepare_release.expected_apk_names(self.version).items():
            payload = f"test-apk:{architecture}\n".encode("utf-8")
            (directory / name).write_bytes(payload)
            payloads[name] = payload
        return payloads

    def prepare(self, source: Path, output: Path) -> tuple[str, list, dict[str, Path | bytes]]:
        template = self.root / ".github" / "RELEASE_TEMPLATE.md"
        generated = prepare_release.expected_outputs(self.root, source, template)
        prepare_release.write_outputs(output, generated[2])
        return generated

    def test_generates_exact_apks_checksums_and_fully_rendered_notes(self) -> None:
        with tempfile.TemporaryDirectory() as temporary:
            workspace = Path(temporary)
            source = workspace / "source"
            output = workspace / "output"
            source.mkdir()
            payloads = self.create_source(source)

            version, artifacts, outputs = self.prepare(source, output)

            self.assertEqual(self.version, version)
            self.assertEqual(list(prepare_release.ARCHITECTURES), [item.architecture for item in artifacts])
            self.assertEqual(set(payloads) | set(prepare_release.RELEASE_SUPPORT_FILES), set(outputs))
            self.assertEqual(set(outputs), {path.name for path in output.iterdir()})
            for name, payload in payloads.items():
                self.assertEqual(payload, (output / name).read_bytes())

            sums = (output / "SHA256SUMS.txt").read_text(encoding="utf-8")
            notes = (output / "RELEASE_NOTES.md").read_text(encoding="utf-8")
            self.assertNotIn("{{", notes)
            self.assertIn(f"# AutoJs6 OpenCV Plugin v{self.version}", notes)
            provenance = prepare_release.collect_native_provenance(self.root)
            self.assertEqual(provenance.path.read_bytes(), (output / provenance.path.name).read_bytes())
            self.assertIn(provenance.sha256, notes)
            for artifact in artifacts:
                self.assertIn(f"{artifact.sha256}  {artifact.path.name}\n", sums)
                self.assertIn(f"| `{artifact.path.name}` | `{artifact.sha256}` |", notes)

            prepare_release.check_outputs(output, outputs)

    def test_rejects_missing_or_unexpected_apks(self) -> None:
        with tempfile.TemporaryDirectory() as temporary:
            source = Path(temporary)
            names = prepare_release.expected_apk_names(self.version)
            for name in names.values():
                (source / name).write_bytes(b"apk")
            (source / names["x86"]).unlink()
            with self.assertRaisesRegex(prepare_release.ReleasePreparationError, "missing="):
                prepare_release.collect_apks(source, self.version)

            (source / names["x86"]).write_bytes(b"apk")
            (source / "wrong-version.apk").write_bytes(b"apk")
            with self.assertRaisesRegex(prepare_release.ReleasePreparationError, "unexpected="):
                prepare_release.collect_apks(source, self.version)

    def test_rejects_provenance_whose_native_aar_hash_drifted(self) -> None:
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            libraries = root / "libs"
            libraries.mkdir()
            native_aar = root / prepare_release.NATIVE_AAR_RELATIVE_PATH
            native_aar.write_bytes(b"original-aar")
            provenance = {
                "schemaVersion": 2,
                "build": {"elfLoadSegmentAlignmentBytes": prepare_release.MIN_ELF_LOAD_ALIGNMENT},
                "artifact": {
                    "path": prepare_release.NATIVE_AAR_RELATIVE_PATH.as_posix(),
                    "sha256": prepare_release.sha256_file(native_aar),
                    "abis": {
                        architecture: {"loadSegmentAlignments": [prepare_release.MIN_ELF_LOAD_ALIGNMENT]}
                        for architecture in prepare_release.NATIVE_ARCHITECTURES
                    },
                },
            }
            (libraries / prepare_release.NATIVE_PROVENANCE_FILE).write_text(
                json.dumps(provenance),
                encoding="utf-8",
            )
            native_aar.write_bytes(b"tampered-aar")

            with self.assertRaisesRegex(prepare_release.ReleasePreparationError, "AAR SHA-256 mismatch"):
                prepare_release.collect_native_provenance(root)

    def test_check_detects_tampering(self) -> None:
        with tempfile.TemporaryDirectory() as temporary:
            workspace = Path(temporary)
            source = workspace / "source"
            output = workspace / "output"
            source.mkdir()
            self.create_source(source)
            _, _, outputs = self.prepare(source, output)

            (output / "RELEASE_NOTES.md").write_text("tampered\n", encoding="utf-8")
            with self.assertRaisesRegex(prepare_release.ReleasePreparationError, "differs"):
                prepare_release.check_outputs(output, outputs)

    def test_rejects_unmanaged_output_entries(self) -> None:
        with tempfile.TemporaryDirectory() as temporary:
            workspace = Path(temporary)
            source = workspace / "source"
            output = workspace / "output"
            source.mkdir()
            output.mkdir()
            self.create_source(source)
            _, _, outputs = prepare_release.expected_outputs(
                self.root,
                source,
                self.root / ".github" / "RELEASE_TEMPLATE.md",
            )
            (output / "do-not-overwrite.txt").write_text("user data", encoding="utf-8")
            with self.assertRaisesRegex(prepare_release.ReleasePreparationError, "unmanaged"):
                prepare_release.write_outputs(output, outputs)


if __name__ == "__main__":
    unittest.main()
