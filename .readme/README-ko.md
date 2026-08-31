<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/res/mipmap/ic_launcher.png?raw=true" alt="autojs6-plugin-opencv-ic-launcher" border="0" width="128" />
  </p>

  <p>AutoJs6용 OpenCV 4.8.0 네이티브 런타임 플러그인</p>

  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases"><img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-OpenCV?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/issues"><img alt="GitHub closed issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-OpenCV?color=A24232&label=Issues"/></a>
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

OpenCV 플러그인 (OpenCV Plugin)은 AutoJs6의 이미지 처리에 필요한 OpenCV 4.8.0 네이티브 런타임을 제공합니다. AutoJs6의 이미지 찾기, 색상 감지, 템플릿 매칭 등 이미지 API는 모두 OpenCV의 연산에 의존합니다. 이 플러그인을 설치하면 플러그인형 OpenCV를 지원하는 AutoJs6에서 이러한 이미지 기능을 정상적으로 사용할 수 있으며, 스크립트에 추가 설정이 전혀 필요 없습니다.

플러그인은 호환성을 우선하는 역할 분담 설계를 채택합니다: AutoJs6 호스트는 스크립트가 직접 호출하는 OpenCV Java API를 유지하고, 플러그인은 이에 정확히 일치하는 네이티브 라이브러리 `libopencv_java4.so`를 담습니다. 이렇게 하면 호스트 설치 패키지는 가볍게 유지되고, 기기는 자체 프로세서 아키텍처 (ABI)에 맞는 플러그인 패키지만 설치하면 되며, OpenCV 런타임을 호스트와 독립적으로 업데이트할 수도 있습니다.

******

### 주요 기능

******

- 설치 즉시 사용: 설치 후 어떤 설정도 필요 없으며, AutoJs6가 플러그인을 자동으로 발견하고 이미지 스크립트를 실행할 때 필요에 따라 OpenCV 런타임을 로드합니다.
- 다섯 가지 설치 패키지: `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64` 네 가지 단일 아키텍처 패키지와 모든 아키텍처를 포함한 `universal` 패키지를 제공하여, 기기에 맞게 필요한 것만 선택할 수 있고 용량도 조절할 수 있습니다.
- 공식 소스 빌드: 네이티브 라이브러리는 수정되지 않은 OpenCV 4.8.0 공식 소스에서 고정된 조건으로 재빌드되며 (NDK r26b, API 24), 빌드 입력과 ABI별 해시가 provenance 매니페스트에 완전하게 기록되어 재현과 대조가 가능합니다.
- 로드 전 다중 검증: 호스트는 먼저 플러그인 서명, OpenCV 버전, 계약 버전, Java API 지문을 검증하고 모두 통과해야만 네이티브 라이브러리를 로드하며, 일치하지 않거나 변조된 런타임은 거부합니다.
- 프로세스 내 단일 C++ 런타임: 플러그인은 `libc++_shared.so`를 중복 패키징하지 않고 호스트가 제공하는 프로세스 수준 종속성을 공유하여, 여러 C++ 런타임이 공존해 일으키는 비정상 종료를 방지합니다.
- 투명한 라이선스: 각 설치 패키지에 OpenCV와 정적으로 연결된 타사 구성 요소의 전체 라이선스 원문을 내장하고, 요약 문서인 THIRD_PARTY_NOTICES.md를 함께 수록합니다.
- 다국어: 플러그인 정보, 사용 설명, README 및 CHANGELOG를 10개 언어로 제공합니다.

******

### 사용 방법

******

1. [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) 페이지에서 기기에 맞는 플러그인 APK를 내려받아 AutoJs6가 실행되는 기기에 설치합니다. 어떤 것을 선택할지 확실하지 않으면 `universal` 패키지를 선택하거나 아래의 `설치 패키지 선택 방법`을 참고하십시오.
2. AutoJs6의 플러그인 센터를 열어 `OpenCV` 플러그인이 인식되고 활성화되어 있는지 확인합니다.
3. 평소처럼 스크립트를 작성하고 실행합니다: 스크립트가 이미지 API를 사용하면 AutoJs6가 플러그인이 제공하는 OpenCV 네이티브 라이브러리를 자동으로 로드하므로, 스크립트 코드를 전혀 수정할 필요가 없습니다.
4. 플러그인을 업데이트하거나 다시 설치한 후에는 먼저 AutoJs6를 완전히 종료했다가 다시 시작한 다음 이미지 관련 스크립트를 실행하여, 새 버전의 네이티브 라이브러리가 적용되도록 하십시오.

