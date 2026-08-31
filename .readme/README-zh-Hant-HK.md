<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-opencv-ic-launcher" border="0" width="128" />
  </p>

  <p>為 AutoJs6 提供 OpenCV 4.8.0 原生執行環境的外掛程式</p>

  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases"><img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-OpenCV?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/issues"><img alt="GitHub closed issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-OpenCV?color=A24232&label=Issues"/></a>
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

OpenCV 插件 (OpenCV Plugin) 為 AutoJs6 提供圖像處理所需的 OpenCV 4.8.0 原生運行時. AutoJs6 的找圖, 找色, 模板匹配等圖像 API 都依賴 OpenCV 完成運算; 安裝本插件後, 支援插件化 OpenCV 的 AutoJs6 即可正常使用這些圖像功能, 腳本無需任何額外配置.

插件採用兼容優先的分工設計: AutoJs6 宿主保留腳本直接調用的 OpenCV Java API, 插件攜帶與之精確匹配的原生庫 `libopencv_java4.so`. 這樣宿主安裝包保持精簡, 裝置只需安裝與自身處理器架構 (ABI) 匹配的插件包, OpenCV 運行時也可以獨立於宿主更新.

******

### 功能亮點

******

- 開箱即用: 安裝後無需任何配置, AutoJs6 自動發現插件並在運行圖像腳本時按需載入 OpenCV 運行時.
- 五種安裝包: 提供 `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64` 四種單架構包與包含全部架構的 `universal` 包, 按裝置按需選擇, 體積可控.
- 官方源代碼構建: 原生庫從未經修改的 OpenCV 4.8.0 官方源代碼定點重建 (NDK r26b, API 24), 構建輸入與逐 ABI 雜湊完整記錄在 provenance 清單中, 可復現可比對.
- 載入前多重核驗: 宿主會先校驗插件簽名, OpenCV 版本, 契約版本與 Java API 指紋, 全部通過才載入原生庫, 拒絕不匹配或被篡改的運行時.
- 進程內單一 C++ 運行時: 插件不重複打包 `libc++_shared.so`, 與宿主共享同一份進程級依賴, 避免多份 C++ 運行時共存引發的崩潰.
- 許可透明: 每個安裝包內置 OpenCV 及其靜態第三方組件的完整許可原文, 並附彙總說明 THIRD_PARTY_NOTICES.md.
- 多語言: 插件資訊, 使用說明, README 與更新日誌覆蓋 10 種語言.

******

### 使用方法

******

1. 從 [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) 頁面下載與裝置匹配的插件 APK 並安裝到運行 AutoJs6 的裝置上; 不確定選哪個時, 可直接選 `universal` 包, 或參考下方 `如何選擇安裝包`.
2. 開啟 AutoJs6 的插件中心, 確認 `OpenCV` 插件已被識別並處於啟用狀態.
3. 像平時一樣編寫和運行腳本: 腳本用到圖像 API 時, AutoJs6 會自動載入插件提供的 OpenCV 原生庫, 腳本代碼無需任何改動.
4. 更新或重裝插件後, 先完全退出並重啟 AutoJs6, 再繼續運行圖像相關腳本, 以確保新版本原生庫生效.

> 若插件中心未顯示該插件, 請先將 AutoJs6 升級到較新版本 (內部版本號 5237 及以上). 插件自身支援 Android 7.0 (API 24) 及以上的裝置.

<p align="center">
  <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/docs/images/screenshots/plugin-center-enabled.png?raw=true" alt="AutoJs6 插件中心已識別 OpenCV 1.1.0, 且開關處於啓用狀態." width="480" />
</p>
<p align="center"><sub>AutoJs6 插件中心已識別 OpenCV 1.1.0, 且開關處於啓用狀態.</sub></p>

******

### 如何選擇安裝包

******

每個發行版本包含 5 個 APK, 差別僅在於內置了哪些架構的原生庫:

| 安裝包 | 適用對象 |
|---|---|
| `arm64-v8a` | 絕大多數現代 Android 手機與平板 (64 位 ARM), 優先選擇 |
| `armeabi-v7a` | 較早期的 32 位 ARM 裝置 |
| `x86_64` | 64 位 x86 模擬器與少數 x86 裝置 |
| `x86` | 32 位 x86 模擬器與少數 x86 裝置 |
| `universal` | 內置全部 4 種架構, 體積最大; 適用於任何裝置, 也是不確定架構時的穩妥選擇 |

如需使用 AutoJs6 的 APK 構建器打包面向多種架構的應用程式, 必須安裝 `universal` 插件包: 單架構插件只能為其自身架構提供 OpenCV. 若誤裝了與裝置架構不匹配的單架構包, 插件將無法提供可用的原生庫, 改為安裝 `universal` 包即可解決.

******

### 快速自檢

******

