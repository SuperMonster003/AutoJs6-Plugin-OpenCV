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
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hant-HK.md)
- 繁體中文 (台灣) [zh-Hant-TW] # 目前
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

AutoJs6 OpenCV 外掛提供 AutoJs6 影像 API 使用的 OpenCV 4.8.0 原生執行環境. 此方案以相容性為優先, 主程式保留 OpenCV Java API, 外掛為每種 ABI 提供相符的原生程式庫.

******

### 外掛契約

******

- 應用程式 ID: `io.github.supermonster003.autojs6.plugin.opencv`
- 外掛 ID: `opencv`
- 引擎: `opencv`
- 變體: `4.8.0`
- 契約版本: `1`
- 最低主程式版本碼: `5236`
- 原生程式庫: `opencv_java4`
- Java API SHA-256: `340976552fda3cce525021f0b072427cabf0aa1c786fb80cfc4a3a8105d90b3f`

同一個 `OpenCvPluginInfoService` 會回應 `org.autojs.plugin.INFO` 和 `org.autojs.plugin.OPENCV`.

兩個 action 皆使用 `opencv-runtime` category. Binder 介面為 common-plugin-api 的 `IPluginInfoProvider`.

******

### ABI

******

本專案會產生以下 APK 變體:

```text
arm64-v8a
armeabi-v7a
x86
x86_64
universal
```

`PluginInfo.supportedAbis` 會根據已安裝 APK 中的 OpenCV 原生程式庫項目計算. 單一 ABI APK 只回報自身 ABI, universal APK 則回報全部四種 ABI.

如需使用 APK 建置器產生適用於多個 ABI 的應用程式, 請安裝 universal 外掛套件. 單一 ABI 外掛只能為其自身 ABI 提供 OpenCV.

******

### 建置與驗證

******

執行單元測試並驗證 debug 和 release APK:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

如只需建置 debug APK:

```powershell
.\gradlew.bat :app:assembleDebug
```

發布前需在不納入版本控制的 `sign.properties` 中設定受信任的簽章身分, 然後執行:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

未設定 `sign.properties` 時仍可建置並驗證 payload, 但產生的 release APK 未簽署, 不可發布.

******

### 執行方式

******

外掛沒有獨立操作介面. 安裝並啟用後, AutoJs6 會找到其服務並載入與主程式所保留 Java API 相符的原生程式庫. 已載入的原生程式庫通常與主程式處理程序具有相同生命週期, 因此外掛更新後應重新啟動 AutoJs6.

外掛只包含 `libopencv_java4.so`, 不打包 `libc++_shared.so`. 相容的 AutoJs6 主程式必須提供並預先載入此處理程序層級相依性, 確保單一處理程序只使用一個 C++ 執行環境.

請只安裝可信任的建置版本. 主程式會在載入原生程式庫前驗證外掛簽章, OpenCV 版本, 契約版本和 Java API SHA-256. 官方發布套件應使用主程式認可的簽章身分.

******

### 發行記錄

******

# v1.0.0

###### 2026/07/22

* `新增` 首次發布用於 AutoJs6 的 OpenCV 4.8.0 原生執行環境外掛: 主程式保留 OpenCV Java API, 外掛提供 `libopencv_java4.so`
* `新增` 支援透過 `org.autojs.plugin.INFO` 和 `org.autojs.plugin.OPENCV` 以及 `opencv-runtime` category 發現外掛, 並提供主程式所需的相容性中繼資料
* `新增` 支援 `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64` 和 `universal` APK, 並動態回報實際打包的 ABI
* `新增` 外掛資訊, 使用說明, README 與 CHANGELOG 均支援西班牙文, 法文, 俄文, 阿拉伯文, 日文, 韓文, 英文, 簡體中文, 香港繁體和台灣繁體
* `新增` 新增 debug 和 release 完整性驗證, 涵蓋原生程式庫清單, ELF 架構, payload 雜湊, Java API 指紋, 重複 OpenCV Java 類別和授權資源; 可發布建置版本的驗證還要求設定簽章
* `新增` 每個 APK 均打包 OpenCV 4.8.0 和靜態連結第三方元件的完整授權原文; 外掛不打包 `libc++_shared.so`, 相容的 AutoJs6 主程式必須提供並預先載入此處理程序層級相依性

##### 更多發行記錄可參閱

* [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/assets/doc/CHANGELOG-zh-Hant-TW.md)

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

`strings.xml` 提供在地化的外掛描述, `plugin_instruction.md` 提供主程式端顯示的說明. README 與 CHANGELOG 由 `.python/generate_markdown.py` 根據 JSON 來源檔產生; 完整多語言更新記錄打包在 `app/src/main/assets/doc` 中.

******

### 授權

******

專案程式碼採用 [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/LICENSE). OpenCV 4.8.0 及其靜態第三方元件授權詳見 [THIRD_PARTY_NOTICES.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/THIRD_PARTY_NOTICES.md). 完整授權原文會隨每個 APK 一同打包至 `assets/licenses/opencv-4.8.0/`.

******

### 相關連結

******

- AutoJs6 文件: https://docs.autojs6.com/
- OpenCV 官方網站: https://opencv.org/
- OpenCV 4.8.0 原始碼: https://github.com/opencv/opencv/tree/4.8.0
