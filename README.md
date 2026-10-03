# mwp-lab05
모바일 웹서비스 프로그래밍 5주차 실습 — 사칙연산 계산기

## Android 앱 (Kotlin)

Android Studio에서 `android` 폴더를 프로젝트로 열고 Gradle 동기화를 완료하세요.
휴대폰의 개발자 옵션에서 **USB 디버깅**을 켜고 USB로 Mac에 연결한 뒤,
휴대폰에서 이 컴퓨터의 디버깅 허용을 승인하세요. Android Studio 상단에서
휴대폰을 선택하고 **Run ▶**을 누르면 빌드, 설치, 실행됩니다.
Android 6.0 이상에서 실행되며 인터넷 연결 없이 계산할 수 있습니다.

터미널에서도 빌드·설치·실행할 수 있습니다.

```sh
./android/run-phone.sh
```

기기가 여러 대 연결되어 있다면 `./android/run-phone.sh 기기시리얼`로 지정하세요.
처음 빌드에는 Gradle 및 의존성 다운로드를 위한 인터넷 연결이 필요합니다.
다른 컴퓨터에서는 Android Studio가 `android/local.properties`의 SDK 경로를
설정하도록 하거나 직접 `sdk.dir`을 지정하세요. JDK 17 이상이 필요합니다.

APK만 빌드하거나 테스트하려면:

```sh
cd android
./gradlew assembleDebug
./gradlew testDebugUnitTest lintDebug
```

APK: `android/app/build/outputs/apk/debug/app-debug.apk`

숫자 두 개와 연산자를 선택해 계산합니다. 음수·소수 입력, 잘못된 입력 안내,
계산 범위 초과 안내를 지원합니다. 기존 실습 규칙에 따라 0으로 나누면 0입니다.

## Python 코드 설명

`FourBasicOpt.py`의 `FourBasicOpt` 클래스는 두 숫자를 받아 사칙연산을 수행합니다.

- `add(x, y)`: 두 숫자를 더합니다.
- `subtract(x, y)`: 첫 번째 숫자에서 두 번째 숫자를 뺍니다.
- `multiply(x, y)`: 두 숫자를 곱합니다.
- `divide(x, y)`: 첫 번째 숫자를 두 번째 숫자로 나눕니다. 두 번째 숫자가 0이면 0을 반환합니다.

`test_FourBasicOpt.py`는 Python의 `unittest`로 각 연산의 결과와 0으로 나누는
경우를 검증합니다.

Python 코드 테스트 명령:

```sh
python3 -m unittest test_FourBasicOpt.py
```
