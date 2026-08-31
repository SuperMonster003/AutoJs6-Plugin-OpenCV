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

OpenCV 外掛 (OpenCV Plugin) 為 AutoJs6 提供影像處理所需的 OpenCV 4.8.0 原生執行環境. AutoJs6 的找圖, 找色, 模板比對等影像 API 都依賴 OpenCV 完成運算; 安裝本外掛後, 支援外掛化 OpenCV 的 AutoJs6 即可正常使用這些影像功能, 腳本無需任何額外設定.

外掛採用相容優先的分工設計: AutoJs6 主程式保留腳本直接呼叫的 OpenCV Java API, 外掛攜帶與之精確相符的原生程式庫 `libopencv_java4.so`. 這樣主程式安裝套件保持精簡, 裝置只需安裝與自身處理器架構 (ABI) 相符的外掛套件, OpenCV 執行環境也可以獨立於主程式更新.

******

### 功能亮點

******

- 開箱即用: 安裝後無需任何設定, AutoJs6 會自動發現外掛並在執行影像腳本時視需要載入 OpenCV 執行環境.
- 五種安裝套件: 提供 `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64` 四種單一架構套件與包含全部架構的 `universal` 套件, 依裝置需求選擇, 體積可控.
- 官方原始碼建置: 原生程式庫從未經修改的 OpenCV 4.8.0 官方原始碼定點重建 (NDK r26b, API 24), 建置輸入與逐 ABI 雜湊完整記錄在 provenance 清單中, 可重現可比對.
- 載入前多重驗證: 主程式會先驗證外掛簽章, OpenCV 版本, 契約版本與 Java API 指紋, 全部通過才載入原生程式庫, 拒絕不相符或被竄改的執行環境.
- 處理程序內單一 C++ 執行環境: 外掛不重複打包 `libc++_shared.so`, 與主程式共享同一份處理程序層級相依性, 避免多份 C++ 執行環境並存引發的當機.
- 授權透明: 每個安裝套件內建 OpenCV 及其靜態第三方元件的完整授權原文, 並附彙整說明 THIRD_PARTY_NOTICES.md.
- 多語言: 外掛資訊, 使用說明, README 與更新記錄涵蓋 10 種語言.

******

### 使用方法

******

1. 從 [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) 頁面下載與裝置相符的外掛 APK 並安裝到執行 AutoJs6 的裝置上; 不確定該選哪個時, 可直接選 `universal` 套件, 或參考下方 `如何選擇安裝套件`.
2. 開啟 AutoJs6 的外掛中心, 確認 `OpenCV` 外掛已被識別並處於啟用狀態.
3. 像平時一樣編寫和執行腳本: 腳本用到影像 API 時, AutoJs6 會自動載入外掛提供的 OpenCV 原生程式庫, 腳本程式碼無需任何改動.
4. 更新或重新安裝外掛後, 先完全退出並重新啟動 AutoJs6, 再繼續執行影像相關腳本, 以確保新版本原生程式庫生效.

> 若外掛中心未顯示該外掛, 請先將 AutoJs6 升級到較新版本 (內部版本號 5237 及以上). 外掛自身支援 Android 7.0 (API 24) 及以上的裝置.

<p align="center">
  <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/docs/images/screenshots/plugin-center-enabled.png?raw=true" alt="AutoJs6 外掛中心已識別 OpenCV 1.1.0, 且開關處於啟用狀態." width="480" />
</p>
<p align="center"><sub>AutoJs6 外掛中心已識別 OpenCV 1.1.0, 且開關處於啟用狀態.</sub></p>

******

### 如何選擇安裝套件

******

每個發行版本包含 5 個 APK, 差別僅在於內建了哪些架構的原生程式庫:

| 安裝套件 | 適用對象 |
|---|---|
| `arm64-v8a` | 絕大多數現代 Android 手機與平板 (64 位元 ARM), 優先選擇 |
| `armeabi-v7a` | 較早期的 32 位元 ARM 裝置 |
| `x86_64` | 64 位元 x86 模擬器與少數 x86 裝置 |
| `x86` | 32 位元 x86 模擬器與少數 x86 裝置 |
| `universal` | 內建全部 4 種架構, 體積最大; 適用於任何裝置, 也是不確定架構時的穩妥選擇 |

如需使用 AutoJs6 的 APK 建置器打包適用於多種架構的應用程式, 必須安裝 `universal` 外掛套件: 單一架構外掛只能為其自身架構提供 OpenCV. 若誤裝了與裝置架構不相符的單一架構套件, 外掛將無法提供可用的原生程式庫, 改為安裝 `universal` 套件即可解決.

******

### 快速自我檢查

******

確認 AutoJs6 外掛中心已顯示並啟用 OpenCV, 重新啟動 AutoJs6 後執行以下指令碼. `images.initOpenCvIfNeeded()` 會實際觸發外掛偵測, 相容性驗證與原生程式庫載入, 而不只是讀取中繼資料:

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

