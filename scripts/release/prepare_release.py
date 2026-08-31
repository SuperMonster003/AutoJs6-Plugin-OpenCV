# -*- coding: utf-8 -*-
"""Prepare the five OpenCV plugin APKs and deterministic GitHub Release material.

The script never builds or signs APKs. The one-click Windows wrapper runs the
publishable Gradle gate first, then calls this script with the verified release
outputs. Direct usage is useful for tests and for checking an already prepared
directory:

  python scripts/release/prepare_release.py
  python scripts/release/prepare_release.py --check

Exit protocol: prints RELEASE_OK on success or RELEASE_ERROR on failure.
"""

from __future__ import annotations

import argparse
import hashlib
import json
import os
import re
import shutil
import sys
import tempfile
from dataclasses import dataclass
from pathlib import Path
from typing import Any


PROJECT_SLUG = "autojs6-plugin-opencv"
ARCHITECTURES = ("arm64-v8a", "armeabi-v7a", "x86", "x86_64", "universal")
NATIVE_ARCHITECTURES = ARCHITECTURES[:-1]
NATIVE_AAR_RELATIVE_PATH = Path("libs/opencv-native-4.8.0.aar")
NATIVE_PROVENANCE_FILE = "opencv-native-4.8.0.provenance.json"
MIN_ELF_LOAD_ALIGNMENT = 16 * 1024
CHANGELOG_CATEGORIES = ("hint", "feature", "fix", "improvement", "dependency")
TEMPLATE_TOKENS = frozenset(
    {
        "VERSION_LABEL",
        "VERSION",
        "RELEASE_DATE",
        "CHANGELOG_BODY",
        "APK_SHA256_ROWS",
        "PROVENANCE_FILE",
        "PROVENANCE_SHA256",
    }
)
TOKEN_PATTERN = re.compile(r"\{\{([A-Z][A-Z0-9_]*)\}\}")
VERSION_PATTERN = re.compile(r"[0-9A-Za-z][0-9A-Za-z.+_-]*\Z")
SHA256_PATTERN = re.compile(r"[0-9a-f]{64}\Z")
RELEASE_SUPPORT_FILES = (NATIVE_PROVENANCE_FILE, "SHA256SUMS.txt", "RELEASE_NOTES.md")


class ReleasePreparationError(Exception):
    """Raised when release inputs or generated material are unsafe or inconsistent."""


@dataclass(frozen=True)
class ApkArtifact:
    architecture: str
    path: Path
    sha256: str


@dataclass(frozen=True)
class ProvenanceArtifact:
    path: Path
    sha256: str


def require(condition: bool, message: str) -> None:
    if not condition:
        raise ReleasePreparationError(message)


def load_text(path: Path) -> str:
    require(path.is_file(), f"Missing file: {path}")
    require(not path.is_symlink(), f"Refusing to read symlink: {path}")
    try:
        return path.read_text(encoding="utf-8")
    except UnicodeDecodeError as error:
        raise ReleasePreparationError(f"Invalid UTF-8 in {path}: {error}") from None


def load_json(path: Path) -> dict[str, Any]:
    try:
        content = json.loads(load_text(path))
    except json.JSONDecodeError as error:
        raise ReleasePreparationError(f"Invalid JSON in {path}: {error}") from None
    require(isinstance(content, dict), f"JSON root must be an object: {path}")
    return content


def read_properties(path: Path) -> dict[str, str]:
    properties: dict[str, str] = {}
    for line in load_text(path).splitlines():
        stripped = line.strip()
        if not stripped or stripped.startswith("#") or "=" not in stripped:
            continue
        key, _, value = stripped.partition("=")
        key = key.strip()
        require(key not in properties, f"Duplicate property {key!r} in {path}")
        properties[key] = value.strip()
    return properties


def current_version(root: Path) -> str:
    version = read_properties(root / "version.properties").get("VERSION_NAME", "")
    require(bool(VERSION_PATTERN.fullmatch(version)), f"Invalid VERSION_NAME: {version!r}")
    return version