> 플러그인 센터에 이 플러그인이 보이지 않으면 먼저 AutoJs6를 최신 버전 (내부 버전 코드 5237 이상)으로 업데이트하십시오. 플러그인 자체는 Android 7.0 (API 24) 이상 기기를 지원합니다.

<p align="center">
  <img src="https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/docs/images/screenshots/plugin-center-enabled.png?raw=true" alt="AutoJs6 플러그인 센터가 OpenCV 1.1.0을 인식하고 활성화된 상태로 표시합니다." width="480" />
</p>
<p align="center"><sub>AutoJs6 플러그인 센터가 OpenCV 1.1.0을 인식하고 활성화된 상태로 표시합니다.</sub></p>

******

### 설치 패키지 선택 방법

******

각 릴리스에는 5개의 APK가 포함되며, 차이는 어떤 아키텍처의 네이티브 라이브러리를 내장했는지뿐입니다:

| 설치 패키지 | 적용 대상 |
|---|---|
| `arm64-v8a` | 대다수의 최신 Android 스마트폰과 태블릿 (64비트 ARM). 우선 선택 |
| `armeabi-v7a` | 비교적 오래된 32비트 ARM 기기 |
| `x86_64` | 64비트 x86 에뮬레이터와 일부 x86 기기 |
| `x86` | 32비트 x86 에뮬레이터와 일부 x86 기기 |
| `universal` | 네 가지 아키텍처를 모두 내장하여 용량이 가장 큼. 모든 기기에서 사용할 수 있으며 아키텍처가 확실하지 않을 때의 안전한 선택 |

AutoJs6의 APK Builder로 여러 아키텍처를 대상으로 하는 애플리케이션을 패키징하려면 반드시 `universal` 플러그인 패키지를 설치해야 합니다: 단일 아키텍처 플러그인은 자체 아키텍처용 OpenCV만 제공할 수 있습니다. 기기 아키텍처와 맞지 않는 단일 아키텍처 패키지를 잘못 설치하면 플러그인이 사용 가능한 네이티브 라이브러리를 제공할 수 없으며, `universal` 패키지로 교체하면 해결됩니다.

******

### 빠른 자체 점검

******

AutoJs6 플러그인 센터에서 OpenCV가 활성화된 것을 확인하고 AutoJs6를 다시 시작한 뒤 다음 스크립트를 실행하십시오. `images.initOpenCvIfNeeded()`는 메타데이터만 확인하지 않고 플러그인 검색, 호환성 검사 및 네이티브 로드를 실제로 수행합니다:

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

성공하면 `OpenCV version: 4.8.0`과 `arm64-v8a` 같은 프로세스 ABI가 출력됩니다. 로드에 실패하면 `universal` 패키지 설치, AutoJs6 내부 빌드 5237 이상으로 업데이트, AutoJs6 완전 종료 후 재시작 순서로 확인하십시오.

******

### 자주 묻는 질문

******

#### 플러그인이 제대로 적용되었는지 어떻게 확인하나요?

AutoJs6의 플러그인 센터를 열어 `OpenCV` 플러그인이 보이면 호스트가 인식한 것입니다. 이어서 이미지 API를 사용하는 아무 스크립트나 실행해 결과가 정상적으로 반환되면 네이티브 라이브러리가 성공적으로 로드된 것입니다.

#### 앱 목록에 플러그인 아이콘이 없는 이유는 무엇인가요?

정상적인 현상입니다. 플러그인은 독립된 인터페이스가 없고 홈 화면에 실행 아이콘도 만들지 않으며, 설치 후에는 AutoJs6가 백그라운드에서 자동으로 발견하고 호출합니다. 모든 상호작용은 AutoJs6 안에서 이루어집니다.