成功時會輸出 `OpenCV version: 4.8.0` 與 `arm64-v8a` 等處理程序 ABI. 若載入失敗, 請依序排查: 改裝 `universal` 套件, 將 AutoJs6 更新到內部版本號 5237 或更新版本, 最後完全結束並重新啟動 AutoJs6.

******

### 常見問題

******

#### 如何確認外掛已經生效?

開啟 AutoJs6 的外掛中心, 能看到 `OpenCV` 外掛即表示主程式已識別. 隨後執行任意用到影像 API 的腳本, 如能正常回傳結果, 表示原生程式庫已成功載入.

#### 為什麼應用程式清單裡沒有外掛的圖示?

這是正常現象. 外掛沒有獨立介面, 也不會在桌面建立啟動圖示, 安裝後由 AutoJs6 在背景自動發現和呼叫, 全部互動都在 AutoJs6 內完成.

#### 更新外掛後影像功能異常, 或感覺仍在使用舊版本?

原生程式庫一旦載入就會伴隨主程式處理程序存續, 更新外掛不會替換正在執行中的舊程式庫. 完全退出並重新啟動 AutoJs6 後, 新版本原生程式庫即可生效.

#### 提示主程式版本過低或外掛不相容, 怎麼辦?

本外掛要求 AutoJs6 內部版本號達到 5237 及以上, 請先升級 AutoJs6. 主程式在載入前會驗證契約版本與 Java API 指紋, 兩端不相符時會直接拒絕載入, 而不是帶著隱患執行.

#### 外掛已安裝, 影像功能仍不可用, 可能是什麼原因?

最常見的原因是安裝套件與裝置架構不相符: 單一架構套件只對自身架構生效. 可先改為安裝 `universal` 套件排除架構因素; 若仍無效, 請確認 AutoJs6 版本符合要求, 並在重新啟動 AutoJs6 後重試.

#### 外掛會連線或申請敏感權限嗎?

不會. 外掛的資訊清單不含網路, 儲存空間, 相機等任何敏感系統權限, 僅宣告用於與 AutoJs6 通訊的外掛權限. 它的唯一職責是將 OpenCV 原生程式庫交給主程式載入.

#### 為什麼提供的是 OpenCV 4.8.0 而不是更新的版本?

外掛的原生程式庫必須與主程式保留的 OpenCV Java API 嚴格相符 (透過 SHA-256 指紋核對), 因此 OpenCV 版本由主程式與外掛的契約共同鎖定. 更新的 OpenCV 版本將在主程式支援後以新變體形式跟進, 進展可關注 [ROADMAP.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/ROADMAP.md).

******

### 權限與安全

******

原生程式碼與主程式執行在同一處理程序中, 因此外掛從建置到載入設有多道防線:

- 來源可查核: 原生程式庫從 OpenCV 官方原始碼的固定提交重建, 工具鏈版本與逐 ABI 雜湊記錄在 `libs/opencv-native-4.8.0.provenance.json` 中, 依照 [NATIVE_BUILD.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/NATIVE_BUILD.md) 可獨立重現比對.
- 建置門禁: 每次建置都會驗證原生程式庫清單, ELF 架構, payload 雜湊, Java API 指紋, 重複 OpenCV Java 類別與授權資源, 發布建置還要求設定受信任的簽章身分.
- 載入前驗證: 主程式依序驗證外掛簽章, OpenCV 版本, 契約版本與 Java API SHA-256, 任何一項不符即拒絕載入.
- 處理程序層級 C++ 執行環境由主程式統一提供並預先載入, 外掛不攜帶 `libc++_shared.so`, 避免不相容執行環境邊界引發的當機.
- 最小權限: 不申請網路與任何敏感系統權限, 無獨立介面, 僅透過 AutoJs6 外掛權限與主程式通訊.

請僅從官方 [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) 頁面或其他可信管道取得外掛. 官方發布套件使用主程式認可的簽章身分; 來源不明的安裝套件即使版本號相同, 也可能無法通過主程式驗證或暗藏風險.

******

### 外掛介面

******

以下資訊面向 AutoJs6 主程式與外掛開發者, 主程式透過這些識別資訊發現外掛並完成相容性協商:

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

同一個 `OpenCvPluginInfoService` 會回應 `org.autojs.plugin.INFO` 與 `org.autojs.plugin.OPENCV` 兩個 action, 皆使用 `opencv-runtime` category; Binder 介面為 common-plugin-api 的 `IPluginInfoProvider`.

`PluginInfo.supportedAbis` 會依據已安裝 APK 中實際存在的 OpenCV 原生程式庫項目動態計算: 單一架構套件只回報自身架構, `universal` 套件回報全部 4 種架構.

******

### 開發路線圖

******

外掛的能力規劃與完成情況以可勾選清單維護在 ROADMAP.md 中, 依里程碑組織並附驗收條件, 涵蓋 16 KB 記憶體分頁支援, OpenCV 版本演進, 持續整合與診斷體驗等方向. 未勾選條目表示規劃意向而非目前版本能力, 歡迎透過 Issues 參與討論.

