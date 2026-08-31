<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-opencv-ic-launcher" border="0" width="128" />
  </p>

  <p>OpenCV 4.8.0 native runtime plugin for AutoJs6</p>

  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases"><img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-OpenCV?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/issues"><img alt="GitHub closed issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-OpenCV?color=A24232&label=Issues"/></a>
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

The OpenCV Plugin supplies the OpenCV 4.8.0 native runtime that powers image processing in AutoJs6. Image APIs such as image finding, color detection, and template matching all rely on OpenCV for their computation; once this plugin is installed, an AutoJs6 host with pluggable OpenCV support can use these image features normally, with no extra setup in scripts.

The plugin follows a compatibility-first division of work: the AutoJs6 host retains the OpenCV Java API that scripts call directly, while the plugin carries the exactly matching native library `libopencv_java4.so`. This keeps the host package slim, lets each device install only the plugin package matching its processor architecture (ABI), and allows the OpenCV runtime to be updated independently of the host.

******

### Features

******

- Works out of the box: no configuration required; AutoJs6 discovers the plugin automatically and loads the OpenCV runtime on demand when image scripts run.
- Five APK flavors: four single-ABI packages (`arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64`) plus an all-in-one `universal` package, so each device installs only what it needs.
- Built from official sources: the native libraries are rebuilt from the unmodified OpenCV 4.8.0 source tree (NDK r26b, API 24), with build inputs and per-ABI hashes recorded in a provenance manifest for independent reproduction.
- Verified before loading: the host checks the plugin signature, OpenCV version, contract version, and Java API fingerprint, and refuses mismatched or tampered runtimes.
- One C++ runtime per process: the plugin does not duplicate `libc++_shared.so` and shares the process-wide dependency supplied by the host, preventing crashes caused by coexisting C++ runtimes.
- Transparent licensing: every APK packages the complete license texts of OpenCV and its statically linked third-party components, summarized in THIRD_PARTY_NOTICES.md.
- Multilingual: plugin metadata, instructions, README, and changelog are available in 10 languages.

******

### Usage

******

1. Download the plugin APK matching the device from the [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) page and install it on the device running AutoJs6; when unsure, pick the `universal` package or see `Choosing an APK` below.
2. Open the AutoJs6 plugin center and confirm that the `OpenCV` plugin is recognized and enabled.
3. Write and run scripts as usual: whenever a script uses image APIs, AutoJs6 loads the OpenCV native library from the plugin automatically; script code needs no changes.
4. After updating or reinstalling the plugin, fully exit and restart AutoJs6 before running image scripts again so the new native library takes effect.

> If the plugin does not show up in the plugin center, upgrade AutoJs6 to a recent version first (internal build 5237 or above). The plugin itself supports devices running Android 7.0 (API 24) and above.

<p align="center">
  <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/docs/images/screenshots/plugin-center-enabled.png?raw=true" alt="AutoJs6 Plugin Center recognizes OpenCV 1.1.0 and shows it enabled." width="480" />
</p>
<p align="center"><sub>AutoJs6 Plugin Center recognizes OpenCV 1.1.0 and shows it enabled.</sub></p>

******

### Choosing an APK

******

Each release ships 5 APKs that differ only in which native library architectures they bundle:

| Package | Best for |
|---|---|
| `arm64-v8a` | The vast majority of modern Android phones and tablets (64-bit ARM); the first choice |
| `armeabi-v7a` | Older 32-bit ARM devices |
| `x86_64` | 64-bit x86 emulators and a few x86 devices |
| `x86` | 32-bit x86 emulators and a few x86 devices |
| `universal` | Bundles all 4 architectures and is the largest; works on any device and is the safe pick when unsure |

Install the `universal` plugin package when using the AutoJs6 APK Builder to package applications for multiple architectures: a single-ABI plugin can supply OpenCV only for its own architecture. If a single-ABI package that does not match the device was installed by mistake, the plugin cannot provide a usable native library; switching to the `universal` package resolves it.

******

### Quick Self-check

******

After confirming that OpenCV appears enabled in the AutoJs6 plugin center and restarting AutoJs6, run this script. `images.initOpenCvIfNeeded()` exercises plugin discovery, compatibility checks, and native loading instead of inspecting metadata only:

```javascript
images.initOpenCvIfNeeded();

const Build = android.os.Build;
const Process = android.os.Process;
const Core = org.opencv.core.Core;
const processAbis = Process.is64Bit()
    ? Build.SUPPORTED_64_BIT_ABIS
    : Build.SUPPORTED_32_BIT_ABIS;
const processAbi = processAbis.length > 0 ? processAbis[0] : "unknown";

console.log("OpenCV version: " + Core.getVersionString());
console.log("Process ABI: " + processAbi);
```