#### 플러그인 업데이트 후 이미지 기능이 이상하거나 여전히 이전 버전을 사용하는 것 같은데요?

네이티브 라이브러리는 한 번 로드되면 호스트 프로세스와 함께 유지되므로, 플러그인을 업데이트해도 실행 중인 이전 라이브러리는 교체되지 않습니다. AutoJs6를 완전히 종료하고 다시 시작하면 새 버전의 네이티브 라이브러리가 적용됩니다.

#### 호스트 버전이 너무 낮거나 플러그인이 호환되지 않는다고 표시되면 어떻게 하나요?

이 플러그인은 AutoJs6 내부 버전 코드 5237 이상을 요구하므로 먼저 AutoJs6를 업그레이드하십시오. 호스트는 로드 전에 계약 버전과 Java API 지문을 검증하며, 양쪽이 일치하지 않으면 위험을 안은 채 실행하는 대신 로드를 바로 거부합니다.

#### 플러그인을 설치했는데도 이미지 기능을 사용할 수 없다면 무엇이 원인일까요?

가장 흔한 원인은 설치 패키지와 기기 아키텍처의 불일치입니다: 단일 아키텍처 패키지는 자체 아키텍처에서만 작동합니다. 먼저 `universal` 패키지로 교체해 아키텍처 요인을 배제하십시오. 그래도 해결되지 않으면 AutoJs6 버전이 요구 사항을 충족하는지 확인하고, AutoJs6를 다시 시작한 후 재시도하십시오.

#### 플러그인이 네트워크에 접속하거나 민감한 권한을 요청하나요?

아니요. 플러그인 매니페스트에는 네트워크, 저장소, 카메라 등 어떤 민감한 시스템 권한도 포함되지 않으며, AutoJs6와 통신하는 데 쓰이는 플러그인 권한만 선언합니다. 플러그인의 유일한 역할은 OpenCV 네이티브 라이브러리를 호스트에 전달해 로드되게 하는 것입니다.

#### 왜 더 새로운 버전이 아닌 OpenCV 4.8.0을 제공하나요?

플러그인의 네이티브 라이브러리는 호스트에 유지된 OpenCV Java API와 엄격하게 일치해야 하므로 (SHA-256 지문으로 검증), OpenCV 버전은 호스트와 플러그인의 계약에 의해 고정됩니다. 더 새로운 OpenCV 버전은 호스트가 지원한 뒤 새 변형으로 뒤따를 예정이며, 진행 상황은 [ROADMAP.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/ROADMAP.md)에서 확인할 수 있습니다.

******

### 권한과 보안

******

네이티브 코드는 호스트와 같은 프로세스에서 실행되므로, 플러그인에는 빌드부터 로드까지 여러 겹의 방어선이 마련되어 있습니다:

- 검증 가능한 출처: 네이티브 라이브러리는 OpenCV 공식 소스의 고정된 커밋에서 재빌드되며, 툴체인 버전과 ABI별 해시가 `libs/opencv-native-4.8.0.provenance.json`에 기록되어 [NATIVE_BUILD.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/NATIVE_BUILD.md)에 따라 독립적으로 재현하고 대조할 수 있습니다.
- 빌드 게이트: 모든 빌드에서 네이티브 라이브러리 목록, ELF 아키텍처, payload 해시, Java API 지문, 중복 OpenCV Java 클래스 및 라이선스 애셋을 검증하며, 게시용 빌드에는 신뢰할 수 있는 서명 ID 설정이 추가로 요구됩니다.
- 로드 전 검증: 호스트는 플러그인 서명, OpenCV 버전, 계약 버전, Java API SHA-256을 차례로 검증하고, 하나라도 일치하지 않으면 로드를 거부합니다.
- 프로세스 수준 C++ 런타임은 호스트가 일괄 제공하고 미리 로드하며, 플러그인은 `libc++_shared.so`를 포함하지 않아 호환되지 않는 런타임 경계에서 발생하는 비정상 종료를 방지합니다.
- 최소 권한: 네트워크를 비롯한 어떤 민감한 시스템 권한도 요청하지 않고, 독립된 인터페이스가 없으며, AutoJs6 플러그인 권한을 통해서만 호스트와 통신합니다.

