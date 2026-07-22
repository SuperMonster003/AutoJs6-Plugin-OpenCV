# OpenCV native build provenance

The plugin's `libopencv_java4.so` files are rebuilt from the unmodified
OpenCV 4.8.0 source tree. They are not copied from the OpenCV 4.8.0 Android SDK
binary release.

## Pinned inputs

- Repository: <https://github.com/opencv/opencv.git>
- Tag: `4.8.0`
- Tag object: `53296de62872b5e7d042ddffb49679fbdcca99f6`
- Source commit: `f9a59f2592993d3dcc080e495f4f5e02dd8ec7ef`
- Android NDK: `26.1.10909125` (`r26b`, Clang 17.0.2)
- Android native API level: `24`
- Android SDK platform used by OpenCV's generated library project: `32`
- Android SDK CMake: `3.22.1`
- JDK used for OpenCV Java binding discovery: `17`
- Python used by OpenCV's Java binding generator: `3.10` or newer
- ABIs: `arm64-v8a`, `armeabi-v7a`, `x86`, and `x86_64`

The build uses OpenCV's official CMake project with every pinned option in
[`scripts/opencv/build-native-aar.ps1`](scripts/opencv/build-native-aar.ps1).
Each ABI is configured independently and only the `opencv_java` target is
built. This preserves Java/JNI wrapper generation while avoiding the unrelated
auxiliary Android AAR build. OpenCV links against
`c++_shared`, but the packaging step omits `libc++_shared.so`; the compatible
AutoJs6 host owns and preloads that single process-wide runtime.

## Rebuild

Install the pinned NDK and CMake versions in an Android SDK, then run on
Windows PowerShell:

```powershell
.\scripts\opencv\build-native-aar.ps1 `
    -AndroidSdk E:\.android\sdk `
    -JavaHome E:\.java\jdk-17.0.4 `
    -PythonExecutable C:\Python310\python.exe
```

The script verifies the OpenCV commit and tool versions, builds all four ABIs,
and deterministically packages only `libopencv_java4.so` into
`libs/opencv-native-4.8.0.aar`. It writes the AAR and per-ABI SHA-256 hashes,
GNU Build IDs, Android API level, NDK release ident, and compiler ident to
[`libs/opencv-native-4.8.0.provenance.json`](libs/opencv-native-4.8.0.provenance.json).

Run the normal release gate after rebuilding:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

The gate rejects unexpected ABIs or native libraries, an Android NDK ident
other than `r26b`, a native API level other than 24, provenance/hash drift,
and missing contract v2 NDK metadata in either the application or service.

## 原生构建来源

插件中的 4 个 `libopencv_java4.so` 均从未经修改的 OpenCV 4.8.0 官方源码
提交 `f9a59f2592993d3dcc080e495f4f5e02dd8ec7ef` 重新构建, 使用 Android NDK
`26.1.10909125`, API 24, CMake 3.22.1 和 JDK 17. 上述 PowerShell 脚本会校验
输入版本, 通过 OpenCV 官方 CMake 工程仅构建 `opencv_java` 目标, 生成仅包含
OpenCV JNI 的 AAR,
并将每个 ABI 的哈希, Build ID, Android ident 和编译器标识写入 provenance JSON.
插件不会携带 `libc++_shared.so`, 该进程级运行时由兼容的 AutoJs6 宿主统一提供.
