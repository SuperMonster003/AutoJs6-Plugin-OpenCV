<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-opencv-ic-launcher" border="0" width="128" />
  </p>

  <p>为 AutoJs6 提供 OpenCV 4.8.0 原生运行时</p>

  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases"><img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-OpenCV?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/issues"><img alt="GitHub closed issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-OpenCV?color=A24232&label=Issues"/></a>
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

OpenCV 插件 (OpenCV Plugin) 为 AutoJs6 提供图像处理所需的 OpenCV 4.8.0 原生运行时. AutoJs6 的找图, 找色, 模板匹配等图像 API 都依赖 OpenCV 完成运算; 安装本插件后, 支持插件化 OpenCV 的 AutoJs6 即可正常使用这些图像功能, 脚本无需任何额外配置.

插件采用兼容优先的分工设计: AutoJs6 宿主保留脚本直接调用的 OpenCV Java API, 插件携带与之精确匹配的原生库 `libopencv_java4.so`. 这样宿主安装包保持精简, 设备只需安装与自身处理器架构 (ABI) 匹配的插件包, OpenCV 运行时也可以独立于宿主更新.

******

### 功能亮点

******

- 开箱即用: 安装后无需任何配置, AutoJs6 自动发现插件并在运行图像脚本时按需加载 OpenCV 运行时.
- 五种安装包: 提供 `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64` 四种单架构包与包含全部架构的 `universal` 包, 按设备按需选择, 体积可控.
- 官方源码构建: 原生库从未经修改的 OpenCV 4.8.0 官方源码定点重建 (NDK r26b, API 24), 构建输入与逐 ABI 哈希完整记录在 provenance 清单中, 可复现可比对.
- 加载前多重核验: 宿主会先校验插件签名, OpenCV 版本, 契约版本与 Java API 指纹, 全部通过才加载原生库, 拒绝不匹配或被篡改的运行时.
- 进程内单一 C++ 运行时: 插件不重复打包 `libc++_shared.so`, 与宿主共享同一份进程级依赖, 避免多份 C++ 运行时共存引发的崩溃.
- 许可透明: 每个安装包内置 OpenCV 及其静态第三方组件的完整许可原文, 并附汇总说明 THIRD_PARTY_NOTICES.md.
- 多语言: 插件信息, 使用说明, README 与更新日志覆盖 10 种语言.

******

### 使用方法

******

1. 从 [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) 页面下载与设备匹配的插件 APK 并安装到运行 AutoJs6 的设备上; 拿不准选哪个时, 可直接选 `universal` 包, 或参考下方 `如何选择安装包`.
2. 打开 AutoJs6 的插件中心, 确认 `OpenCV` 插件已被识别并处于启用状态.
3. 像平时一样编写和运行脚本: 脚本用到图像 API 时, AutoJs6 会自动加载插件提供的 OpenCV 原生库, 脚本代码无需任何改动.
4. 更新或重装插件后, 先完全退出并重启 AutoJs6, 再继续运行图像相关脚本, 以确保新版本原生库生效.

> 若插件中心未显示该插件, 请先将 AutoJs6 升级到较新版本 (内部版本号 5237 及以上). 插件自身支持 Android 7.0 (API 24) 及以上的设备.

<p align="center">
  <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/docs/images/screenshots/plugin-center-enabled.png?raw=true" alt="AutoJs6 插件中心已识别 OpenCV 1.1.0, 且开关处于启用状态." width="480" />
</p>
<p align="center"><sub>AutoJs6 插件中心已识别 OpenCV 1.1.0, 且开关处于启用状态.</sub></p>

******

### 如何选择安装包

******

每个发行版本包含 5 个 APK, 差别仅在于内置了哪些架构的原生库:

| 安装包 | 适用对象 |
|---|---|
| `arm64-v8a` | 绝大多数现代 Android 手机与平板 (64 位 ARM), 优先选择 |
| `armeabi-v7a` | 较早期的 32 位 ARM 设备 |
| `x86_64` | 64 位 x86 模拟器与少数 x86 设备 |
| `x86` | 32 位 x86 模拟器与少数 x86 设备 |
| `universal` | 内置全部 4 种架构, 体积最大; 适用于任何设备, 也是拿不准架构时的稳妥选择 |

如需使用 AutoJs6 的 APK 构建器打包面向多种架构的应用, 必须安装 `universal` 插件包: 单架构插件只能为其自身架构提供 OpenCV. 若误装了与设备架构不匹配的单架构包, 插件将无法提供可用的原生库, 换装 `universal` 包即可解决.

******

### 快速自检

******

