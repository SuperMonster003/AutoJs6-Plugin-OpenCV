# AutoJs6 OpenCV Plugin

AutoJs6 的 OpenCV 4.8.0 原生运行时插件. 此项目采用兼容优先的第一阶段拆分方案: AutoJs6 宿主保留 OpenCV Java API, 插件提供各 ABI 的 `libopencv_java4.so`.

## 插件契约

- 应用包名: `io.github.supermonster003.autojs6.plugin.opencv`
- 插件 ID: `opencv`
- 引擎: `opencv`
- 变体: `4.8.0`
- 契约版本: `1`
- 最低宿主版本代码: `5236`
- 原生库: `opencv_java4`
- Java API SHA-256: `340976552fda3cce525021f0b072427cabf0aa1c786fb80cfc4a3a8105d90b3f`

同一个 `OpenCvPluginInfoService` 响应以下两个 action:

- `org.autojs.plugin.INFO`
- `org.autojs.plugin.OPENCV`

两者均使用 category `opencv-runtime`, Binder 接口为 common-plugin-api 的 `IPluginInfoProvider`.

## ABI

项目生成以下 APK:

- `arm64-v8a`
- `armeabi-v7a`
- `x86`
- `x86_64`
- `universal`

`PluginInfo.supportedAbis` 从实际安装 APK 中的 OpenCV 原生库条目动态计算. 单 ABI 包只上报自身 ABI, universal 包上报全部四种 ABI.

如需使用 APK 构建器生成多个 ABI 的应用, 请安装 universal 插件包; 单 ABI 插件只能为其自身 ABI 提供 OpenCV 原生库.

## 构建与校验

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

仅构建 APK:

```powershell
.\gradlew.bat :app:assembleDebug
```

`verifyOpenCvApks` 会同时验证 debug/release 的四个单 ABI 包和 universal 包, 包括原生库清单, ELF 架构, payload 哈希, Java API 指纹, 以及插件 APK 不得重复包含 OpenCV Java 类.

发布前需在不会提交到版本库的 `sign.properties` 中配置受信任的签名, 并运行:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

未配置 `sign.properties` 时仍可构建和校验 payload, 但生成的 release APK 未签名, 不可发布.

## 运行方式

插件没有独立操作界面. 安装并启用后, AutoJs6 会自动发现其服务并加载与宿主 Java API 匹配的原生库. 原生库加载后通常与宿主进程保持相同生命周期, 因此插件升级后应重启 AutoJs6.

插件只携带 `libopencv_java4.so`. OpenCV 所需的进程级 `libc++_shared.so` 由 AutoJs6 宿主提供并先行加载, 以确保同一进程只使用一个 C++ 运行时.

请只安装可信构建. 宿主会在加载原生库前校验插件签名, OpenCV 版本, 契约版本和 Java API SHA-256. 官方发布包应使用宿主认可的签名身份.

## 许可

本项目代码使用 [Mozilla Public License 2.0](LICENSE). 打包的 OpenCV 4.8.0 及其静态第三方组件许可详见 [THIRD_PARTY_NOTICES.md](THIRD_PARTY_NOTICES.md). 完整许可原文会随每个 APK 一同打包到 `assets/licenses/opencv-4.8.0/`.
