# Third-party notices

This application redistributes native OpenCV 4.8.0 binaries for
`arm64-v8a`, `armeabi-v7a`, `x86`, and `x86_64`. The binaries are rebuilt from
the unmodified official source at the pinned commit below with Android NDK
26.1.10909125 and native API level 24.

- Project: OpenCV 4.8.0
- Source: <https://github.com/opencv/opencv/tree/4.8.0>
- Source commit: `f9a59f2592993d3dcc080e495f4f5e02dd8ec7ef`
- Official Android SDK archive: <https://github.com/opencv/opencv/releases/download/4.8.0/opencv-4.8.0-android-sdk.zip>
- Archive SHA-256: `e415d976549be7aa56a909cedb7d9b5b7fd24dbdd0eabee94de0f004c52ddc7c`
- License: Apache License 2.0

The reproducible build procedure and native artifact hashes are recorded in
[`NATIVE_BUILD.md`](NATIVE_BUILD.md) and
[`libs/opencv-native-4.8.0.provenance.json`](libs/opencv-native-4.8.0.provenance.json).

The complete license and attribution texts are packaged in every plugin APK at
`assets/licenses/opencv-4.8.0/` and are also available in the source tree at
[`app/src/main/assets/licenses/opencv-4.8.0/`](app/src/main/assets/licenses/opencv-4.8.0/).

The packaged notices cover OpenCV and the statically linked components present
in the rebuilt Android binaries: ADE, Android cpufeatures, Carotene (ARM ABIs),
FlatBuffers, Intel IPP and IPP IW (x86), Intel ITT, libjpeg-turbo, OpenEXR,
OpenJPEG, libpng, libtiff, libwebp, Protocol Buffers, quirc, SoftFloat, oneTBB,
and the Torch importer.

The plugin does not package `libc++_shared.so`; that runtime is supplied by the
AutoJs6 host. FFmpeg, GStreamer, Qt, Jasper, libspng, OpenCL headers, and Python
wheel notices are not included because they are not part of this Android SDK
native payload.