确认 AutoJs6 插件中心已显示并启用 OpenCV, 重启 AutoJs6 后运行以下脚本. `images.initOpenCvIfNeeded()` 会实际触发插件发现, 兼容性校验与原生库加载, 而不只是读取元数据:

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

成功时会输出 `OpenCV version: 4.8.0` 与 `arm64-v8a` 等进程 ABI. 若加载失败, 请依次排查: 换装 `universal` 包, 将 AutoJs6 升级到内部版本号 5237 或更高版本, 最后完全退出并重启 AutoJs6.

******

### 常见问题

******

#### 如何确认插件已经生效?

打开 AutoJs6 的插件中心, 能看到 `OpenCV` 插件即表示宿主已识别. 随后运行任意用到图像 API 的脚本, 如能正常返回结果, 说明原生库已成功加载.

#### 为什么应用列表里没有插件的图标?

这是正常现象. 插件没有独立界面, 也不在桌面创建启动图标, 安装后由 AutoJs6 在后台自动发现和调用, 全部交互都在 AutoJs6 内完成.

#### 更新插件后图像功能异常, 或感觉仍在使用旧版本?

原生库一旦加载就会伴随宿主进程存续, 更新插件不会替换正在运行中的旧库. 完全退出并重启 AutoJs6 后, 新版本原生库即可生效.

#### 提示宿主版本过低或插件不兼容, 怎么办?

本插件要求 AutoJs6 内部版本号达到 5237 及以上, 请先升级 AutoJs6. 宿主在加载前会校验契约版本与 Java API 指纹, 两端不匹配时会直接拒绝加载, 而不是带着隐患运行.

#### 插件已安装, 图像功能仍不可用, 可能是什么原因?

最常见的原因是安装包与设备架构不匹配: 单架构包只对自身架构生效. 可先换装 `universal` 包排除架构因素; 若仍无效, 请确认 AutoJs6 版本满足要求, 并在重启 AutoJs6 后重试.

#### 插件会联网或申请敏感权限吗?

不会. 插件清单不含网络, 存储, 相机等任何敏感系统权限, 仅声明用于与 AutoJs6 通信的插件权限. 它的唯一职责是把 OpenCV 原生库交给宿主加载.

#### 为什么提供的是 OpenCV 4.8.0 而不是更新的版本?

插件的原生库必须与宿主保留的 OpenCV Java API 严格匹配 (通过 SHA-256 指纹核验), 因此 OpenCV 版本由宿主与插件的契约共同锁定. 更新的 OpenCV 版本将在宿主支持后以新变体形式跟进, 进展可关注 [ROADMAP.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/ROADMAP.md).

******

### 权限与安全

******

原生代码与宿主运行在同一进程中, 因此插件从构建到加载设有多道防线:

- 来源可复核: 原生库从 OpenCV 官方源码的固定提交重建, 工具链版本与逐 ABI 哈希记录在 `libs/opencv-native-4.8.0.provenance.json` 中, 依照 [NATIVE_BUILD.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/NATIVE_BUILD.md) 可独立复现比对.
- 构建门禁: 每次构建都会校验原生库清单, ELF 架构, payload 哈希, Java API 指纹, 重复 OpenCV Java 类与许可资源, 发布构建还要求配置受信任的签名身份.
- 加载前核验: 宿主依次校验插件签名, OpenCV 版本, 契约版本与 Java API SHA-256, 任何一项不符即拒绝加载.
- 进程级 C++ 运行时由宿主统一提供并预加载, 插件不携带 `libc++_shared.so`, 避免不兼容运行时边界引发的崩溃.
- 最小权限: 不申请网络与任何敏感系统权限, 无独立界面, 仅通过 AutoJs6 插件权限与宿主通信.

请仅从官方 [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) 页面或其他可信渠道获取插件. 官方发布包使用宿主认可的签名身份; 来源不明的安装包即使版本号相同, 也可能无法通过宿主校验或暗藏风险.

******

### 插件接口

******

以下信息面向 AutoJs6 宿主与插件开发者, 宿主通过这些标识发现插件并完成兼容性协商:

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

同一个 `OpenCvPluginInfoService` 响应 `org.autojs.plugin.INFO` 与 `org.autojs.plugin.OPENCV` 两个 action, 均使用 `opencv-runtime` category; Binder 接口为 common-plugin-api 的 `IPluginInfoProvider`.

`PluginInfo.supportedAbis` 依据已安装 APK 中实际存在的 OpenCV 原生库条目动态计算: 单架构包只上报自身架构, `universal` 包上报全部 4 种架构.

******

### 开发路线图

******

插件的能力规划与完成情况以可勾选清单维护在 ROADMAP.md 中, 按里程碑组织并附验收条件, 涵盖 16 KB 内存页适配, OpenCV 版本演进, 持续集成与诊断体验等方向. 未勾选条目表示规划意向而非当前版本能力, 欢迎通过 Issues 参与讨论.

