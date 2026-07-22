<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-opencv-ic-launcher" border="0" width="128" />
  </p>

  <p>AutoJs6용 OpenCV 4.8.0 네이티브 런타임 플러그인</p>

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

### 언어 (Languages)

******

현재 README.md는 다음 언어를 지원합니다:

- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 (香港) [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 (台灣) [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-zh-Hant-TW.md)
- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-en.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-fr.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-es.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ja.md)
- 한국어 [ko] # 현재
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ru.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/.readme/README-ar.md)

******

### 소개

******

AutoJs6 OpenCV 플러그인은 AutoJs6 이미지 API에서 사용하는 OpenCV 4.8.0 네이티브 런타임을 제공합니다. 호환성을 우선하는 이 설계에서는 호스트에 OpenCV Java API를 유지하고 플러그인이 각 ABI에 맞는 네이티브 라이브러리를 제공합니다.

******

### 플러그인 계약

******

- 애플리케이션 ID: `io.github.supermonster003.autojs6.plugin.opencv`
- 플러그인 ID: `opencv`
- 엔진: `opencv`
- 변형: `4.8.0`
- 계약 버전: `2`
- 필수 호스트 버전 코드: `5237`
- 네이티브 라이브러리: `opencv_java4`
- 네이티브 NDK 버전: `26.1.10909125`
- Java API SHA-256: `340976552fda3cce525021f0b072427cabf0aa1c786fb80cfc4a3a8105d90b3f`

동일한 `OpenCvPluginInfoService`가 `org.autojs.plugin.INFO` 및 `org.autojs.plugin.OPENCV`에 응답합니다.

두 액션 모두 `opencv-runtime` 카테고리를 사용합니다. Binder 인터페이스는 common-plugin-api의 `IPluginInfoProvider`입니다.

******

### ABI

******

이 프로젝트는 다음 APK 변형을 생성합니다:

```text
arm64-v8a
armeabi-v7a
x86
x86_64
universal
```

`PluginInfo.supportedAbis`는 설치된 APK의 OpenCV 네이티브 라이브러리 항목에서 계산됩니다. 단일 ABI APK는 자체 ABI만 보고하고, universal APK는 네 가지 ABI를 모두 보고합니다.

APK Builder로 여러 ABI용 애플리케이션을 생성하려면 universal 플러그인 APK를 설치하십시오. 단일 ABI 플러그인은 해당 ABI용 OpenCV만 제공할 수 있습니다.

******

### 빌드 및 검증

******

단위 테스트를 실행하고 debug 및 release APK를 검증:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

debug APK만 빌드하려면:

```powershell
.\gradlew.bat :app:assembleDebug
```

게시하기 전에 버전 관리에서 제외된 `sign.properties`에 신뢰할 수 있는 서명 ID를 설정하고 다음을 실행하십시오:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

`sign.properties`를 설정하지 않아도 payload를 빌드하고 검증할 수 있지만, 생성된 release APK는 서명되지 않아 게시할 수 없습니다.

******

### 런타임 동작

******

플러그인에는 독립 실행형 사용자 인터페이스가 없습니다. 설치하고 활성화하면 AutoJs6가 서비스를 검색하고 호스트에 유지된 Java API와 일치하는 네이티브 라이브러리를 로드합니다. 로드된 네이티브 라이브러리는 일반적으로 호스트 프로세스와 수명 주기를 공유하므로 플러그인을 업데이트한 후 AutoJs6를 다시 시작해야 합니다.

플러그인에는 `libopencv_java4.so`만 포함되며 `libc++_shared.so`는 패키징되지 않습니다. 호환되는 AutoJs6 호스트가 프로세스 전체에서 사용하는 이 종속성을 제공하고 먼저 로드하여 하나의 프로세스에서 하나의 C++ 런타임만 사용되도록 해야 합니다.

신뢰할 수 있는 빌드만 설치하십시오. 호스트는 네이티브 라이브러리를 로드하기 전에 플러그인 서명, OpenCV 버전, 계약 버전, Java API SHA-256을 검증합니다. 공식 릴리스는 호스트가 인식하는 서명 ID를 사용해야 합니다.

******

### 릴리스 기록

******

# v1.0.0

###### 2026/07/22

* `기능` AutoJs6용 OpenCV 4.8.0 네이티브 런타임 플러그인 최초 릴리스: 호스트는 OpenCV Java API를 유지하고 플러그인은 `libopencv_java4.so`를 제공
* `기능` `org.autojs.plugin.INFO` 및 `org.autojs.plugin.OPENCV`와 `opencv-runtime` 카테고리를 통한 검색을 지원하고 호스트에 필요한 호환성 메타데이터를 추가
* `기능` `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64` 및 `universal` APK 변형을 추가하고 실제 패키징된 ABI를 동적으로 보고
* `기능` 플러그인 정보, 사용 설명, README 및 CHANGELOG를 스페인어, 프랑스어, 러시아어, 아랍어, 일본어, 한국어, 영어, 간체 중국어, 홍콩 번체 및 대만 번체로 현지화
* `기능` debug 및 release 무결성 검증에서 네이티브 라이브러리 목록, ELF 아키텍처, payload 해시, Java API 지문, 중복 OpenCV Java 클래스 및 라이선스 애셋을 확인; 게시 가능한 빌드 검증에는 서명 설정도 필요
* `기능` OpenCV 4.8.0과 정적으로 연결된 타사 구성 요소의 전체 라이선스 원문을 각 APK에 포함; 플러그인은 `libc++_shared.so`를 패키징하지 않으며 호환되는 AutoJs6 호스트가 이를 제공하고 먼저 로드해야 함
* `수정` OpenCV 4.8.0 네이티브 라이브러리를 Android NDK 26 및 API 24로 재빌드하여 예외가 호환되지 않는 C++ 런타임 경계를 넘어 AutoJs6 호스트를 비정상 종료시키는 문제를 방지

##### 더 많은 릴리스 기록

* [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/assets/doc/CHANGELOG-ko.md)

******

### 리소스 구조

******

```text
.readme/lang_*.json
.changelog/lang_*.json
.python/generate_markdown.py
app/src/main/res/values*/strings.xml
app/src/main/res/raw*/plugin_instruction.md
app/src/main/assets/doc/CHANGELOG-*.md
```

`strings.xml`에는 현지화된 플러그인 설명이 포함되고 `plugin_instruction.md`에는 호스트가 표시하는 설명이 포함됩니다. README와 CHANGELOG는 `.python/generate_markdown.py`가 JSON 소스에서 생성합니다. 각 언어의 전체 CHANGELOG는 `app/src/main/assets/doc` 아래에 패키징됩니다.

******

### 라이선스

******

프로젝트 코드는 [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/LICENSE)에 따라 라이선스가 부여됩니다. OpenCV 4.8.0 및 정적으로 연결된 타사 구성 요소의 라이선스는 [THIRD_PARTY_NOTICES.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/THIRD_PARTY_NOTICES.md)를 참조하십시오. 전체 라이선스 원문은 각 APK의 `assets/licenses/opencv-4.8.0/`에 포함됩니다.

******

### 링크

******

- AutoJs6 문서: https://docs.autojs6.com/
- OpenCV 공식 웹사이트: https://opencv.org/
- OpenCV 4.8.0 소스 코드: https://github.com/opencv/opencv/tree/4.8.0
