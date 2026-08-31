******

### Release History

******

# v1.1.0

###### 2026/08/31

* `Feature` Release downloads now bundle the 5 architecture APKs, `SHA256SUMS.txt`, and the native-build provenance manifest so users can select the right package and verify files independently
* `Fix` Rebuilt all 4 ABI native libraries with 16 KB `PT_LOAD` alignment and added release gates so OpenCV loads on 16 KB page-size Android devices while remaining compatible with 4 KB devices
* `Improvement` Expanded the plugin-center instructions and 10-language README with a real enabled-state screenshot and a runnable self-check that prints the OpenCV version and process ABI
* `Improvement` Made supported-ABI reporting resilient across universal, single-ABI, and split installs, including missing or corrupt APK paths and extracted-library fallback

# v1.0.0

###### 2026/07/22

* `Feature` Initial release: supplies the OpenCV 4.8.0 native runtime behind the AutoJs6 image APIs; the host retains the OpenCV Java API called by scripts, while the plugin carries the exactly matching `libopencv_java4.so`
* `Feature` Automatic discovery and compatibility negotiation with AutoJs6 through the `org.autojs.plugin.INFO` and `org.autojs.plugin.OPENCV` actions (`opencv-runtime` category), exposing version, contract, and fingerprint metadata to the host
* `Feature` Five APK flavors: `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64`, plus an all-in-one `universal` package, with supported architectures reported dynamically from the actual APK contents
* `Feature` Plugin metadata, instructions, README, and changelog available in 10 languages: Simplified Chinese, Traditional Chinese (Hong Kong and Taiwan), English, French, Spanish, Japanese, Korean, Russian, and Arabic
* `Feature` Built-in build gates verifying the native library inventory, ELF architectures, payload hashes, the Java API fingerprint, duplicate OpenCV Java classes, and license assets; publishable builds additionally require a trusted signing identity
* `Feature` Complete license texts of OpenCV 4.8.0 and its statically linked third-party components packaged in every APK; `libc++_shared.so` is supplied and preloaded by a compatible AutoJs6 host instead of being duplicated in the plugin
* `Fix` Rebuilt the OpenCV 4.8.0 native libraries from official sources with Android NDK 26 (API 24) so that plugin and host share the same C++ runtime family, fixing AutoJs6 crashes caused by exceptions crossing incompatible runtime boundaries