- [檢視 ROADMAP.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/ROADMAP.md)

******

### 發行記錄

******

#### v1.1.0

_2026/08/31_

- `新增` 發行下載現統一包含 5 個架構安裝套件, `SHA256SUMS.txt` 與原生建置 provenance 清單, 方便依裝置選擇套件並獨立驗證檔案
- `修正` 以 16 KB `PT_LOAD` 對齊重新建置 4 個 ABI 的原生程式庫並加入發行門檻, 使 OpenCV 可在 16 KB page-size Android 裝置上載入, 同時維持對 4 KB 裝置的相容性
- `改善` 擴充外掛中心說明與 10 種語言 README: 加入實際啟用狀態截圖和可直接執行的自我檢查腳本, 輸出 OpenCV 版本與目前行程 ABI
- `改善` 強化支援 ABI 的辨識邏輯: 涵蓋 universal, 單一架構與 split 安裝, 並能在 APK 路徑遺失或損壞時繼續掃描或使用已擷取原生程式庫備援

#### v1.0.0

_2026/07/22_

- `新增` 首個正式版本: 為 AutoJs6 的影像 API 提供 OpenCV 4.8.0 原生執行環境, 主程式保留腳本呼叫的 OpenCV Java API, 外掛攜帶與之精確相符的 `libopencv_java4.so`
- `新增` 支援被 AutoJs6 自動發現與相容性協商: 透過 `org.autojs.plugin.INFO` 與 `org.autojs.plugin.OPENCV` action (`opencv-runtime` category) 向主程式提供版本, 契約與指紋等中繼資料
- `新增` 提供 `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64` 四種單一架構安裝套件與包含全部架構的 `universal` 套件, 並依安裝套件實際內容動態回報支援的架構
- `新增` 外掛資訊, 使用說明, README 與更新記錄涵蓋 10 種語言: 簡體中文, 香港繁體, 台灣繁體, 英文, 法文, 西班牙文, 日文, 韓文, 俄文與阿拉伯文
- `新增` 內建建置門禁: 驗證原生程式庫清單, ELF 架構, payload 雜湊, Java API 指紋, 重複 OpenCV Java 類別與授權資源, 發布建置還要求設定受信任的簽章身分
- `新增` 每個安裝套件內建 OpenCV 4.8.0 及其靜態第三方元件的完整授權原文; `libc++_shared.so` 由相容的 AutoJs6 主程式統一提供並預先載入, 外掛不重複打包
- `修正` 改用 Android NDK 26 (API 24) 從官方原始碼重建 OpenCV 4.8.0 原生程式庫, 使外掛與主程式共用同源 C++ 執行環境, 修正例外跨越不相容執行環境邊界導致 AutoJs6 當機的問題

##### 更多發行記錄可參閱

* [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/assets/doc/CHANGELOG-zh-Hant-TW.md)

******

### 建置與驗證

******

本節適用於希望從原始碼建置外掛的開發者.

建置 debug APK:

```powershell
.\gradlew.bat :app:assembleDebug
```

執行單元測試並驗證 debug 與 release APK 的完整性:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

發布前在不納入版本控制的 `sign.properties` 中設定受信任的簽章身分, 然後執行:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

產生 5 個已簽署 APK, SHA-256 清單與 GitHub Release 說明 (完整流程請參閱 [RELEASING.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/RELEASING.md)):

```bat
scripts\release\prepare-release.bat
```

未設定 `sign.properties` 時仍可建置並驗證 payload, 但產生的 release APK 未簽署, 不可發布.

常規建置直接使用儲存庫內預先建置的原生程式庫 AAR, 無需在本機編譯 OpenCV; 如需從官方原始碼完整重建並核對 provenance, 參閱 [NATIVE_BUILD.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/NATIVE_BUILD.md).

******

### 在地化與文件產生

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

`strings.xml` 提供在地化的外掛描述, `plugin_instruction.md` 提供主程式端顯示的使用說明. README 與更新記錄一律修改 `.readme/` 與 `.changelog/` 下的 JSON 來源檔案, 再執行 `py .python/generate_markdown.py` 重新產生, 產生產物不手動編輯; 執行 `py .python/generate_markdown.py --check` 可校驗來源檔案與產生產物是否同步.

******

### 授權

******

專案程式碼採用 [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/LICENSE). OpenCV 4.8.0 及其靜態第三方元件的授權詳見 [THIRD_PARTY_NOTICES.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/THIRD_PARTY_NOTICES.md), 完整授權原文隨每個 APK 打包在 `assets/licenses/opencv-4.8.0/` 目錄下.

******

### 相關連結

******

- AutoJs6 文件: https://docs.autojs6.com
- OpenCV 官方網站: https://opencv.org
- OpenCV 4.8.0 原始碼: https://github.com/opencv/opencv/tree/4.8.0
