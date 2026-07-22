******

### Release History

******

# v1.0.0

###### 2026/07/22

* `Feature` Released the OpenCV 4.8.0 native runtime plugin for AutoJs6: the host retains the OpenCV Java API while the plugin supplies `libopencv_java4.so`
* `Feature` Added discovery through `org.autojs.plugin.INFO` and `org.autojs.plugin.OPENCV` with the `opencv-runtime` category, including compatibility metadata required by the host
* `Feature` Added APK variants for `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64`, and `universal`, with dynamic reporting of the ABIs actually packaged
* `Feature` Localized plugin metadata, instructions, README, and CHANGELOG for Spanish, French, Russian, Arabic, Japanese, Korean, English, Simplified Chinese, Hong Kong Traditional Chinese, and Taiwan Traditional Chinese
* `Feature` Added debug and release integrity checks for native inventory, ELF architecture, payload hashes, the Java API fingerprint, duplicate OpenCV Java classes, and license assets; publishable verification also requires configured signing
* `Feature` Packaged the complete OpenCV 4.8.0 and static third-party license texts in every APK; the plugin does not package `libc++_shared.so`, which a compatible AutoJs6 host must supply and preload
* `Fix` Rebuilt the OpenCV 4.8.0 native libraries with Android NDK 26 and API 24 to prevent exceptions from crossing incompatible C++ runtime boundaries and crashing the AutoJs6 host
