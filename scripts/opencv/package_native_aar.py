#!/usr/bin/env python3
"""Create the deterministic, native-only OpenCV AAR used by the plugin."""

from __future__ import annotations

import argparse
import hashlib
import io
import json
import re
import subprocess
import zipfile
from pathlib import Path


ABIS = ("arm64-v8a", "armeabi-v7a", "x86", "x86_64")
LIBRARY_NAME = "libopencv_java4.so"
OPENCV_VERSION = "4.8.0"
OPENCV_TAG_OBJECT = "53296de62872b5e7d042ddffb49679fbdcca99f6"
OPENCV_COMMIT = "f9a59f2592993d3dcc080e495f4f5e02dd8ec7ef"
NDK_VERSION = "26.1.10909125"
MIN_SDK = 24


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        while chunk := stream.read(1024 * 1024):
            digest.update(chunk)
    return digest.hexdigest()


def command_output(*command: str | Path) -> str:
    return subprocess.check_output(
        [str(part) for part in command],
        stderr=subprocess.STDOUT,
        text=True,
        encoding="utf-8",
    )


def zip_info(name: str) -> zipfile.ZipInfo:
    info = zipfile.ZipInfo(name, date_time=(1980, 2, 1, 0, 0, 0))
    info.compress_type = zipfile.ZIP_DEFLATED
    info.create_system = 3
    info.external_attr = 0o100664 << 16
    return info


def add_bytes(archive: zipfile.ZipFile, name: str, payload: bytes) -> None:
    archive.writestr(zip_info(name), payload, compresslevel=9)


def empty_jar() -> bytes:
    result = io.BytesIO()
    with zipfile.ZipFile(result, "w"):
        pass
    return result.getvalue()


def inspect_library(readelf: Path, library: Path) -> dict[str, str | int | list[str]]:
    notes = command_output(readelf, "--notes", library)
    dynamic = command_output(readelf, "--dynamic", library)
    ident_match = re.search(
        r"description data:\s+((?:[0-9a-f]{2}\s*)+)",
        notes,
        flags=re.IGNORECASE,
    )
    if ident_match is None:
        raise RuntimeError(f"Android ident is missing from {library}")
    ident = bytes.fromhex(ident_match.group(1))
    if len(ident) < 68:
        raise RuntimeError(f"Android ident is truncated in {library}")
    api_level = int.from_bytes(ident[0:4], byteorder="little")
    ndk_release = ident[4:68].split(b"\0", 1)[0].decode("ascii")
    if api_level != MIN_SDK:
        raise RuntimeError(f"Expected Android API {MIN_SDK} in {library}, got {api_level}")
    if ndk_release != "r26b":
        raise RuntimeError(f"Expected an r26b Android ident in {library}, got {ndk_release}")
    build_id_match = re.search(r"Build ID:\s*([0-9a-f]+)", notes, flags=re.IGNORECASE)
    if build_id_match is None:
        raise RuntimeError(f"GNU build ID is missing from {library}")
    needed_libraries = re.findall(r"Shared library: \[([^]]+)]", dynamic)
    if "libc++_shared.so" not in needed_libraries:
        raise RuntimeError(f"OpenCV does not depend on libc++_shared.so: {library}")
    return {
        "sha256": sha256(library),
        "sizeBytes": library.stat().st_size,
        "gnuBuildId": build_id_match.group(1).lower(),
        "androidApiLevel": api_level,
        "androidNdkRelease": ndk_release,
        "neededLibraries": needed_libraries,
    }


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--opencv-sdk", required=True, type=Path)
    parser.add_argument("--ndk", required=True, type=Path)
    parser.add_argument("--output", required=True, type=Path)
    parser.add_argument("--provenance", required=True, type=Path)
    args = parser.parse_args()

    ndk_properties = (args.ndk / "source.properties").read_text(encoding="utf-8")
    if f"Pkg.Revision = {NDK_VERSION}" not in ndk_properties:
        raise RuntimeError(f"Expected Android NDK {NDK_VERSION}: {args.ndk}")
    readelf = (
        args.ndk
        / "toolchains"
        / "llvm"
        / "prebuilt"
        / "windows-x86_64"
        / "bin"
        / "llvm-readelf.exe"
    )
    if not readelf.is_file():
        raise RuntimeError(f"llvm-readelf is missing: {readelf}")
    clang = readelf.with_name("clang.exe")
    compiler_ident = command_output(clang, "--version").splitlines()[0]
    if "Android (10552028, based on r487747d) clang version 17.0.2" not in compiler_ident:
        raise RuntimeError(f"Unexpected Android NDK compiler: {compiler_ident}")

    libraries: dict[str, Path] = {}
    abi_metadata: dict[str, dict[str, str | int | list[str]]] = {}
    for abi in ABIS:
        library = args.opencv_sdk / "sdk" / "native" / "libs" / abi / LIBRARY_NAME
        if not library.is_file():
            raise RuntimeError(f"OpenCV SDK is missing {library}")
        libraries[abi] = library
        abi_metadata[abi] = inspect_library(readelf, library)

    manifest = f"""<?xml version=\"1.0\" encoding=\"utf-8\"?>
<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\"
    package=\"org.opencv\">
    <uses-sdk android:minSdkVersion=\"{MIN_SDK}\" android:targetSdkVersion=\"36\" />
</manifest>
""".encode()
    aar_metadata = (
        "aarFormatVersion=1.0\n"
        "aarMetadataVersion=1.0\n"
        f"minCompileSdk={MIN_SDK}\n"
    ).encode()

    args.output.parent.mkdir(parents=True, exist_ok=True)
    temporary_output = args.output.with_suffix(args.output.suffix + ".tmp")
    with zipfile.ZipFile(temporary_output, "w", allowZip64=True) as archive:
        add_bytes(archive, "AndroidManifest.xml", manifest)
        add_bytes(archive, "classes.jar", empty_jar())
        for abi in ABIS:
            add_bytes(
                archive,
                f"jni/{abi}/{LIBRARY_NAME}",
                libraries[abi].read_bytes(),
            )
        add_bytes(
            archive,
            "META-INF/com/android/build/gradle/aar-metadata.properties",
            aar_metadata,
        )
    temporary_output.replace(args.output)

    provenance = {
        "schemaVersion": 1,
        "opencv": {
            "version": OPENCV_VERSION,
            "repository": "https://github.com/opencv/opencv.git",
            "tag": OPENCV_VERSION,
            "tagObjectSha": OPENCV_TAG_OBJECT,
            "commitSha": OPENCV_COMMIT,
        },
        "build": {
            "androidNdkVersion": NDK_VERSION,
            "androidNdkRelease": sorted(
                {metadata["androidNdkRelease"] for metadata in abi_metadata.values()}
            )[0],
            "androidMinSdk": MIN_SDK,
            "cmakeVersion": "3.22.1",
            "compilerIdent": compiler_ident,
            "stl": "c++_shared",
            "libcxxSharedPackaged": False,
        },
        "artifact": {
            "path": "libs/opencv-native-4.8.0.aar",
            "sha256": sha256(args.output),
            "sizeBytes": args.output.stat().st_size,
            "abis": abi_metadata,
        },
    }
    args.provenance.write_text(
        json.dumps(provenance, indent=2, ensure_ascii=True) + "\n",
        encoding="utf-8",
        newline="\n",
    )
    print(f"Wrote {args.output} ({provenance['artifact']['sha256']})")
    print(f"Wrote {args.provenance}")


if __name__ == "__main__":
    main()