- [查看 ROADMAP.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/ROADMAP.md)

******

### 发行历史

******

#### v1.1.0

_2026/08/31_

- `新增` 发布下载现统一包含 5 个架构安装包, `SHA256SUMS.txt` 与原生构建 provenance 清单, 便于按设备选包并独立核验文件
- `修复` 以 16 KB `PT_LOAD` 对齐重新构建 4 个 ABI 的原生库并加入发布门禁, 使 OpenCV 可在 16 KB page-size Android 设备上加载, 同时保持对 4 KB 设备的兼容
- `优化` 扩充插件中心说明与 10 语言 README: 加入真实启用状态截图和可直接运行的自检脚本, 输出 OpenCV 版本与当前进程 ABI
- `优化` 增强支持 ABI 的识别逻辑: 覆盖 universal, 单架构与 split 安装, 并能在 APK 路径缺失或损坏时继续扫描或使用已提取原生库回退

#### v1.0.0

_2026/07/22_

- `新增` 首个正式版本: 为 AutoJs6 的图像 API 提供 OpenCV 4.8.0 原生运行时, 宿主保留脚本调用的 OpenCV Java API, 插件携带与之精确匹配的 `libopencv_java4.so`
- `新增` 支持被 AutoJs6 自动发现与兼容性协商: 通过 `org.autojs.plugin.INFO` 与 `org.autojs.plugin.OPENCV` action (`opencv-runtime` category) 向宿主提供版本, 契约与指纹等元数据
- `新增` 提供 `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64` 四种单架构安装包与包含全部架构的 `universal` 包, 并按安装包实际内容动态上报支持的架构
- `新增` 插件信息, 使用说明, README 与更新日志覆盖 10 种语言: 简体中文, 香港繁体, 台湾繁体, 英语, 法语, 西班牙语, 日语, 韩语, 俄语与阿拉伯语
- `新增` 内置构建门禁: 校验原生库清单, ELF 架构, payload 哈希, Java API 指纹, 重复 OpenCV Java 类与许可资源, 发布构建还要求配置受信任的签名身份
- `新增` 每个安装包内置 OpenCV 4.8.0 及其静态第三方组件的完整许可原文; `libc++_shared.so` 由兼容的 AutoJs6 宿主统一提供并预加载, 插件不重复打包
- `修复` 改用 Android NDK 26 (API 24) 从官方源码重建 OpenCV 4.8.0 原生库, 使插件与宿主共用同源 C++ 运行时, 修复异常跨越不兼容运行时边界导致 AutoJs6 崩溃的问题

##### 更多发行历史可参阅

* [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/assets/doc/CHANGELOG-zh-Hans.md)

******

### 构建与校验

******

本节面向希望从源码构建插件的开发者.

构建 debug APK:

```powershell
.\gradlew.bat :app:assembleDebug
```

运行单元测试并校验 debug 与 release APK 的完整性:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

发布前在不入库的 `sign.properties` 中配置受信任的签名身份, 然后运行:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

生成 5 个已签名 APK, SHA-256 清单与 GitHub Release 说明 (完整流程见 [RELEASING.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/RELEASING.md)):

```bat
scripts\release\prepare-release.bat
```

未配置 `sign.properties` 时仍可构建并校验 payload, 但生成的 release APK 未签名, 不可发布.

常规构建直接使用仓库内预构建的原生库 AAR, 无需本地编译 OpenCV; 如需从官方源码完整重建并核对 provenance, 参见 [NATIVE_BUILD.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/NATIVE_BUILD.md).

******

### 本地化与文档生成

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

`strings.xml` 提供本地化插件描述, `plugin_instruction.md` 提供宿主侧展示的使用说明. README 与更新日志一律修改 `.readme/` 与 `.changelog/` 下的 JSON 源文件, 再运行 `py .python/generate_markdown.py` 重新生成, 生成产物不手工编辑; 运行 `py .python/generate_markdown.py --check` 可校验源文件与生成产物是否同步.

******

### 许可

******

项目代码使用 [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/LICENSE). OpenCV 4.8.0 及其静态第三方组件的许可详见 [THIRD_PARTY_NOTICES.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/THIRD_PARTY_NOTICES.md), 完整许可原文随每个 APK 打包在 `assets/licenses/opencv-4.8.0/` 目录下.

******

### 相关链接

******

- AutoJs6 文档: https://docs.autojs6.com
- OpenCV 官方网站: https://opencv.org
- OpenCV 4.8.0 源码: https://github.com/opencv/opencv/tree/4.8.0
