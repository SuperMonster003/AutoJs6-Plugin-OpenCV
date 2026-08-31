OpenCV 插件 (OpenCV Plugin) 为 AutoJs6 提供图像处理所需的 OpenCV 4.8.0 原生运行时. AutoJs6 的找图, 找色, 模板匹配等图像 API 都依赖 OpenCV 完成运算; 安装本插件后, 支持插件化 OpenCV 的 AutoJs6 即可正常使用这些图像功能, 脚本无需任何额外配置.

### 使用方法

1. 从 [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) 页面下载与设备匹配的插件 APK 并安装到运行 AutoJs6 的设备上; 拿不准选哪个时, 可直接选 `universal` 包, 或参考下方 `如何选择安装包`.
2. 打开 AutoJs6 的插件中心, 确认 `OpenCV` 插件已被识别并处于启用状态.
3. 像平时一样编写和运行脚本: 脚本用到图像 API 时, AutoJs6 会自动加载插件提供的 OpenCV 原生库, 脚本代码无需任何改动.
4. 更新或重装插件后, 先完全退出并重启 AutoJs6, 再继续运行图像相关脚本, 以确保新版本原生库生效.

若插件中心未显示该插件, 请先将 AutoJs6 升级到较新版本 (内部版本号 5237 及以上). 插件自身支持 Android 7.0 (API 24) 及以上的设备.

### 如何选择安装包

每个发行版本包含 5 个 APK, 差别仅在于内置了哪些架构的原生库:

| 安装包 | 适用对象 |
|---|---|
| `arm64-v8a` | 绝大多数现代 Android 手机与平板 (64 位 ARM), 优先选择 |
| `armeabi-v7a` | 较早期的 32 位 ARM 设备 |
| `x86_64` | 64 位 x86 模拟器与少数 x86 设备 |
| `x86` | 32 位 x86 模拟器与少数 x86 设备 |
| `universal` | 内置全部 4 种架构, 体积最大; 适用于任何设备, 也是拿不准架构时的稳妥选择 |

如需使用 AutoJs6 的 APK 构建器打包面向多种架构的应用, 必须安装 `universal` 插件包: 单架构插件只能为其自身架构提供 OpenCV. 若误装了与设备架构不匹配的单架构包, 插件将无法提供可用的原生库, 换装 `universal` 包即可解决.

### 快速自检

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
