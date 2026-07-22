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
