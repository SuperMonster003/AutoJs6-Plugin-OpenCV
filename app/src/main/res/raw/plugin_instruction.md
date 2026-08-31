The OpenCV Plugin supplies the OpenCV 4.8.0 native runtime that powers image processing in AutoJs6. Image APIs such as image finding, color detection, and template matching all rely on OpenCV for their computation; once this plugin is installed, an AutoJs6 host with pluggable OpenCV support can use these image features normally, with no extra setup in scripts.

### Usage

1. Download the plugin APK matching the device from the [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) page and install it on the device running AutoJs6; when unsure, pick the `universal` package or see `Choosing an APK` below.
2. Open the AutoJs6 plugin center and confirm that the `OpenCV` plugin is recognized and enabled.
3. Write and run scripts as usual: whenever a script uses image APIs, AutoJs6 loads the OpenCV native library from the plugin automatically; script code needs no changes.
4. After updating or reinstalling the plugin, fully exit and restart AutoJs6 before running image scripts again so the new native library takes effect.

If the plugin does not show up in the plugin center, upgrade AutoJs6 to a recent version first (internal build 5237 or above). The plugin itself supports devices running Android 7.0 (API 24) and above.

### Choosing an APK

Each release ships 5 APKs that differ only in which native library architectures they bundle:

| Package | Best for |
|---|---|
| `arm64-v8a` | The vast majority of modern Android phones and tablets (64-bit ARM); the first choice |
| `armeabi-v7a` | Older 32-bit ARM devices |
| `x86_64` | 64-bit x86 emulators and a few x86 devices |
| `x86` | 32-bit x86 emulators and a few x86 devices |
| `universal` | Bundles all 4 architectures and is the largest; works on any device and is the safe pick when unsure |

Install the `universal` plugin package when using the AutoJs6 APK Builder to package applications for multiple architectures: a single-ABI plugin can supply OpenCV only for its own architecture. If a single-ABI package that does not match the device was installed by mistake, the plugin cannot provide a usable native library; switching to the `universal` package resolves it.

### Quick Self-check

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
