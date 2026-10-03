#!/bin/sh
set -eu
cd "$(dirname "$0")"
if [ -z "${JAVA_HOME:-}" ] && [ -d '/Applications/Android Studio.app/Contents/jbr/Contents/Home' ]; then
    export JAVA_HOME='/Applications/Android Studio.app/Contents/jbr/Contents/Home'
fi
sdk_path="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-$HOME/Library/Android/sdk}}"
adb_path="$sdk_path/platform-tools/adb"
if [ ! -x "$adb_path" ]; then
    echo 'Android SDK를 설치하고 ANDROID_HOME을 설정해주세요.' >&2
    exit 1
fi
if [ -n "${1:-}" ]; then
    export ANDROID_SERIAL="$1"
fi
"$adb_path" get-state
./gradlew assembleDebug
"$adb_path" install -r app/build/outputs/apk/debug/app-debug.apk
"$adb_path" shell am start -n kr.ac.mwplab.calculator/.MainActivity
