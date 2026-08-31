OpenCV 插件 (OpenCV Plugin) 為 AutoJs6 提供圖像處理所需的 OpenCV 4.8.0 原生運行時. AutoJs6 的找圖, 找色, 模板匹配等圖像 API 都依賴 OpenCV 完成運算; 安裝本插件後, 支援插件化 OpenCV 的 AutoJs6 即可正常使用這些圖像功能, 腳本無需任何額外配置.

### 使用方法

1. 從 [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) 頁面下載與裝置匹配的插件 APK 並安裝到運行 AutoJs6 的裝置上; 不確定選哪個時, 可直接選 `universal` 包, 或參考下方 `如何選擇安裝包`.
2. 開啟 AutoJs6 的插件中心, 確認 `OpenCV` 插件已被識別並處於啟用狀態.
3. 像平時一樣編寫和運行腳本: 腳本用到圖像 API 時, AutoJs6 會自動載入插件提供的 OpenCV 原生庫, 腳本代碼無需任何改動.
4. 更新或重裝插件後, 先完全退出並重啟 AutoJs6, 再繼續運行圖像相關腳本, 以確保新版本原生庫生效.

若插件中心未顯示該插件, 請先將 AutoJs6 升級到較新版本 (內部版本號 5237 及以上). 插件自身支援 Android 7.0 (API 24) 及以上的裝置.

### 如何選擇安裝包

每個發行版本包含 5 個 APK, 差別僅在於內置了哪些架構的原生庫:

| 安裝包 | 適用對象 |
|---|---|
| `arm64-v8a` | 絕大多數現代 Android 手機與平板 (64 位 ARM), 優先選擇 |
| `armeabi-v7a` | 較早期的 32 位 ARM 裝置 |
| `x86_64` | 64 位 x86 模擬器與少數 x86 裝置 |
| `x86` | 32 位 x86 模擬器與少數 x86 裝置 |
| `universal` | 內置全部 4 種架構, 體積最大; 適用於任何裝置, 也是不確定架構時的穩妥選擇 |

如需使用 AutoJs6 的 APK 構建器打包面向多種架構的應用程式, 必須安裝 `universal` 插件包: 單架構插件只能為其自身架構提供 OpenCV. 若誤裝了與裝置架構不匹配的單架構包, 插件將無法提供可用的原生庫, 改為安裝 `universal` 包即可解決.

### 快速自檢

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
