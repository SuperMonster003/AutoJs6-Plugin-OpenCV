<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <img src="{{ repo_url }}/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-opencv-ic-launcher" border="0" width="128" />
  </p>

  <p>{{ text_plugin_synopsis }}</p>

  <p>
    <a href="{{ repo_url }}/releases"><img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-OpenCV?label=Release"/></a>
    <a href="{{ repo_url }}/issues"><img alt="GitHub closed issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-OpenCV?color=A24232&label=Issues"/></a>
    <br>
    <a href="https://developer.android.com/studio/archive"><img alt="Android Studio" src="https://img.shields.io/badge/Android%20Studio-2023.3+-B64FC8"/></a>
    <a href="https://www.jetbrains.com/idea/download/other.html"><img alt="IntelliJ IDEA" src="https://img.shields.io/badge/IntelliJ%20IDEA-2023.3+-EE4677"/></a>
    <a href="{{ license_url }}"><img alt="GitHub License" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-OpenCV?color=534BAE&label=License"/></a>
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

{{ p_introduction }}

******

### {{ h3_plugin_contract }}

******

- {{ text_application_id }}: `{{ plugin_application_id }}`
- {{ text_plugin_id }}: `{{ plugin_id }}`
- {{ text_engine }}: `{{ plugin_engine }}`
- {{ text_variant }}: `{{ plugin_variant }}`
- {{ text_contract_version }}: `{{ plugin_contract_version }}`
- {{ text_required_host_version_code }}: `{{ required_host_version_code }}`
- {{ text_native_library }}: `{{ native_library_name }}`
- {{ text_native_ndk_version }}: `{{ native_ndk_version }}`
- {{ text_java_api_sha256 }}: `{{ java_api_sha256 }}`

{{ p_contract_service }}

{{ p_contract_binder }}

******

### {{ h3_abis }}

******

{{ p_abis_intro }}:

```text
{{ supported_abis }}
```

{{ p_abi_reporting }}

{{ p_abi_builder }}

******

### {{ h3_build_and_verification }}

******

{{ p_build_verification }}:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

{{ p_build_only }}:

```powershell
.\gradlew.bat :app:assembleDebug
```

{{ p_build_signing }}:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

{{ p_build_unsigned }}

******

### {{ h3_runtime_behavior }}

******

{{ p_runtime_behavior }}

{{ p_runtime_cpp }}

{{ p_runtime_trust }}

******

### {{ h3_release_history }}

******

{{ placeholder_latest_release_history }}

##### {{ h5_for_more_release_history }}

* {{ placeholder_read_more_in_changelog_md }}

******

### {{ h3_resource_layout }}

******

```text
.readme/lang_*.json
.changelog/lang_*.json
.python/generate_markdown.py
app/src/main/res/values*/strings.xml
app/src/main/res/raw*/plugin_instruction.md
app/src/main/assets/doc/CHANGELOG-*.md
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
