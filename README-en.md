# AutoJs6 OpenCV Plugin

OpenCV 4.8.0 native runtime plugin for AutoJs6. This project implements the compatibility-first extraction phase: the AutoJs6 host retains the OpenCV Java API, while this plugin supplies `libopencv_java4.so` for each ABI.

## Plugin contract

- Application ID: `io.github.supermonster003.autojs6.plugin.opencv`
- Plugin ID: `opencv`
- Engine: `opencv`
- Variant: `4.8.0`
- Contract version: `1`
- Required host version code: `5236`
- Native library: `opencv_java4`
- Java API SHA-256: `340976552fda3cce525021f0b072427cabf0aa1c786fb80cfc4a3a8105d90b3f`

The same `OpenCvPluginInfoService` responds to both actions:

- `org.autojs.plugin.INFO`
- `org.autojs.plugin.OPENCV`

Both use the `opencv-runtime` category. The Binder interface is `IPluginInfoProvider` from common-plugin-api.

## ABIs

The project produces these APKs:

- `arm64-v8a`
- `armeabi-v7a`
- `x86`
- `x86_64`
- `universal`

`PluginInfo.supportedAbis` is derived from the OpenCV native library entries in the installed APK. A single-ABI APK reports only its ABI, while the universal APK reports all four.

Install the universal plugin APK when APK Builder must produce applications for multiple ABIs. A single-ABI plugin can supply OpenCV only for that ABI.

## Build and verification

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

To build APKs only:

```powershell
.\gradlew.bat :app:assembleDebug
```

`verifyOpenCvApks` checks the debug and release single-ABI and universal APKs, including native inventory, ELF architecture, payload hashes, the Java API fingerprint, and the absence of duplicate OpenCV Java classes.

Before publishing, configure a trusted signing identity in the ignored `sign.properties` file and run:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

Without `sign.properties`, payloads can still be built and verified, but the generated release APKs are unsigned and must not be published.

## Runtime behavior

The plugin has no standalone user interface. Once installed and enabled, AutoJs6 discovers the service and loads the native library that matches its retained Java API. A loaded native library normally shares the host process lifetime, so restart AutoJs6 after updating the plugin.

The plugin carries only `libopencv_java4.so`. AutoJs6 supplies and preloads the process-wide `libc++_shared.so` dependency so that one process uses one C++ runtime.

Install only trusted builds. Before loading the native library, the host verifies the plugin signature, OpenCV version, contract version, and Java API SHA-256. Official releases should use a signing identity recognized by the host.

## License

Project code is licensed under the [Mozilla Public License 2.0](LICENSE). See [THIRD_PARTY_NOTICES.md](THIRD_PARTY_NOTICES.md) for the licenses of OpenCV 4.8.0 and its statically linked third-party components. The complete license texts are packaged in every APK at `assets/licenses/opencv-4.8.0/`.