플러그인은 반드시 공식 [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) 페이지 또는 기타 신뢰할 수 있는 경로에서만 받으십시오. 공식 릴리스 패키지는 호스트가 인정하는 서명 ID를 사용합니다. 출처가 불분명한 설치 패키지는 버전 번호가 같더라도 호스트 검증을 통과하지 못하거나 위험을 숨기고 있을 수 있습니다.

******

### 플러그인 인터페이스

******

다음 정보는 AutoJs6 호스트와 플러그인 개발자를 위한 것입니다. 호스트는 이 식별자들을 통해 플러그인을 발견하고 호환성 협상을 수행합니다:

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

동일한 `OpenCvPluginInfoService`가 `org.autojs.plugin.INFO`와 `org.autojs.plugin.OPENCV` 두 action에 응답하며, 모두 `opencv-runtime` category를 사용합니다. Binder 인터페이스는 common-plugin-api의 `IPluginInfoProvider`입니다.

`PluginInfo.supportedAbis`는 설치된 APK에 실제로 존재하는 OpenCV 네이티브 라이브러리 항목을 기준으로 동적으로 계산됩니다: 단일 아키텍처 패키지는 자체 아키텍처만 보고하고, `universal` 패키지는 네 가지 아키텍처를 모두 보고합니다.

******

### 로드맵

******

플러그인의 기능 계획과 완료 상황은 ROADMAP.md에서 체크 가능한 목록으로 관리되며, 마일스톤별로 정리되고 승인 조건이 붙어 있습니다. 16 KB 메모리 페이지 대응, OpenCV 버전 발전, 지속적 통합과 진단 경험 등의 방향을 다룹니다. 체크되지 않은 항목은 현재 버전의 기능이 아니라 계획 의도를 나타냅니다. Issues를 통한 토론 참여를 환영합니다.

- [ROADMAP.md 보기](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/ROADMAP.md)

******

### 릴리스 기록

******

#### v1.1.0

_2026/08/31_

- `기능` 릴리스 다운로드에 5개 아키텍처 APK, `SHA256SUMS.txt`, 네이티브 빌드 provenance 매니페스트를 함께 제공하여 기기에 맞는 패키지를 선택하고 파일을 독립적으로 검증할 수 있게 했습니다
- `수정` 4개 ABI 네이티브 라이브러리를 16 KB `PT_LOAD` 정렬로 다시 빌드하고 릴리스 검증을 추가하여 4 KB 기기 호환성을 유지하면서 16 KB page-size Android 기기에서 OpenCV를 로드할 수 있게 했습니다
- `개선` 플러그인 센터 안내와 10개 언어 README에 실제 활성화 상태 스크린샷과 OpenCV 버전 및 프로세스 ABI를 출력하는 실행 가능한 자체 점검 스크립트를 추가했습니다
- `개선` 지원 ABI 감지를 universal, 단일 ABI, split 설치까지 확장하고 APK 경로가 없거나 손상되어도 스캔을 계속하거나 추출된 네이티브 라이브러리로 대체할 수 있게 했습니다

#### v1.0.0

_2026/07/22_

