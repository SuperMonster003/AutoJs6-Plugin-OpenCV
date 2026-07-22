<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-opencv-ic-launcher" border="0" width="128" />
  </p>

  <p>為 AutoJs6 提供 OpenCV 4.8.0 原生執行環境的外掛程式</p>

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

### 語言 (Languages)

******

目前 README.md 支援以下語言:

- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hans.md)
- 繁體中文 (香港) [zh-Hant-HK] # 目前
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hant-TW.md)
- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-en.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-fr.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-es.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ru.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ar.md)

******

### 簡介

******

AutoJs6 OpenCV 插件提供 AutoJs6 圖像 API 使用的 OpenCV 4.8.0 原生運行時. 此方案以兼容性為先, 宿主保留 OpenCV Java API, 插件為每種 ABI 提供相匹配的原生庫.

******

### 插件契約

******

- 應用程式包名: `io.github.supermonster003.autojs6.plugin.opencv`
- 插件 ID: `opencv`
- 引擎: `opencv`
- 變體: `4.8.0`
- 契約版本: `2`
- 最低宿主版本代碼: `5237`
- 原生庫: `opencv_java4`
- 原生 NDK 版本: `26.1.10909125`
- Java API SHA-256: `340976552fda3cce525021f0b072427cabf0aa1c786fb80cfc4a3a8105d90b3f`

同一個 `OpenCvPluginInfoService` 響應 `org.autojs.plugin.INFO` 和 `org.autojs.plugin.OPENCV`.

兩個 action 均使用 `opencv-runtime` category. Binder 介面為 common-plugin-api 的 `IPluginInfoProvider`.

******

### ABI

******

項目生成以下 APK 變體:

```text
arm64-v8a
armeabi-v7a
x86
x86_64
universal
```

`PluginInfo.supportedAbis` 根據已安裝 APK 中的 OpenCV 原生庫條目計算. 單 ABI APK 只上報自身 ABI, universal APK 則上報全部四種 ABI.

如需使用 APK 構建器生成多個 ABI 的應用, 請安裝 universal 插件包. 單 ABI 插件只能為其自身 ABI 提供 OpenCV.

******

### 構建與校驗

******

運行單元測試並校驗 debug 和 release APK:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

如只需構建 debug APK:

```powershell
.\gradlew.bat :app:assembleDebug
```

發佈前需在不會提交到版本庫的 `sign.properties` 中配置受信任的簽名身份, 然後運行:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

未配置 `sign.properties` 時仍可構建並校驗 payload, 但生成的 release APK 未簽名, 不可發佈.

******

### 運行方式

******

插件沒有獨立操作介面. 安裝並啟用後, AutoJs6 會發現其服務並載入與宿主所保留 Java API 匹配的原生庫. 已載入的原生庫通常與宿主進程保持相同生命週期, 因此插件更新後應重啟 AutoJs6.

插件只攜帶 `libopencv_java4.so`, 不打包 `libc++_shared.so`. 兼容的 AutoJs6 宿主必須提供並預先載入此進程級依賴, 以確保同一進程只使用一個 C++ 運行時.

請只安裝可信構建. 宿主會在載入原生庫前校驗插件簽名, OpenCV 版本, 契約版本和 Java API SHA-256. 官方發佈包應使用宿主認可的簽名身份.

******

### 發行歷史

******

# v1.0.0

###### 2026/07/22

* `新增` 首次發佈面向 AutoJs6 的 OpenCV 4.8.0 原生運行時插件: 宿主保留 OpenCV Java API, 插件提供 `libopencv_java4.so`
* `新增` 支援通過 `org.autojs.plugin.INFO` 和 `org.autojs.plugin.OPENCV` 以及 `opencv-runtime` category 發現插件, 並提供宿主所需的兼容性元數據
* `新增` 支援 `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64` 和 `universal` APK, 並動態上報實際打包的 ABI
* `新增` 插件資訊, 使用說明, README 與 CHANGELOG 均支援西班牙語, 法語, 俄語, 阿拉伯語, 日語, 韓語, 英語, 簡體中文, 香港繁體和台灣繁體
* `新增` 新增 debug 和 release 完整性校驗, 覆蓋原生庫清單, ELF 架構, payload 雜湊, Java API 指紋, 重複 OpenCV Java 類和許可資源; 可發佈構建校驗還要求配置簽名
* `新增` 每個 APK 均打包 OpenCV 4.8.0 和靜態連結第三方組件的完整許可原文; 插件不打包 `libc++_shared.so`, 兼容的 AutoJs6 宿主必須提供並預先載入此進程級依賴
* `修復` 將 OpenCV 4.8.0 原生庫改用 Android NDK 26 和 API 24 重建, 避免異常跨越不兼容的 C++ 運行時邊界並導致 AutoJs6 宿主崩潰

##### 更多發行歷史可參閱

* [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/assets/doc/CHANGELOG-zh-Hant-HK.md)

******

### 資源結構

******

```text
.readme/lang_*.json
.changelog/lang_*.json
.python/generate_markdown.py
app/src/main/res/values*/strings.xml
app/src/main/res/raw*/plugin_instruction.md
app/src/main/assets/doc/CHANGELOG-*.md
```

`strings.xml` 提供本地化插件描述, `plugin_instruction.md` 提供宿主側展示的說明. README 與 CHANGELOG 由 `.python/generate_markdown.py` 根據 JSON 源文件生成; 完整多語言更新日誌打包在 `app/src/main/assets/doc` 中.

******

### 許可

******

項目代碼使用 [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/LICENSE). OpenCV 4.8.0 及其靜態第三方組件許可詳見 [THIRD_PARTY_NOTICES.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/THIRD_PARTY_NOTICES.md). 完整許可原文會隨每個 APK 一同打包到 `assets/licenses/opencv-4.8.0/`.

******

### 相關連結

******

- AutoJs6 文件: https://docs.autojs6.com/
- OpenCV 官方網站: https://opencv.org/
- OpenCV 4.8.0 源代碼: https://github.com/opencv/opencv/tree/4.8.0