Success prints `OpenCV version: 4.8.0` and a process ABI such as `arm64-v8a`. If loading fails, check these in order: install the `universal` package, update AutoJs6 to internal build 5237 or newer, then fully exit and restart AutoJs6.

******

### FAQ

******

#### How can I confirm the plugin is working?

Open the AutoJs6 plugin center; seeing the `OpenCV` plugin there means the host has recognized it. Then run any script that uses image APIs; results coming back normally means the native library loaded successfully.

#### Why is there no plugin icon in the app list?

This is expected. The plugin has no standalone interface and creates no launcher icon; after installation it is discovered and driven entirely by AutoJs6 in the background, and every interaction happens inside AutoJs6.

#### Image features misbehave after a plugin update, or the old version still seems active?

Once loaded, a native library lives as long as the host process, so updating the plugin does not swap out the library already in use. Fully exit and restart AutoJs6 and the new native library takes effect.

#### What if the host is reported as too old or incompatible?

The plugin requires an AutoJs6 internal build of 5237 or above, so upgrade AutoJs6 first. Before loading, the host verifies the contract version and the Java API fingerprint, and refuses to load on any mismatch instead of running with hidden risks.

#### The plugin is installed but image features still fail. What could be wrong?

The most common cause is an APK that does not match the device architecture: a single-ABI package works only on its own architecture. Switch to the `universal` package to rule this out; if it still fails, confirm the AutoJs6 version meets the requirement and retry after restarting AutoJs6.

#### Does the plugin access the network or request sensitive permissions?

No. Its manifest contains no network, storage, camera, or other sensitive system permissions; it only declares the plugin permission used to communicate with AutoJs6. Its sole job is handing the OpenCV native library to the host.

#### Why OpenCV 4.8.0 instead of a newer version?

The native library must match the OpenCV Java API retained by the host exactly (verified via a SHA-256 fingerprint), so the OpenCV version is locked by the contract between host and plugin. Newer OpenCV versions will follow as new variants once the host supports them; see [ROADMAP.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/ROADMAP.md) for progress.

******

### Permissions and Security

******

Native code runs in the same process as the host, so multiple lines of defense apply from build to load:

- Auditable origin: the native libraries are rebuilt from a pinned commit of the official OpenCV source tree, with toolchain versions and per-ABI hashes recorded in `libs/opencv-native-4.8.0.provenance.json`; anyone can reproduce and compare by following [NATIVE_BUILD.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/NATIVE_BUILD.md).
- Build gates: every build verifies the native library inventory, ELF architectures, payload hashes, the Java API fingerprint, duplicate OpenCV Java classes, and license assets; publishable builds additionally require a trusted signing identity.
- Pre-load verification: the host checks the plugin signature, OpenCV version, contract version, and Java API SHA-256 in turn, and refuses to load on any mismatch.
- The process-wide C++ runtime is supplied and preloaded by the host; the plugin carries no `libc++_shared.so`, avoiding crashes at incompatible runtime boundaries.
- Minimal footprint: no network or sensitive system permissions, no standalone interface, and communication with the host only through the AutoJs6 plugin permission.

Install the plugin only from the official [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) page or other trusted channels. Official packages use a signing identity recognized by the host; packages from unknown origins may fail host verification or hide risks even when the version number looks identical.

******

### Plugin Interface

******

The following information targets AutoJs6 host and plugin developers; the host uses these identifiers to discover the plugin and negotiate compatibility:

```text
application id: io.github.supermonster003.autojs6.plugin.opencv
plugin id: opencv
engine: opencv
variant: 4.8.0
contract version: 2
minimum host build: 5237
native library: libopencv_java4.so
native ndk version: 26.1.10909125
java api sha-256: 340976552fda3cce525021f0b072427cabf0aa1c786fb80cfc4a3a8105d90b3f
```

A single `OpenCvPluginInfoService` responds to both the `org.autojs.plugin.INFO` and `org.autojs.plugin.OPENCV` actions, each with the `opencv-runtime` category; the Binder interface is `IPluginInfoProvider` from common-plugin-api.

`PluginInfo.supportedAbis` is computed dynamically from the OpenCV native library entries actually present in the installed APK: a single-ABI package reports only its own architecture, while the `universal` package reports all 4.

******

### Roadmap

******

The plugin's planned capabilities and their completion status are maintained as a checkable list in ROADMAP.md, organized by milestones with acceptance criteria, covering 16 KB page size support, OpenCV version evolution, continuous integration, and diagnostics. Unchecked items are intentions rather than shipped capabilities; discussion via Issues is welcome.