確認 AutoJs6 插件中心已顯示並啟用 OpenCV, 重新啟動 AutoJs6 後運行以下腳本. `images.initOpenCvIfNeeded()` 會實際觸發插件偵測, 相容性校驗與原生庫載入, 而不只是讀取元數據:

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

成功時會輸出 `OpenCV version: 4.8.0` 與 `arm64-v8a` 等進程 ABI. 若載入失敗, 請依次排查: 換裝 `universal` 包, 將 AutoJs6 升級到內部版本號 5237 或更高版本, 最後完全退出並重新啟動 AutoJs6.

******

### 常見問題

******

#### 如何確認插件已經生效?

開啟 AutoJs6 的插件中心, 能看到 `OpenCV` 插件即表示宿主已識別. 隨後運行任意用到圖像 API 的腳本, 如能正常返回結果, 說明原生庫已成功載入.

#### 為什麼應用程式列表裏沒有插件的圖示?

這是正常現象. 插件沒有獨立介面, 也不在桌面建立啟動圖示, 安裝後由 AutoJs6 在背景自動發現和調用, 全部互動都在 AutoJs6 內完成.

#### 更新插件後圖像功能異常, 或感覺仍在使用舊版本?

原生庫一旦載入就會伴隨宿主進程存續, 更新插件不會替換正在運行中的舊庫. 完全退出並重啟 AutoJs6 後, 新版本原生庫即可生效.

#### 提示宿主版本過低或插件不兼容, 怎麼辦?

本插件要求 AutoJs6 內部版本號達到 5237 及以上, 請先升級 AutoJs6. 宿主在載入前會校驗契約版本與 Java API 指紋, 兩端不匹配時會直接拒絕載入, 而不是帶著隱患運行.

#### 插件已安裝, 圖像功能仍不可用, 可能是什麼原因?

最常見的原因是安裝包與裝置架構不匹配: 單架構包只對自身架構生效. 可先改為安裝 `universal` 包排除架構因素; 若仍無效, 請確認 AutoJs6 版本滿足要求, 並在重啟 AutoJs6 後重試.

#### 插件會連線或申請敏感權限嗎?

不會. 插件清單不含網絡, 儲存空間, 相機等任何敏感系統權限, 僅聲明用於與 AutoJs6 通訊的插件權限. 它的唯一職責是把 OpenCV 原生庫交給宿主載入.

#### 為什麼提供的是 OpenCV 4.8.0 而不是更新的版本?

插件的原生庫必須與宿主保留的 OpenCV Java API 嚴格匹配 (通過 SHA-256 指紋核驗), 因此 OpenCV 版本由宿主與插件的契約共同鎖定. 更新的 OpenCV 版本將在宿主支援後以新變體形式跟進, 進展可關注 [ROADMAP.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/ROADMAP.md).

******

### 權限與安全

******

原生代碼與宿主運行在同一進程中, 因此插件從構建到載入設有多道防線:

- 來源可覆核: 原生庫從 OpenCV 官方源代碼的固定提交重建, 工具鏈版本與逐 ABI 雜湊記錄在 `libs/opencv-native-4.8.0.provenance.json` 中, 依照 [NATIVE_BUILD.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/NATIVE_BUILD.md) 可獨立復現比對.
- 構建門禁: 每次構建都會校驗原生庫清單, ELF 架構, payload 雜湊, Java API 指紋, 重複 OpenCV Java 類與許可資源, 發佈構建還要求配置受信任的簽名身份.
- 載入前核驗: 宿主依次校驗插件簽名, OpenCV 版本, 契約版本與 Java API SHA-256, 任何一項不符即拒絕載入.
- 進程級 C++ 運行時由宿主統一提供並預先載入, 插件不攜帶 `libc++_shared.so`, 避免不兼容運行時邊界引發的崩潰.
- 最小權限: 不申請網絡與任何敏感系統權限, 無獨立介面, 僅通過 AutoJs6 插件權限與宿主通訊.

請僅從官方 [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) 頁面或其他可信渠道獲取插件. 官方發佈包使用宿主認可的簽名身份; 來源不明的安裝包即使版本號相同, 也可能無法通過宿主校驗或暗藏風險.

******

### 插件介面

******

以下資訊面向 AutoJs6 宿主與插件開發者, 宿主通過這些標識發現插件並完成兼容性協商:

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

同一個 `OpenCvPluginInfoService` 響應 `org.autojs.plugin.INFO` 與 `org.autojs.plugin.OPENCV` 兩個 action, 均使用 `opencv-runtime` category; Binder 介面為 common-plugin-api 的 `IPluginInfoProvider`.

`PluginInfo.supportedAbis` 依據已安裝 APK 中實際存在的 OpenCV 原生庫條目動態計算: 單架構包只上報自身架構, `universal` 包上報全部 4 種架構.

******

### 開發路線圖

******

