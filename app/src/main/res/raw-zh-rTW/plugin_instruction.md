OpenCV 外掛 (OpenCV Plugin) 為 AutoJs6 提供影像處理所需的 OpenCV 4.8.0 原生執行環境. AutoJs6 的找圖, 找色, 模板比對等影像 API 都依賴 OpenCV 完成運算; 安裝本外掛後, 支援外掛化 OpenCV 的 AutoJs6 即可正常使用這些影像功能, 腳本無需任何額外設定.

### 使用方法

1. 從 [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) 頁面下載與裝置相符的外掛 APK 並安裝到執行 AutoJs6 的裝置上; 不確定該選哪個時, 可直接選 `universal` 套件, 或參考下方 `如何選擇安裝套件`.
2. 開啟 AutoJs6 的外掛中心, 確認 `OpenCV` 外掛已被識別並處於啟用狀態.
3. 像平時一樣編寫和執行腳本: 腳本用到影像 API 時, AutoJs6 會自動載入外掛提供的 OpenCV 原生程式庫, 腳本程式碼無需任何改動.
4. 更新或重新安裝外掛後, 先完全退出並重新啟動 AutoJs6, 再繼續執行影像相關腳本, 以確保新版本原生程式庫生效.

若外掛中心未顯示該外掛, 請先將 AutoJs6 升級到較新版本 (內部版本號 5237 及以上). 外掛自身支援 Android 7.0 (API 24) 及以上的裝置.

### 如何選擇安裝套件

每個發行版本包含 5 個 APK, 差別僅在於內建了哪些架構的原生程式庫:

| 安裝套件 | 適用對象 |
|---|---|
| `arm64-v8a` | 絕大多數現代 Android 手機與平板 (64 位元 ARM), 優先選擇 |
| `armeabi-v7a` | 較早期的 32 位元 ARM 裝置 |
| `x86_64` | 64 位元 x86 模擬器與少數 x86 裝置 |
| `x86` | 32 位元 x86 模擬器與少數 x86 裝置 |
| `universal` | 內建全部 4 種架構, 體積最大; 適用於任何裝置, 也是不確定架構時的穩妥選擇 |

如需使用 AutoJs6 的 APK 建置器打包適用於多種架構的應用程式, 必須安裝 `universal` 外掛套件: 單一架構外掛只能為其自身架構提供 OpenCV. 若誤裝了與裝置架構不相符的單一架構套件, 外掛將無法提供可用的原生程式庫, 改為安裝 `universal` 套件即可解決.

### 快速自我檢查

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
