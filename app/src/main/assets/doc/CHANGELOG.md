******

### 发行历史

******

# v1.0.0

###### 2026/07/22

* `新增` 首次发布面向 AutoJs6 的 OpenCV 4.8.0 原生运行时插件: 宿主保留 OpenCV Java API, 插件提供 `libopencv_java4.so`
* `新增` 支持通过 `org.autojs.plugin.INFO` 和 `org.autojs.plugin.OPENCV` 以及 `opencv-runtime` category 发现插件, 并提供宿主所需的兼容性元数据
* `新增` 支持 `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64` 和 `universal` APK, 并动态上报实际打包的 ABI
* `新增` 插件信息, 使用说明, README 与 CHANGELOG 均支持西班牙语, 法语, 俄语, 阿拉伯语, 日语, 韩语, 英语, 简体中文, 香港繁体和台湾繁体
* `新增` 新增 debug 和 release 完整性校验, 覆盖原生库清单, ELF 架构, payload 哈希, Java API 指纹, 重复 OpenCV Java 类和许可资源; 可发布构建校验还要求配置签名
* `新增` 每个 APK 均打包 OpenCV 4.8.0 和静态第三方组件的完整许可原文; 插件不打包 `libc++_shared.so`, 兼容的 AutoJs6 宿主必须提供并预加载此进程级依赖
* `修复` 将 OpenCV 4.8.0 原生库改用 Android NDK 26 和 API 24 重建, 避免异常跨越不兼容的 C++ 运行时边界并导致 AutoJs6 宿主崩溃