- `기능` 첫 정식 릴리스: AutoJs6의 이미지 API를 위해 OpenCV 4.8.0 네이티브 런타임을 제공. 호스트는 스크립트가 호출하는 OpenCV Java API를 유지하고, 플러그인은 이에 정확히 일치하는 `libopencv_java4.so`를 포함
- `기능` AutoJs6의 자동 발견과 호환성 협상을 지원: `org.autojs.plugin.INFO` 및 `org.autojs.plugin.OPENCV` action (`opencv-runtime` category)을 통해 버전, 계약, 지문 등의 메타데이터를 호스트에 제공
- `기능` `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64` 네 가지 단일 아키텍처 설치 패키지와 모든 아키텍처를 포함한 `universal` 패키지를 제공하고, 설치 패키지의 실제 내용에 따라 지원 아키텍처를 동적으로 보고
- `기능` 플러그인 정보, 사용 설명, README 및 CHANGELOG가 10개 언어를 지원: 간체 중국어, 홍콩 번체, 대만 번체, 영어, 프랑스어, 스페인어, 일본어, 한국어, 러시아어 및 아랍어
- `기능` 빌드 게이트 내장: 네이티브 라이브러리 목록, ELF 아키텍처, payload 해시, Java API 지문, 중복 OpenCV Java 클래스 및 라이선스 애셋을 검증하며, 게시용 빌드에는 신뢰할 수 있는 서명 ID 설정도 요구
- `기능` 각 설치 패키지에 OpenCV 4.8.0과 정적으로 연결된 타사 구성 요소의 전체 라이선스 원문을 내장. `libc++_shared.so`는 호환되는 AutoJs6 호스트가 일괄 제공하고 미리 로드하며, 플러그인은 중복 패키징하지 않음
- `수정` Android NDK 26 (API 24)으로 공식 소스에서 OpenCV 4.8.0 네이티브 라이브러리를 재빌드해 플러그인과 호스트가 같은 계열의 C++ 런타임을 공유하도록 하여, 예외가 호환되지 않는 런타임 경계를 넘어 AutoJs6를 비정상 종료시키는 문제를 수정

##### 더 많은 릴리스 기록

* [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/app/src/main/assets/doc/CHANGELOG-ko.md)

******

### 빌드 및 검증

******

이 섹션은 소스에서 플러그인을 빌드하려는 개발자를 위한 것입니다.

debug APK 빌드:

```powershell
.\gradlew.bat :app:assembleDebug
```

단위 테스트를 실행하고 debug 및 release APK의 무결성을 검증:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:verifyOpenCvApks
```

게시하기 전에 버전 관리에서 제외된 `sign.properties`에 신뢰할 수 있는 서명 ID를 설정하고 다음을 실행하십시오:

```powershell
.\gradlew.bat :app:verifyOpenCvPublishableApks
```

서명된 APK 5개, SHA-256 매니페스트 및 GitHub Release 설명 생성 (전체 절차는 [RELEASING.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/RELEASING.md) 참조):

```bat
scripts\release\prepare-release.bat
```

`sign.properties`를 설정하지 않아도 payload를 빌드하고 검증할 수 있지만, 생성된 release APK는 서명되지 않아 게시할 수 없습니다.

일반 빌드는 저장소에 미리 빌드된 네이티브 라이브러리 AAR을 그대로 사용하므로 OpenCV를 로컬에서 컴파일할 필요가 없습니다. 공식 소스에서 완전히 재빌드하고 provenance를 대조하려면 [NATIVE_BUILD.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/NATIVE_BUILD.md)를 참조하십시오.

******

### 현지화와 문서 생성

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

`strings.xml`은 현지화된 플러그인 설명을 제공하고, `plugin_instruction.md`는 호스트 측에 표시되는 사용 설명을 제공합니다. README와 CHANGELOG는 반드시 `.readme/`와 `.changelog/` 아래의 JSON 소스 파일을 수정한 뒤 `py .python/generate_markdown.py`를 실행해 다시 생성하며, 생성물은 손으로 편집하지 않습니다. `py .python/generate_markdown.py --check`를 실행하면 소스 파일과 생성물이 동기화되어 있는지 검증할 수 있습니다.

******

### 라이선스

******

프로젝트 코드는 [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/LICENSE)에 따라 라이선스가 부여됩니다. OpenCV 4.8.0 및 정적으로 연결된 타사 구성 요소의 라이선스는 [THIRD_PARTY_NOTICES.md](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/blob/master/THIRD_PARTY_NOTICES.md)를 참조하십시오. 전체 라이선스 원문은 각 APK의 `assets/licenses/opencv-4.8.0/`에 포함됩니다.

******

### 링크

******

- AutoJs6 문서: https://docs.autojs6.com
- OpenCV 공식 웹사이트: https://opencv.org
- OpenCV 4.8.0 소스 코드: https://github.com/opencv/opencv/tree/4.8.0
