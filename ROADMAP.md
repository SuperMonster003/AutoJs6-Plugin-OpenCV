# AutoJs6 OpenCV Plugin Roadmap

更新日期: 2026-08-31

本文档是 OpenCV 插件从"单版本原生运行时"逐步演进为"可持续演进, 可自助诊断, 可独立复核"的运行时交付方案的执行清单. 每个条目只有在代码/测试与可验证的验收条件同时满足后才可勾选.

## 状态与证据规则

- `[x]`: 已完成, 且本仓库存在可复核证据 (代码, 测试, 脚本或生成产物).
- `[ ]`: 尚未完成; 括号中的 `插件` / `API` / `宿主` / `测试` / `发布` 表示主要落点.
- 未勾选条目属于规划意向, 不代表当前版本能力; 依赖宿主或契约的条目需等待对应仓库先行支持.

## 总览

| 里程碑 | 状态 | 核心结果 | 主要落点 |
|---|---|---|---|
| M0 基线运行时 | 已完成 | OpenCV 4.8.0 原生运行时与完整性门禁 | 插件/发布 |
| M1 文档与发布体验 | 进行中 | 用户导向文档, 文档 CI 与发布物料 | 发布 |
| M2 工程化与持续集成 | 已完成 | 构建/测试/校验流水线与测试矩阵 | 测试/发布 |
| M3 诊断与兼容性体验 | 已完成 | 自检脚本与"未生效"问题的快速定位 | 插件/发布 |
| M4 原生运行时演进 | 进行中 | 16 KB 内存页适配与 OpenCV 新变体 | 插件/API/宿主 |

依赖顺序:

```text
M0 ──> M1 ──> M2 ──> M3
        └──────────> M4 (新变体与契约条目需宿主先行)
```

## M0: 基线运行时 (v1.0.0, 已完成)

- [x] (插件) 以插件形式提供 OpenCV 4.8.0 原生运行时: 宿主保留 OpenCV Java API, 插件携带 4 个 ABI 的 `libopencv_java4.so` (`app/src/main/java/.../opencv/PluginRuntimeInfo.kt`).
- [x] (插件) 插件发现与兼容性协商: `OpenCvPluginInfoService` 响应 `org.autojs.plugin.INFO` 与 `org.autojs.plugin.OPENCV` (`opencv-runtime` category), 通过 `IPluginInfoProvider` 上报 id/engine/variant, 契约版本, NDK 版本与 Java API SHA-256; 另提供 `WakeActivity` 供宿主唤醒插件进程.
- [x] (插件) `PluginInfo.supportedAbis` 按已安装 APK 的实际原生库条目动态计算, 并为 split 安装场景提供回退 (`NativeLibraryInventory.kt`).
- [x] (插件) 五种 APK 变体: `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64` 与 `universal`.
- [x] (发布) 原生库从 OpenCV 官方源码固定提交定点重建 (NDK r26b, API 24), 逐 ABI 哈希与 Build ID 记录于 `libs/opencv-native-4.8.0.provenance.json`, 重建流程见 `NATIVE_BUILD.md` 与 `scripts/opencv/build-native-aar.ps1`.
- [x] (测试/发布) 构建门禁 `:app:verifyOpenCvApks` / `:app:verifyOpenCvPublishableApks`: 覆盖原生库清单, ELF 架构, payload 哈希, Java API 指纹, 重复 OpenCV Java 类, 许可资源与发布签名要求.
- [x] (发布) 多语言资源: strings.xml, plugin_instruction.md, README 与 CHANGELOG 覆盖 10 种语言.

验收条件: 在版本代码不低于 5237 的 AutoJs6 中安装并启用后, 宿主可发现插件, 通过全部兼容性校验并加载与设备 ABI 匹配的原生库. (已满足)

## M1: 文档与发布体验 (进行中)

- [x] (发布) README 重构为用户导向结构: 简介 / 功能亮点 / 使用方法 / 如何选择安装包 / 常见问题 / 权限与安全 / 插件接口 / 开发路线图, 10 种语言全部由 JSON 源再生成.
- [x] (发布) CHANGELOG 文案面向用户重写: 每条先讲可感知的结果, 再补技术细节, 10 种语言同步.
- [x] (发布) 文档生成脚本升级至同族插件最新实现 (`.python/generate_markdown.py`): 新增 `--check` 漂移检测, 跨语言键位与列表形状对齐校验, 产物清点与孤儿文件检测, 全角符号与占位符残留拦截.
- [x] (发布) 新增 `.python/check_markdown.bat` 与 GitHub Actions 工作流 `.github/workflows/markdown.yml`, push/PR 自动校验文档源与产物同步.
- [x] (发布) 新建本 ROADMAP.md, 并在 README 增加"开发路线图"章节挂链.
- [x] (发布) Releases 页面发布说明模板化: `.github/RELEASE_TEMPLATE.md` 提供选包速查表, `scripts/release/prepare_release.py` 从实际 5 个 APK 与当前英文 CHANGELOG 生成无占位符的 `RELEASE_NOTES.md`, 并逐包写入 SHA-256 与 `SHA256SUMS.txt`; 经 AAR 哈希与 16 KB 对齐元数据复核的 provenance JSON 同步归集, 完整发布清单见 `RELEASING.md`.
- [x] (发布) 提供插件在 AutoJs6 插件中心内被识别与启用状态的真实界面截图 (`docs/images/screenshots/plugin-center-enabled.png`), 由 `.readme/template_readme.md` 与 10 语言说明共同接入 README 生成链路, 文档门禁同时校验 PNG 签名与最小尺寸.
- [ ] (发布) 文档整改后的首个对外版本: 更新 `version.properties`, 补充对应 CHANGELOG 条目并发布 Release.

