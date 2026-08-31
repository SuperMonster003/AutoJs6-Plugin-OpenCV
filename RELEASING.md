# Release guide

This repository prepares a GitHub Release from the exact APK bytes that pass the
OpenCV integrity and signing gates. The process produces five APKs, the verified
native-build provenance, a canonical SHA-256 manifest, and a fully rendered
Release description containing the same five APK hashes and a package-selection
table.

## Prerequisites

- Use JDK 21 and Python 3.
- Configure the ignored `sign.properties` with the trusted release identity.
- Update `VERSION_NAME` in `version.properties`.
- Add the matching newest `v<VERSION_NAME>` entry to every
  `.changelog/lang_*.json` source and regenerate the localized Markdown.
- Confirm that `.python\check_markdown.bat` succeeds.

The release preparer refuses unsigned output because its wrapper first runs
`:app:verifyOpenCvPublishableApks`.

## Prepare the release directory

From the repository root on Windows, run:

```bat
scripts\release\prepare-release.bat
```

The wrapper performs these steps in order:

1. Builds all debug and release ABI splits.
2. Verifies the native payload, ELF architecture, OpenCV provenance, Java API
   fingerprint, licenses, manifest metadata, and release signing identity.
3. Requires exactly the expected `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64`,
   and `universal` APK filenames for the current version.
4. Copies those exact APK bytes and the verified native-build provenance to
   `build/releases/v<VERSION_NAME>/`.
5. Generates `SHA256SUMS.txt` and renders `RELEASE_NOTES.md` from
   `.github/RELEASE_TEMPLATE.md` plus the current English changelog entry.

The output directory therefore contains eight files:

```text
autojs6-plugin-opencv-v<VERSION_NAME>-arm64-v8a.apk
autojs6-plugin-opencv-v<VERSION_NAME>-armeabi-v7a.apk
autojs6-plugin-opencv-v<VERSION_NAME>-x86.apk
autojs6-plugin-opencv-v<VERSION_NAME>-x86_64.apk
autojs6-plugin-opencv-v<VERSION_NAME>-universal.apk
opencv-native-4.8.0.provenance.json
SHA256SUMS.txt
RELEASE_NOTES.md
```

The preparer overwrites only these managed names. It stops if the destination
contains any unknown entry, so unrelated files are never silently removed or
replaced.

## Verify and publish

Recheck an existing prepared directory without writing it:

```powershell
python scripts/release/prepare_release.py --check
```

Before creating the GitHub Release:

1. Inspect `RELEASE_NOTES.md` and confirm its version and release date.
2. Confirm the five rows in its SHA-256 table match `SHA256SUMS.txt`, and the
   provenance hash in the notes matches `opencv-native-4.8.0.provenance.json`.
3. Create tag `v<VERSION_NAME>` from the intended commit.
4. Use `RELEASE_NOTES.md` as the Release body.
5. Upload the five APKs, `SHA256SUMS.txt`, and the provenance JSON as the seven
   Release assets.
6. Download the seven assets from the published Release and verify both the APK
   manifest and provenance hash independently before marking the Roadmap
   publication item complete.

The preparation scripts intentionally do not create tags, push commits, or
publish a GitHub Release. Those remote mutations remain an explicit maintainer
action.
