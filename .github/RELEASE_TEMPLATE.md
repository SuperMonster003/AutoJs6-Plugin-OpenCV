# AutoJs6 OpenCV Plugin {{VERSION_LABEL}}

Released on {{RELEASE_DATE}}.

## What's changed

{{CHANGELOG_BODY}}

## Choose the right APK

Every release contains five APKs. They have the same plugin code and differ only in the bundled native architectures.

| APK suffix | Choose it for |
|---|---|
| `arm64-v8a` | Most modern Android phones and tablets (64-bit ARM); this is the preferred small package for those devices |
| `armeabi-v7a` | Older 32-bit ARM devices |
| `x86_64` | 64-bit x86 emulators and the rare 64-bit x86 device |
| `x86` | 32-bit x86 emulators and the rare 32-bit x86 device |
| `universal` | Any supported device, an unknown device architecture, or AutoJs6 APK Builder output that must support multiple architectures; this package is the largest |

If you are unsure, download the `universal` APK. Install only one OpenCV plugin APK at a time. After an update or reinstall, fully exit and restart AutoJs6 before running image scripts.

## APK SHA-256

{{APK_SHA256_ROWS}}

The attached `SHA256SUMS.txt` contains the same five hashes in a machine-readable form. For example, verify a downloaded APK in PowerShell with:

```powershell
(Get-FileHash -Algorithm SHA256 .\autojs6-plugin-opencv-v{{VERSION}}-universal.apk).Hash.ToLowerInvariant()
```

On Linux or macOS, place the five APKs beside `SHA256SUMS.txt` and run:

```bash
sha256sum --check SHA256SUMS.txt
```

## Native build provenance

The attached `{{PROVENANCE_FILE}}` records the pinned upstream source, Android NDK and compiler versions, native AAR hash, per-ABI library hashes and Build IDs, and every ELF `PT_LOAD` alignment. Its own SHA-256 is:

```text
{{PROVENANCE_SHA256}}
```

Third parties can independently rebuild the native AAR and compare this record by following [NATIVE_BUILD.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/NATIVE_BUILD.md).

## Requirements

- AutoJs6 build 5237 or newer.
- Android 7.0 (API 24) or newer.
- A package architecture matching the device, unless the `universal` package is used.

Installation and troubleshooting details are available in the [README](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV#readme).
