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