- [View ROADMAP.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/ROADMAP.md)

******

### Release History

******

#### v1.1.0

_2026/08/31_

- `Feature` Release downloads now bundle the 5 architecture APKs, `SHA256SUMS.txt`, and the native-build provenance manifest so users can select the right package and verify files independently
- `Fix` Rebuilt all 4 ABI native libraries with 16 KB `PT_LOAD` alignment and added release gates so OpenCV loads on 16 KB page-size Android devices while remaining compatible with 4 KB devices
- `Improvement` Expanded the plugin-center instructions and 10-language README with a real enabled-state screenshot and a runnable self-check that prints the OpenCV version and process ABI
- `Improvement` Made supported-ABI reporting resilient across universal, single-ABI, and split installs, including missing or corrupt APK paths and extracted-library fallback

#### v1.0.0

_2026/07/22_

- `Feature` Initial release: supplies the OpenCV 4.8.0 native runtime behind the AutoJs6 image APIs; the host retains the OpenCV Java API called by scripts, while the plugin carries the exactly matching `libopencv_java4.so`
- `Feature` Automatic discovery and compatibility negotiation with AutoJs6 through the `org.autojs.plugin.INFO` and `org.autojs.plugin.OPENCV` actions (`opencv-runtime` category), exposing version, contract, and fingerprint metadata to the host
- `Feature` Five APK flavors: `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64`, plus an all-in-one `universal` package, with supported architectures reported dynamically from the actual APK contents
- `Feature` Plugin metadata, instructions, README, and changelog available in 10 languages: Simplified Chinese, Traditional Chinese (Hong Kong and Taiwan), English, French, Spanish, Japanese, Korean, Russian, and Arabic
- `Feature` Built-in build gates verifying the native library inventory, ELF architectures, payload hashes, the Java API fingerprint, duplicate OpenCV Java classes, and license assets; publishable builds additionally require a trusted signing identity
- `Feature` Complete license texts of OpenCV 4.8.0 and its statically linked third-party components packaged in every APK; `libc++_shared.so` is supplied and preloaded by a compatible AutoJs6 host instead of being duplicated in the plugin
- `Fix` Rebuilt the OpenCV 4.8.0 native libraries from official sources with Android NDK 26 (API 24) so that plugin and host share the same C++ runtime family, fixing AutoJs6 crashes caused by exceptions crossing incompatible runtime boundaries

##### For more release history

* [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/assets/doc/CHANGELOG-en.md)

******

### Build and Verification

******

This section targets developers who want to build the plugin from source.

Build debug APKs:

```powershell
.\gradlew.bat :app:assembleDebug
```

Run the unit tests and verify the integrity of debug and release APKs:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

Before publishing, configure a trusted signing identity in the untracked `sign.properties` and run:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

Generate the five signed APKs, SHA-256 manifest, and GitHub Release description (see [RELEASING.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/RELEASING.md) for the complete process):

```bat
scripts\release\prepare-release.bat
```

Without `sign.properties`, payloads can still be built and verified, but the generated release APKs are unsigned and must not be published.

Regular builds use the prebuilt native AAR in the repository, so compiling OpenCV locally is unnecessary; to rebuild fully from official sources and cross-check the provenance, see [NATIVE_BUILD.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/NATIVE_BUILD.md).

******

### Localization and Docs Generation

******

```text
.readme/common.json
.readme/lang_*.json
.readme/template_readme.md
.changelog/lang_*.json
.changelog/template_changelog.md
.python/generate_markdown.py
app/src/main/assets/doc/CHANGELOG-*.md
app/src/main/res/values-*/strings.xml
app/src/main/res/raw-*/plugin_instruction.md
```

`strings.xml` holds the localized plugin description, and `plugin_instruction.md` holds the instructions displayed by the host. For README and changelog, always edit the JSON sources under `.readme/` and `.changelog/`, then run `py .python/generate_markdown.py` to regenerate; generated artifacts are never edited by hand. Run `py .python/generate_markdown.py --check` to verify sources and artifacts are in sync.

******

### License

******

Project code is licensed under the [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/LICENSE). See [THIRD_PARTY_NOTICES.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/THIRD_PARTY_NOTICES.md) for the licenses of OpenCV 4.8.0 and its statically linked third-party components; the complete license texts are packaged in every APK under `assets/licenses/opencv-4.8.0/`.

******

### Links

******

- AutoJs6 documentation: https://docs.autojs6.com
- OpenCV official website: https://opencv.org
- OpenCV 4.8.0 source: https://github.com/opencv/opencv/tree/4.8.0
