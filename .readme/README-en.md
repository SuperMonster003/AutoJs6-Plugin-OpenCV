<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-opencv-ic-launcher" border="0" width="128" />
  </p>

  <p>OpenCV 4.8.0 native runtime plugin for AutoJs6</p>

  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases"><img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-OpenCV?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/issues"><img alt="GitHub closed issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-OpenCV?color=A24232&label=Issues"/></a>
    <br>
    <a href="https://developer.android.com/studio/archive"><img alt="Android Studio" src="https://img.shields.io/badge/Android%20Studio-2023.3+-B64FC8"/></a>
    <a href="https://www.jetbrains.com/idea/download/other.html"><img alt="IntelliJ IDEA" src="https://img.shields.io/badge/IntelliJ%20IDEA-2023.3+-EE4677"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/LICENSE"><img alt="GitHub License" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-OpenCV?color=534BAE&label=License"/></a>
  </p>
</div>

******

### Languages

******

The current README.md supports the following languages:

- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hant-TW.md)
- English [en] # current
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-fr.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-es.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ru.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ar.md)

******

### Introduction

******

The AutoJs6 OpenCV Plugin supplies the OpenCV 4.8.0 native runtime used by AutoJs6 image APIs. In this compatibility-first design, the host retains the OpenCV Java API while the plugin supplies the matching native library for each ABI.

******

### Plugin Contract

******

- Application ID: `io.github.supermonster003.autojs6.plugin.opencv`
- Plugin ID: `opencv`
- Engine: `opencv`
- Variant: `4.8.0`
- Contract version: `2`
- Required host version code: `5237`
- Native library: `opencv_java4`
- Native NDK version: `26.1.10909125`
- Java API SHA-256: `340976552fda3cce525021f0b072427cabf0aa1c786fb80cfc4a3a8105d90b3f`

The same `OpenCvPluginInfoService` responds to `org.autojs.plugin.INFO` and `org.autojs.plugin.OPENCV`.

Both actions use the `opencv-runtime` category. The Binder interface is `IPluginInfoProvider` from common-plugin-api.

******

### ABIs

******

The project produces the following APK variants:

```text
arm64-v8a
armeabi-v7a
x86
x86_64
universal
```

`PluginInfo.supportedAbis` is derived from the OpenCV native library entries in the installed APK. A single-ABI APK reports only its own ABI, while the universal APK reports all four ABIs.

Install the universal plugin APK when APK Builder must produce applications for multiple ABIs. A single-ABI plugin can supply OpenCV only for that ABI.

******

### Build and Verification

******

Run the unit tests and verify the debug and release APKs:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

To build debug APKs only:

```powershell
.\gradlew.bat :app:assembleDebug
```

Before publishing, configure a trusted signing identity in the ignored `sign.properties` file and run:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

Without `sign.properties`, payloads can still be built and verified, but the generated release APKs are unsigned and must not be published.

******

### Runtime Behavior

******

The plugin has no standalone user interface. Once installed and enabled, AutoJs6 discovers its service and loads the native library that matches the Java API retained by the host. A loaded native library normally shares the host process lifetime, so restart AutoJs6 after updating the plugin.

The plugin carries only `libopencv_java4.so` and does not package `libc++_shared.so`. A compatible AutoJs6 host must supply and preload the process-wide dependency so that one process uses one C++ runtime.

Install only trusted builds. Before loading the native library, the host verifies the plugin signature, OpenCV version, contract version, and Java API SHA-256. Official releases should use a signing identity recognized by the host.

******

### Release History

******

# v1.0.0

###### 2026/07/22

* `Feature` Released the OpenCV 4.8.0 native runtime plugin for AutoJs6: the host retains the OpenCV Java API while the plugin supplies `libopencv_java4.so`
* `Feature` Added discovery through `org.autojs.plugin.INFO` and `org.autojs.plugin.OPENCV` with the `opencv-runtime` category, including compatibility metadata required by the host
* `Feature` Added APK variants for `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64`, and `universal`, with dynamic reporting of the ABIs actually packaged
* `Feature` Localized plugin metadata, instructions, README, and CHANGELOG for Spanish, French, Russian, Arabic, Japanese, Korean, English, Simplified Chinese, Hong Kong Traditional Chinese, and Taiwan Traditional Chinese
* `Feature` Added debug and release integrity checks for native inventory, ELF architecture, payload hashes, the Java API fingerprint, duplicate OpenCV Java classes, and license assets; publishable verification also requires configured signing
* `Feature` Packaged the complete OpenCV 4.8.0 and static third-party license texts in every APK; the plugin does not package `libc++_shared.so`, which a compatible AutoJs6 host must supply and preload
* `Fix` Rebuilt the OpenCV 4.8.0 native libraries with Android NDK 26 and API 24 to prevent exceptions from crossing incompatible C++ runtime boundaries and crashing the AutoJs6 host

##### For more release history

* [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/assets/doc/CHANGELOG-en.md)

******

### Resource Layout

******

```text
.readme/lang_*.json
.changelog/lang_*.json
.python/generate_markdown.py
app/src/main/res/values*/strings.xml
app/src/main/res/raw*/plugin_instruction.md
app/src/main/assets/doc/CHANGELOG-*.md
```

`strings.xml` contains localized plugin descriptions, and `plugin_instruction.md` contains instructions displayed by the host. README and CHANGELOG files are generated from JSON sources by `.python/generate_markdown.py`; full localized changelogs are packaged under `app/src/main/assets/doc`.

******

### License

******

Project code is licensed under the [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/LICENSE). See [THIRD_PARTY_NOTICES.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/THIRD_PARTY_NOTICES.md) for the licenses of OpenCV 4.8.0 and its statically linked third-party components. The complete license texts are packaged in every APK under `assets/licenses/opencv-4.8.0/`.

******

### Links

******

- AutoJs6 documentation: https://docs.autojs6.com/
- OpenCV official website: https://opencv.org/
- OpenCV 4.8.0 source: https://github.com/opencv/opencv/tree/4.8.0
