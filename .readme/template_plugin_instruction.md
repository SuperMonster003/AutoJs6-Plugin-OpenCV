{{ p_introduction_what }}

### {{ h3_usage }}

{{ placeholder_usage_steps }}

{{ p_usage_note }}

### {{ h3_choose_apk }}

{{ p_choose_apk_intro }}:

| {{ th_apk_variant }} | {{ th_apk_target }} |
|---|---|
| `arm64-v8a` | {{ td_abi_arm64 }} |
| `armeabi-v7a` | {{ td_abi_arm32 }} |
| `x86_64` | {{ td_abi_x86_64 }} |
| `x86` | {{ td_abi_x86 }} |
| `universal` | {{ td_abi_universal }} |

{{ p_choose_apk_note }}

### {{ h3_self_check }}

{{ p_self_check_intro }}:

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

{{ p_self_check_result }}
