<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-opencv-ic-launcher" border="0" width="128" />
  </p>

  <p>为 AutoJs6 提供 OpenCV 4.8.0 原生运行时</p>

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

### 语言 (Languages)

******

当前 README.md 支持以下语言:

- 简体中文 [zh-Hans] # 当前
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hant-TW.md)
- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-en.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-fr.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-es.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ru.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ar.md)

******

### 简介

******

AutoJs6 OpenCV 插件为 AutoJs6 图像 API 提供 OpenCV 4.8.0 原生运行时. 此兼容优先方案由宿主保留 OpenCV Java API, 插件则为各 ABI 提供与其匹配的原生库.

******

### 插件契约

******

- 应用包名: `io.github.supermonster003.autojs6.plugin.opencv`
- 插件 ID: `opencv`
- 引擎: `opencv`
- 变体: `4.8.0`
- 契约版本: `2`
- 最低宿主版本代码: `5237`
- 原生库: `opencv_java4`
- 原生 NDK 版本: `26.1.10909125`
- Java API SHA-256: `340976552fda3cce525021f0b072427cabf0aa1c786fb80cfc4a3a8105d90b3f`

同一个 `OpenCvPluginInfoService` 响应 `org.autojs.plugin.INFO` 和 `org.autojs.plugin.OPENCV`.

两个 action 均使用 `opencv-runtime` category. Binder 接口为 common-plugin-api 的 `IPluginInfoProvider`.

******

### ABI

******

项目生成以下 APK 变体:

```text
arm64-v8a
armeabi-v7a
x86
x86_64
universal
```

`PluginInfo.supportedAbis` 根据已安装 APK 中的 OpenCV 原生库条目计算. 单 ABI APK 只上报自身 ABI, universal APK 则上报全部四种 ABI.

如需使用 APK 构建器生成多个 ABI 的应用, 请安装 universal 插件包. 单 ABI 插件只能为其自身 ABI 提供 OpenCV.

******

### 构建与校验

******

运行单元测试并校验 debug 和 release APK:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

如只需构建 debug APK:

```powershell
.\gradlew.bat :app:assembleDebug
```

发布前需在不会提交到版本库的 `sign.properties` 中配置受信任的签名身份, 然后运行:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

未配置 `sign.properties` 时仍可构建并校验 payload, 但生成的 release APK 未签名, 不可发布.

******

### 运行方式

******

插件没有独立操作界面. 安装并启用后, AutoJs6 会发现其服务并加载与宿主所保留 Java API 匹配的原生库. 已加载的原生库通常与宿主进程保持相同生命周期, 因此插件更新后应重启 AutoJs6.

插件只携带 `libopencv_java4.so`, 不打包 `libc++_shared.so`. 兼容的 AutoJs6 宿主必须提供并预加载此进程级依赖, 以确保同一进程只使用一个 C++ 运行时.

请只安装可信构建. 宿主会在加载原生库前校验插件签名, OpenCV 版本, 契约版本和 Java API SHA-256. 官方发布包应使用宿主认可的签名身份.

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

##### 更多发行历史可参阅

* [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/assets/doc/CHANGELOG-zh-Hans.md)

******

### 资源结构

******

```text
.readme/lang_*.json
.changelog/lang_*.json
.python/generate_markdown.py
app/src/main/res/values*/strings.xml
app/src/main/res/raw*/plugin_instruction.md
app/src/main/assets/doc/CHANGELOG-*.md
```

`strings.xml` 提供本地化插件描述, `plugin_instruction.md` 提供宿主侧展示的说明. README 与 CHANGELOG 由 `.python/generate_markdown.py` 根据 JSON 源文件生成; 完整多语言更新日志打包在 `app/src/main/assets/doc` 中.

******

### 许可

******

项目代码使用 [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/LICENSE). OpenCV 4.8.0 及其静态第三方组件许可详见 [THIRD_PARTY_NOTICES.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/THIRD_PARTY_NOTICES.md). 完整许可原文会随每个 APK 一同打包到 `assets/licenses/opencv-4.8.0/`.

******

### 相关链接

******

- AutoJs6 文档: https://docs.autojs6.com/
- OpenCV 官方网站: https://opencv.org/
- OpenCV 4.8.0 源码: https://github.com/opencv/opencv/tree/4.8.0