验收条件: `py .python/generate_markdown.py --check` 在本地与 CI 全绿; 新用户仅凭 README 即可独立完成选包, 安装与生效确认.

## M2: 工程化与持续集成 (已完成)

- [x] (测试) GitHub Actions 构建流水线: `.github/workflows/build.yml` 在 push/PR 上运行 Release 生成器自测, `:app:testDebugUnitTest` 与 `:app:verifyOpenCvApks`, 守护单元行为及全部 debug/release APK 的完整性.
- [x] (测试) 扩充 `NativeLibraryInventoryTest` 用例矩阵: universal/单架构/无原生库/split 安装/缺失或损坏的多 APK 路径/提取目录回退等分支均有 JVM 用例.
- [x] (测试) instrumentation 用例: `OpenCvPluginInfoServiceTest` 通过两个 discovery action 绑定服务, 断言 `PluginInfo` 的 id/engine/variant/version/supportedAbis/instruction 与全部 capabilities; `.github/workflows/build.yml` 配置 arm64-v8a/x86_64 模拟器矩阵, 本地另已在 x86_64 模拟器与 arm64-v8a 真机实跑通过.
- [x] (发布) Release 产物脚本化: `scripts/release/prepare-release.bat` 先执行 `:app:verifyOpenCvPublishableApks`, 再一键归集 5 个已签名 APK, provenance JSON, `SHA256SUMS.txt` 与 `RELEASE_NOTES.md`; Python 自测覆盖缺包, 混包, provenance/AAR 漂移, 篡改与未知输出保护.

验收条件: 主分支每次提交自动完成构建, 单元测试与 APK 完整性校验; 发布产物由脚本生成且哈希可追溯.

## M3: 诊断与兼容性体验 (已完成)

- [x] (发布) 官方自检示例: README 与宿主插件说明均提供 AutoJs6 脚本, 由 `images.initOpenCvIfNeeded()` 实际触发发现/兼容性校验/原生加载, 再输出 `Core.getVersionString()` 与当前进程 ABI; 结果说明按 universal 包, 宿主版本与进程重启三步排障.
- [x] (插件) "未生效"场景的元数据完备性复查: application/service manifest 双层元数据继续由 APK 门禁校验; `OpenCvPluginInfoServiceTest` 进一步从 Binder 断言版本, 契约, NDK, 原生库名, Java API 指纹, 最低宿主版本与 ABI 均可读取 (具体拒绝提示文案仍由宿主负责).
- [x] (插件/发布) 扩写 `plugin_instruction.md` (宿主插件中心内的使用说明): 补充选包, 启用, 重启, 自检与三步排障指引; `.readme/template_plugin_instruction.md` 与同一组 10 语言 JSON 生成 11 个 Android resource 产物, 已纳入 `.python/generate_markdown.py --check`, 消除双源维护.

验收条件: 用户遇到"插件未生效"问题时, 依照 FAQ 与自检脚本可在 3 步内定位到架构不匹配, 宿主过旧或需要重启三类原因之一.

## M4: 原生运行时演进 (进行中)

- [x] (插件/发布) 16 KB 内存页适配: 使用 NDK r26b 与显式 `-Wl,-z,max-page-size=16384` 从固定 OpenCV 4.8.0 提交重建 4 个 ABI 原生库; provenance schema v2 记录每个 ELF 的 `PT_LOAD` 对齐值, 并已在 16 KB page-size x86_64 AVD 中通过 AutoJs6 宿主的发现, 加载, 运算与 native 异常传播测试.
- [x] (测试) 校验门禁增加直接解析 ELF32/ELF64 program header 的 `PT_LOAD` 对齐检查, 同时校验 offset/vaddr 同余与 provenance 对齐字段, 防止未对齐 AAR 或 APK 产物混入发布.
- [ ] (API/宿主) OpenCV 新变体 (如 4.1x): 待宿主发布对应的 OpenCV Java API 与契约支持后, 以并行 variant 形式发布新插件包, 4.8.0 包继续服役, 互不影响.
- [ ] (插件) 契约版本演进跟进: 宿主发布 contract v3 后补充所需元数据并保持对 v2 宿主的兼容.
- [ ] (发布) provenance JSON 随每个 Release 附带, 并提供第三方复现核对指引. (本地 Release 脚本与说明已自动归集/校验该资产; 待首个对外 Release 实际上传并回下载核验后勾选.)

验收条件: 16 KB 构建与新变体均通过既有完整性门禁与真机加载验证, 且现有设备行为不回归; 新变体条目仅在宿主发布对应能力后开始实施.

## 边界 (非目标)

- 不修改 OpenCV 源码: 仅从官方源码定点重建, 不携带任何自定义补丁; 功能层面的图像 API 由 AutoJs6 宿主定义.
- 不提供脚本 API 与独立界面: 插件不新增脚本可调用的接口, 不添加桌面入口, 不接受第三方应用调用.
- 不打包 `libc++_shared.so`: 进程级 C++ 运行时始终由兼容的 AutoJs6 宿主统一提供并预加载.
- 不在插件内实现图像算法定制: 算法能力以 OpenCV 官方发行版为准.