插件的能力規劃與完成情況以可勾選清單維護在 ROADMAP.md 中, 按里程碑組織並附驗收條件, 涵蓋 16 KB 記憶體分頁適配, OpenCV 版本演進, 持續整合與診斷體驗等方向. 未勾選條目表示規劃意向而非目前版本能力, 歡迎通過 Issues 參與討論.

- [查看 ROADMAP.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/ROADMAP.md)

******

### 發行歷史

******

#### v1.1.0

_2026/08/31_

- `新增` 發佈下載現統一包含 5 個架構安裝包, `SHA256SUMS.txt` 與原生構建 provenance 清單, 方便按裝置選包並獨立核驗檔案
- `修復` 以 16 KB `PT_LOAD` 對齊重新構建 4 個 ABI 的原生庫並加入發佈門禁, 使 OpenCV 可在 16 KB page-size Android 裝置上載入, 同時保持對 4 KB 裝置的兼容
- `優化` 擴充插件中心說明與 10 語言 README: 加入真實啟用狀態截圖和可直接運行的自檢腳本, 輸出 OpenCV 版本與目前進程 ABI
- `優化` 增強支援 ABI 的識別邏輯: 覆蓋 universal, 單架構與 split 安裝, 並能在 APK 路徑缺失或損壞時繼續掃描或使用已提取原生庫回退

#### v1.0.0

_2026/07/22_

- `新增` 首個正式版本: 為 AutoJs6 的圖像 API 提供 OpenCV 4.8.0 原生運行時, 宿主保留腳本調用的 OpenCV Java API, 插件攜帶與之精確匹配的 `libopencv_java4.so`
- `新增` 支援被 AutoJs6 自動發現與兼容性協商: 通過 `org.autojs.plugin.INFO` 與 `org.autojs.plugin.OPENCV` action (`opencv-runtime` category) 向宿主提供版本, 契約與指紋等元數據
- `新增` 提供 `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64` 四種單架構安裝包與包含全部架構的 `universal` 包, 並按安裝包實際內容動態上報支援的架構
- `新增` 插件資訊, 使用說明, README 與更新日誌覆蓋 10 種語言: 簡體中文, 香港繁體, 台灣繁體, 英語, 法語, 西班牙語, 日語, 韓語, 俄語與阿拉伯語
- `新增` 內置構建門禁: 校驗原生庫清單, ELF 架構, payload 雜湊, Java API 指紋, 重複 OpenCV Java 類與許可資源, 發佈構建還要求配置受信任的簽名身份
- `新增` 每個安裝包內置 OpenCV 4.8.0 及其靜態第三方組件的完整許可原文; `libc++_shared.so` 由兼容的 AutoJs6 宿主統一提供並預先載入, 插件不重複打包
- `修復` 改用 Android NDK 26 (API 24) 從官方源代碼重建 OpenCV 4.8.0 原生庫, 使插件與宿主共用同源 C++ 運行時, 修復異常跨越不兼容運行時邊界導致 AutoJs6 崩潰的問題

##### 更多發行歷史可參閱

* [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/assets/doc/CHANGELOG-zh-Hant-HK.md)

******

### 構建與校驗

******

本節面向希望從源代碼構建插件的開發者.

構建 debug APK:

```powershell
.\gradlew.bat :app:assembleDebug
```

運行單元測試並校驗 debug 與 release APK 的完整性:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

發佈前在不會提交到版本庫的 `sign.properties` 中配置受信任的簽名身份, 然後運行:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

生成 5 個已簽名 APK, SHA-256 清單與 GitHub Release 說明 (完整流程見 [RELEASING.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/RELEASING.md)):

```bat
scripts\release\prepare-release.bat
```

未配置 `sign.properties` 時仍可構建並校驗 payload, 但生成的 release APK 未簽名, 不可發佈.

常規構建直接使用倉庫內預構建的原生庫 AAR, 無需本地編譯 OpenCV; 如需從官方源代碼完整重建並核對 provenance, 參見 [NATIVE_BUILD.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/NATIVE_BUILD.md).

******

### 本地化與文件生成

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

`strings.xml` 提供本地化插件描述, `plugin_instruction.md` 提供宿主側展示的使用說明. README 與更新日誌一律修改 `.readme/` 與 `.changelog/` 下的 JSON 源文件, 再運行 `py .python/generate_markdown.py` 重新生成, 生成產物不手工編輯; 運行 `py .python/generate_markdown.py --check` 可校驗源文件與生成產物是否同步.

******

### 許可

******

項目代碼使用 [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/LICENSE). OpenCV 4.8.0 及其靜態第三方組件的許可詳見 [THIRD_PARTY_NOTICES.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/THIRD_PARTY_NOTICES.md), 完整許可原文隨每個 APK 打包在 `assets/licenses/opencv-4.8.0/` 目錄下.

******

### 相關連結

******

- AutoJs6 文件: https://docs.autojs6.com
- OpenCV 官方網站: https://opencv.org
- OpenCV 4.8.0 源代碼: https://github.com/opencv/opencv/tree/4.8.0
