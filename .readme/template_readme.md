<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <img src="{{ repo_url }}/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="{{ icon_alt }}" border="0" width="128" />
  </p>

  <p>{{ text_plugin_synopsis }}</p>

  <p>
    <a href="{{ repo_url }}/releases"><img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/{{ repo_slug }}?label=Release"/></a>
    <a href="{{ repo_url }}/issues"><img alt="GitHub closed issues" src="https://img.shields.io/github/issues/{{ repo_slug }}?color=A24232&label=Issues"/></a>
    <a href="{{ license_url }}"><img alt="GitHub License" src="https://img.shields.io/github/license/{{ repo_slug }}?color=534BAE&label=License"/></a>
  </p>
</div>

******

### {{ h3_languages_with_ascii }}

******

{{ p_languages_all_supported_for_readme }}:

{{ placeholder_ul_languages_all_supported }}

******

### {{ h3_introduction }}

******

{{ p_introduction_what }}

{{ p_introduction_how }}

******

### {{ h3_features }}

******

{{ placeholder_features }}

******

### {{ h3_usage }}

******

{{ placeholder_usage_steps }}

> {{ p_usage_note }}

<p align="center">
  <img src="{{ repo_url }}/blob/master/docs/images/screenshots/plugin-center-enabled.png?raw=true" alt="{{ p_plugin_center_screenshot }}" width="480" />
</p>
<p align="center"><sub>{{ p_plugin_center_screenshot }}</sub></p>

******

### {{ h3_choose_apk }}

******

{{ p_choose_apk_intro }}:

| {{ th_apk_variant }} | {{ th_apk_target }} |
|---|---|
| `arm64-v8a` | {{ td_abi_arm64 }} |
| `armeabi-v7a` | {{ td_abi_arm32 }} |
| `x86_64` | {{ td_abi_x86_64 }} |
| `x86` | {{ td_abi_x86 }} |
| `universal` | {{ td_abi_universal }} |

{{ p_choose_apk_note }}

******

### {{ h3_self_check }}

******

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

******

### {{ h3_faq }}

******

{{ placeholder_faq }}

******

### {{ h3_security }}

******

{{ p_security_intro }}

{{ placeholder_security_points }}

{{ p_security_permission }}

******

### {{ h3_plugin_interface }}

******

{{ p_plugin_interface }}:

```text
application id: {{ plugin_application_id }}
plugin id: {{ plugin_id }}
engine: {{ plugin_engine }}
variant: {{ plugin_variant }}
contract version: {{ plugin_contract_version }}
minimum host build: {{ required_host_version_code }}
native library: {{ native_library_file }}
native ndk version: {{ native_ndk_version }}
java api sha-256: {{ java_api_sha256 }}
```

{{ p_contract_service }}

{{ p_abi_reporting }}

******

### {{ h3_roadmap }}

******

{{ p_roadmap }}

- [{{ text_link_roadmap }}]({{ roadmap_url }})

******

### {{ h3_release_history }}

******

{{ placeholder_latest_release_history }}

##### {{ h5_for_more_release_history }}

* {{ placeholder_read_more_in_changelog_md }}

******

### {{ h3_build }}

******

{{ p_build_intro }}

{{ p_build_debug }}:

```powershell
.\gradlew.bat :app:assembleDebug
```

{{ p_build_verification }}:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

{{ p_build_signing }}:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

{{ p_build_release }}:

```bat
scripts\release\prepare-release.bat
```

{{ p_build_unsigned }}

{{ p_build_native }}

******

### {{ h3_resource_layout }}

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

{{ p_resource_layout }}

******

### {{ h3_license }}

******

{{ p_license }}

******

### {{ h3_links }}

******

- {{ text_link_autojs6_docs }}: {{ autojs6_docs_url }}
- {{ text_link_opencv_official }}: {{ opencv_official_url }}
- {{ text_link_opencv_source }}: {{ opencv_source_url }}
