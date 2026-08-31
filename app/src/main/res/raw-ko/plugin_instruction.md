OpenCV 플러그인 (OpenCV Plugin)은 AutoJs6의 이미지 처리에 필요한 OpenCV 4.8.0 네이티브 런타임을 제공합니다. AutoJs6의 이미지 찾기, 색상 감지, 템플릿 매칭 등 이미지 API는 모두 OpenCV의 연산에 의존합니다. 이 플러그인을 설치하면 플러그인형 OpenCV를 지원하는 AutoJs6에서 이러한 이미지 기능을 정상적으로 사용할 수 있으며, 스크립트에 추가 설정이 전혀 필요 없습니다.

### 사용 방법

1. [Releases](https://github.com/SuperMonster003/AutoJs6-Plugin-OpenCV/releases) 페이지에서 기기에 맞는 플러그인 APK를 내려받아 AutoJs6가 실행되는 기기에 설치합니다. 어떤 것을 선택할지 확실하지 않으면 `universal` 패키지를 선택하거나 아래의 `설치 패키지 선택 방법`을 참고하십시오.
2. AutoJs6의 플러그인 센터를 열어 `OpenCV` 플러그인이 인식되고 활성화되어 있는지 확인합니다.
3. 평소처럼 스크립트를 작성하고 실행합니다: 스크립트가 이미지 API를 사용하면 AutoJs6가 플러그인이 제공하는 OpenCV 네이티브 라이브러리를 자동으로 로드하므로, 스크립트 코드를 전혀 수정할 필요가 없습니다.
4. 플러그인을 업데이트하거나 다시 설치한 후에는 먼저 AutoJs6를 완전히 종료했다가 다시 시작한 다음 이미지 관련 스크립트를 실행하여, 새 버전의 네이티브 라이브러리가 적용되도록 하십시오.

플러그인 센터에 이 플러그인이 보이지 않으면 먼저 AutoJs6를 최신 버전 (내부 버전 코드 5237 이상)으로 업데이트하십시오. 플러그인 자체는 Android 7.0 (API 24) 이상 기기를 지원합니다.

### 설치 패키지 선택 방법

각 릴리스에는 5개의 APK가 포함되며, 차이는 어떤 아키텍처의 네이티브 라이브러리를 내장했는지뿐입니다:

| 설치 패키지 | 적용 대상 |
|---|---|
| `arm64-v8a` | 대다수의 최신 Android 스마트폰과 태블릿 (64비트 ARM). 우선 선택 |
| `armeabi-v7a` | 비교적 오래된 32비트 ARM 기기 |
| `x86_64` | 64비트 x86 에뮬레이터와 일부 x86 기기 |
| `x86` | 32비트 x86 에뮬레이터와 일부 x86 기기 |
| `universal` | 네 가지 아키텍처를 모두 내장하여 용량이 가장 큼. 모든 기기에서 사용할 수 있으며 아키텍처가 확실하지 않을 때의 안전한 선택 |

AutoJs6의 APK Builder로 여러 아키텍처를 대상으로 하는 애플리케이션을 패키징하려면 반드시 `universal` 플러그인 패키지를 설치해야 합니다: 단일 아키텍처 플러그인은 자체 아키텍처용 OpenCV만 제공할 수 있습니다. 기기 아키텍처와 맞지 않는 단일 아키텍처 패키지를 잘못 설치하면 플러그인이 사용 가능한 네이티브 라이브러리를 제공할 수 없으며, `universal` 패키지로 교체하면 해결됩니다.

### 빠른 자체 점검

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