def expected_apk_names(version: str) -> dict[str, str]:
    return {
        architecture: f"{PROJECT_SLUG}-v{version}-{architecture}.apk"
        for architecture in ARCHITECTURES
    }


def sha256_file(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(chunk)
    value = digest.hexdigest()
    require(bool(SHA256_PATTERN.fullmatch(value)), f"Invalid SHA-256 produced for {path}")
    return value


def collect_apks(source_directory: Path, version: str) -> list[ApkArtifact]:
    require(source_directory.is_dir(), f"APK source directory does not exist: {source_directory}")
    require(not source_directory.is_symlink(), f"Refusing to use a symlink APK directory: {source_directory}")

    expected = expected_apk_names(version)
    actual_names = {path.name for path in source_directory.iterdir() if path.is_file() and path.suffix == ".apk"}
    expected_names = set(expected.values())
    missing = sorted(expected_names - actual_names)
    unexpected = sorted(actual_names - expected_names)
    require(
        not missing and not unexpected,
        f"Expected exactly five v{version} APKs; missing={missing}, unexpected={unexpected}",
    )

    artifacts: list[ApkArtifact] = []
    for architecture in ARCHITECTURES:
        path = source_directory / expected[architecture]
        require(path.is_file() and not path.is_symlink(), f"Invalid APK input: {path}")
        require(path.stat().st_size > 0, f"Empty APK input: {path}")
        artifacts.append(ApkArtifact(architecture, path, sha256_file(path)))
    return artifacts


def collect_native_provenance(root: Path) -> ProvenanceArtifact:
    provenance_path = root / "libs" / NATIVE_PROVENANCE_FILE
    require(provenance_path.is_file(), f"Missing native provenance: {provenance_path}")
    require(not provenance_path.is_symlink(), f"Refusing to read symlink: {provenance_path}")
    require(provenance_path.stat().st_size > 0, f"Empty native provenance: {provenance_path}")

    provenance = load_json(provenance_path)
    require(provenance.get("schemaVersion") == 2, "Native provenance must use schemaVersion 2")
    build = provenance.get("build")
    require(isinstance(build, dict), "Native provenance has no build object")
    require(
        build.get("elfLoadSegmentAlignmentBytes") == MIN_ELF_LOAD_ALIGNMENT,
        f"Native provenance does not declare {MIN_ELF_LOAD_ALIGNMENT}-byte ELF alignment",
    )

    artifact = provenance.get("artifact")
    require(isinstance(artifact, dict), "Native provenance has no artifact object")
    expected_aar_path = NATIVE_AAR_RELATIVE_PATH.as_posix()
    require(artifact.get("path") == expected_aar_path, f"Unexpected native AAR path: {artifact.get('path')!r}")
    declared_aar_sha256 = artifact.get("sha256")
    require(
        isinstance(declared_aar_sha256, str) and SHA256_PATTERN.fullmatch(declared_aar_sha256) is not None,
        "Native provenance has an invalid AAR SHA-256",
    )
    native_aar = root / NATIVE_AAR_RELATIVE_PATH
    require(native_aar.is_file() and not native_aar.is_symlink(), f"Missing native AAR: {native_aar}")
    require(native_aar.stat().st_size > 0, f"Empty native AAR: {native_aar}")
    require(sha256_file(native_aar) == declared_aar_sha256, "Native provenance AAR SHA-256 mismatch")

    abi_metadata = artifact.get("abis")
    require(isinstance(abi_metadata, dict), "Native provenance has no ABI metadata")
    require(set(abi_metadata) == set(NATIVE_ARCHITECTURES), "Native provenance ABI set is incomplete or unexpected")
    for architecture in NATIVE_ARCHITECTURES:
        metadata = abi_metadata.get(architecture)
        require(isinstance(metadata, dict), f"Native provenance is missing {architecture}")
        alignments = metadata.get("loadSegmentAlignments")
        require(
            isinstance(alignments, list)
            and bool(alignments)
            and all(isinstance(value, int) and value >= MIN_ELF_LOAD_ALIGNMENT for value in alignments),
            f"Native provenance contains invalid PT_LOAD alignment for {architecture}",
        )

    return ProvenanceArtifact(provenance_path, sha256_file(provenance_path))


def changelog_body(root: Path, version: str) -> tuple[str, str]:
    changelog_path = root / ".changelog" / "lang_en.json"
    changelog = load_json(changelog_path)
    data = changelog.get("$data")
    require(isinstance(data, dict), f"Missing $data object in {changelog_path}")
    version_label = f"v{version}"
    entry = data.get(version_label)
    require(isinstance(entry, dict), f"Missing {version_label} in {changelog_path}")
    release_date = entry.get("released_date")
    require(
        isinstance(release_date, str) and re.fullmatch(r"\d{4}/\d{2}/\d{2}", release_date) is not None,
        f"Invalid released_date for {version_label} in {changelog_path}",
    )

    lines: list[str] = []
    for category in CHANGELOG_CATEGORIES:
        items = entry.get(category, [])
        if not items:
            continue
        require(isinstance(items, list), f"Changelog category {category!r} must be a list")
        label = changelog.get(f"changelog_label_{category}")
        require(isinstance(label, str) and label, f"Missing changelog label for {category!r}")
        for item in items:
            require(isinstance(item, str) and item.strip(), f"Invalid {category!r} changelog item")
            lines.append(f"- **{label}:** {item}")
    require(bool(lines), f"No release notes found for {version_label}")
    return release_date, "\n".join(lines)


def checksum_rows(artifacts: list[ApkArtifact]) -> str:
    lines = ["| APK | SHA-256 |", "|---|---|"]
    lines.extend(f"| `{artifact.path.name}` | `{artifact.sha256}` |" for artifact in artifacts)
    return "\n".join(lines)


def checksum_manifest(artifacts: list[ApkArtifact]) -> str:
    return "".join(f"{artifact.sha256}  {artifact.path.name}\n" for artifact in artifacts)


def render_release_notes(
    root: Path,
    template_path: Path,
    version: str,
    artifacts: list[ApkArtifact],
    provenance: ProvenanceArtifact,
) -> str:
    template = load_text(template_path)
    discovered_tokens = frozenset(TOKEN_PATTERN.findall(template))
    missing = sorted(TEMPLATE_TOKENS - discovered_tokens)
    unexpected = sorted(discovered_tokens - TEMPLATE_TOKENS)
    require(
        not missing and not unexpected,
        f"Release template token mismatch; missing={missing}, unexpected={unexpected}",
    )
    release_date, body = changelog_body(root, version)
    values = {
        "VERSION_LABEL": f"v{version}",
        "VERSION": version,
        "RELEASE_DATE": release_date,
        "CHANGELOG_BODY": body,
        "APK_SHA256_ROWS": checksum_rows(artifacts),
        "PROVENANCE_FILE": provenance.path.name,
        "PROVENANCE_SHA256": provenance.sha256,
    }
    rendered = TOKEN_PATTERN.sub(lambda match: values[match.group(1)], template)
    require(TOKEN_PATTERN.search(rendered) is None, "Unresolved token remains in rendered release notes")
    return rendered.rstrip() + "\n"


def expected_outputs(
    root: Path,
    source_directory: Path,
    template_path: Path,
) -> tuple[str, list[ApkArtifact], dict[str, Path | bytes]]:
    version = current_version(root)
    artifacts = collect_apks(source_directory, version)
    provenance = collect_native_provenance(root)
    notes = render_release_notes(root, template_path, version, artifacts, provenance)
    outputs: dict[str, Path | bytes] = {artifact.path.name: artifact.path for artifact in artifacts}
    outputs[provenance.path.name] = provenance.path
    outputs["SHA256SUMS.txt"] = checksum_manifest(artifacts).encode("utf-8")
    outputs["RELEASE_NOTES.md"] = notes.encode("utf-8")
    return version, artifacts, outputs


def validate_output_directory(output_directory: Path, allowed_names: set[str]) -> None:
    if not output_directory.exists():
        return
    require(output_directory.is_dir(), f"Release output is not a directory: {output_directory}")
    require(not output_directory.is_symlink(), f"Refusing to use a symlink output directory: {output_directory}")
    unexpected = sorted(path.name for path in output_directory.iterdir() if path.name not in allowed_names)
    require(not unexpected, f"Release output contains unmanaged entries: {unexpected}")


def write_outputs(output_directory: Path, outputs: dict[str, Path | bytes]) -> None:
    allowed_names = set(outputs)
    validate_output_directory(output_directory, allowed_names)
    output_directory.parent.mkdir(parents=True, exist_ok=True)
    with tempfile.TemporaryDirectory(prefix=".opencv-release-", dir=output_directory.parent) as temporary:
        staging = Path(temporary)
        for name, content in outputs.items():
            destination = staging / name
            if isinstance(content, Path):
                shutil.copy2(content, destination)
            else:
                destination.write_bytes(content)
        output_directory.mkdir(parents=True, exist_ok=True)
        for name in outputs:
            os.replace(staging / name, output_directory / name)


def check_outputs(output_directory: Path, outputs: dict[str, Path | bytes]) -> None:
    require(output_directory.is_dir(), f"Release output directory does not exist: {output_directory}")
    allowed_names = set(outputs)
    validate_output_directory(output_directory, allowed_names)
    actual_names = {path.name for path in output_directory.iterdir() if path.is_file()}
    missing = sorted(allowed_names - actual_names)
    require(not missing, f"Release output is missing files: {missing}")
    stale: list[str] = []
    for name, content in outputs.items():
        destination = output_directory / name
        if isinstance(content, Path):
            if destination.stat().st_size != content.stat().st_size or sha256_file(destination) != sha256_file(content):
                stale.append(name)
        elif destination.read_bytes() != content:
            stale.append(name)
    require(not stale, f"Release output differs from verified inputs: {stale}")


def parse_arguments(argv: list[str] | None) -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Prepare verified OpenCV plugin GitHub Release material")
    parser.add_argument("--check", action="store_true", help="verify an existing output directory without writing")
    parser.add_argument("--source", type=Path, default=None, help="directory containing the five release APKs")
    parser.add_argument("--output", type=Path, default=None, help="destination for APKs, hashes, and release notes")
    parser.add_argument("--template", type=Path, default=None, help="release notes template path")
    parser.add_argument("--root", type=Path, default=None, help=argparse.SUPPRESS)
    return parser.parse_args(argv)


def main(argv: list[str] | None = None) -> int:
    arguments = parse_arguments(argv)
    root = (arguments.root or Path(__file__).resolve().parents[2]).resolve()
    source_directory = (arguments.source or root / "app" / "build" / "outputs" / "apk" / "release").resolve()
    template_path = (arguments.template or root / ".github" / "RELEASE_TEMPLATE.md").resolve()
    try:
        version = current_version(root)
        output_directory = (arguments.output or root / "build" / "releases" / f"v{version}").resolve()
        version, artifacts, outputs = expected_outputs(root, source_directory, template_path)
        if arguments.check:
            check_outputs(output_directory, outputs)
        else:
            write_outputs(output_directory, outputs)
    except (OSError, ReleasePreparationError) as error:
        print(f"RELEASE_ERROR {error}")
        return 1
    mode = "check" if arguments.check else "write"
    print(f"RELEASE_OK version=v{version} apks={len(artifacts)} mode={mode} output={output_directory}")
    return 0


if __name__ == "__main__":
    sys.exit(main())
