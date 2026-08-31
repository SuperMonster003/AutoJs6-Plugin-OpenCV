[CmdletBinding()]
param(
    [string]$AndroidSdk,
    [string]$JavaHome,
    [string]$PythonExecutable,
    [string]$WorkDirectory,
    [switch]$ReuseSource
)

$ErrorActionPreference = "Stop"
$scriptDirectory = Split-Path -Parent $MyInvocation.MyCommand.Path
$projectDirectory = Resolve-Path (Join-Path $scriptDirectory "..\..")

if ([string]::IsNullOrWhiteSpace($AndroidSdk)) {
    $localProperties = Join-Path $projectDirectory "local.properties"
    if (Test-Path -LiteralPath $localProperties) {
        $sdkLine = Get-Content -LiteralPath $localProperties |
            Where-Object { $_ -match '^sdk\.dir=' } |
            Select-Object -First 1
        if ($sdkLine) {
            $AndroidSdk = $sdkLine.Substring("sdk.dir=".Length)
            $AndroidSdk = $AndroidSdk -replace '\\:', ':' -replace '\\\\', '\'
        }
    }
}
if ([string]::IsNullOrWhiteSpace($AndroidSdk)) {
    throw "Pass -AndroidSdk or configure sdk.dir in local.properties"
}
$AndroidSdk = (Resolve-Path -LiteralPath $AndroidSdk).Path

if ([string]::IsNullOrWhiteSpace($WorkDirectory)) {
    $WorkDirectory = Join-Path $projectDirectory "build\opencv-native-ndk26-ps16k"
}
$WorkDirectory = [System.IO.Path]::GetFullPath($WorkDirectory)
$sourceDirectory = Join-Path $WorkDirectory "opencv-4.8.0"
$nativeSdkDirectory = Join-Path $WorkDirectory "native-sdk"
$ndkDirectory = Join-Path $AndroidSdk "ndk\26.1.10909125"
$cmakeBin = Join-Path $AndroidSdk "cmake\3.22.1\bin"
$expectedTagObject = "53296de62872b5e7d042ddffb49679fbdcca99f6"
$expectedCommit = "f9a59f2592993d3dcc080e495f4f5e02dd8ec7ef"
$elfMaxPageSize = 16384

if (-not (Test-Path -LiteralPath (Join-Path $ndkDirectory "source.properties"))) {
    throw "Android NDK 26.1.10909125 is not installed under $AndroidSdk"
}
$ndkProperties = Get-Content -LiteralPath (Join-Path $ndkDirectory "source.properties") -Raw
if ($ndkProperties -notmatch '(?m)^Pkg\.Revision\s*=\s*26\.1\.10909125\s*$') {
    throw "Unexpected Android NDK revision in $ndkDirectory"
}
if (-not (Test-Path -LiteralPath (Join-Path $cmakeBin "cmake.exe"))) {
    throw "Android SDK CMake 3.22.1 is not installed under $AndroidSdk"
}
$cmakeVersion = & (Join-Path $cmakeBin "cmake.exe") --version 2>&1
if ($LASTEXITCODE -ne 0 -or $cmakeVersion[0] -notmatch '^cmake version 3\.22\.1(?:[-+][^\s]+)?$') {
    throw "Expected Android SDK CMake 3.22.1: $($cmakeVersion[0])"
}
if ([string]::IsNullOrWhiteSpace($JavaHome)) {
    $JavaHome = $env:JAVA_HOME_17
}
if ([string]::IsNullOrWhiteSpace($JavaHome) -or
    -not (Test-Path -LiteralPath (Join-Path $JavaHome "bin\java.exe"))) {
    throw "Pass a JDK 17 installation with -JavaHome or JAVA_HOME_17"
}
$JavaHome = (Resolve-Path -LiteralPath $JavaHome).Path
$javaVersion = & (Join-Path $JavaHome "bin\java.exe") -version 2>&1
if ($LASTEXITCODE -ne 0 -or ($javaVersion -join "`n") -notmatch 'version "17\.') {
    throw "OpenCV Java bindings must be configured with JDK 17: $JavaHome"
}
if ([string]::IsNullOrWhiteSpace($PythonExecutable)) {
    $PythonExecutable = (Get-Command python -ErrorAction Stop).Source
}
$PythonExecutable = (Resolve-Path -LiteralPath $PythonExecutable).Path
$pythonVersion = & $PythonExecutable -c "import sys; print('.'.join(map(str, sys.version_info[:3])))"
if ($LASTEXITCODE -ne 0 -or [version]$pythonVersion -lt [version]"3.10") {
    throw "Python 3.10 or newer is required: $PythonExecutable"
}

New-Item -ItemType Directory -Force -Path $WorkDirectory | Out-Null
if (-not (Test-Path -LiteralPath (Join-Path $sourceDirectory ".git"))) {
    & git clone --branch 4.8.0 --depth 1 https://github.com/opencv/opencv.git $sourceDirectory
    if ($LASTEXITCODE -ne 0) { throw "Unable to clone OpenCV 4.8.0" }
} elseif (-not $ReuseSource) {
    throw "Source already exists at $sourceDirectory; pass -ReuseSource after verifying it"
}

$actualCommit = (& git -C $sourceDirectory rev-parse HEAD).Trim()
if ($LASTEXITCODE -ne 0 -or $actualCommit -ne $expectedCommit) {
    throw "Expected OpenCV commit $expectedCommit, got $actualCommit"
}
$actualTagObject = (& git -C $sourceDirectory rev-parse "4.8.0^{tag}").Trim()
if ($LASTEXITCODE -ne 0 -or $actualTagObject -ne $expectedTagObject) {
    throw "Expected OpenCV 4.8.0 tag object $expectedTagObject, got $actualTagObject"
}
$sourceChanges = & git -C $sourceDirectory status --porcelain --untracked-files=no
if ($LASTEXITCODE -ne 0 -or $sourceChanges) {
    throw "OpenCV source has tracked modifications: $sourceDirectory"
}

$abiConfigurations = @(
    [pscustomobject]@{ Name = "arm64-v8a"; CMakeAbi = "arm64-v8a"; PlatformId = "3"; Ipp = "OFF" },
    [pscustomobject]@{ Name = "armeabi-v7a"; CMakeAbi = "armeabi-v7a with NEON"; PlatformId = "2"; Ipp = "OFF" },
    [pscustomobject]@{ Name = "x86"; CMakeAbi = "x86"; PlatformId = "4"; Ipp = "ON" },
    [pscustomobject]@{ Name = "x86_64"; CMakeAbi = "x86_64"; PlatformId = "5"; Ipp = "ON" }
)

$oldPath = $env:PATH
$oldJavaHome = $env:JAVA_HOME
$oldAndroidHome = $env:ANDROID_HOME
$oldAndroidSdk = $env:ANDROID_SDK
$oldAndroidNdk = $env:ANDROID_NDK
try {
    $env:PATH = "$JavaHome\bin;$cmakeBin;$oldPath"
    $env:JAVA_HOME = $JavaHome
    $env:ANDROID_HOME = $AndroidSdk
    $env:ANDROID_SDK = $AndroidSdk
    $env:ANDROID_NDK = $ndkDirectory

    foreach ($abi in $abiConfigurations) {
        $binaryDirectory = Join-Path $WorkDirectory "build-$($abi.Name)"
        $logFile = Join-Path $WorkDirectory "build-$($abi.Name).log"
        $cmakeArguments = @(
            "-S", $sourceDirectory,
            "-B", $binaryDirectory,
            "-G", "Ninja",
            "-DCMAKE_MAKE_PROGRAM=$cmakeBin\ninja.exe",
            "-DCMAKE_TOOLCHAIN_FILE=$ndkDirectory\build\cmake\android.toolchain.cmake",
            "-DCMAKE_BUILD_TYPE=Release",
            "-DCMAKE_SHARED_LINKER_FLAGS=-Wl,-z,max-page-size=$elfMaxPageSize",
            "-DINSTALL_CREATE_DISTRIB=ON",
            "-DWITH_OPENCL=OFF",
            "-DBUILD_KOTLIN_EXTENSIONS=OFF",
            "-DBUILD_ANDROID_PROJECTS=ON",
            "-DBUILD_JAVA=ON",
            "-DWITH_IPP=$($abi.Ipp)",
            "-DWITH_TBB=ON",
            "-DBUILD_EXAMPLES=OFF",
            "-DBUILD_TESTS=OFF",
            "-DBUILD_PERF_TESTS=OFF",
            "-DBUILD_DOCS=OFF",
            "-DBUILD_ANDROID_EXAMPLES=OFF",
            "-DINSTALL_ANDROID_EXAMPLES=OFF",
            "-DANDROID_STL=c++_shared",
            "-DANDROID_ABI=$($abi.CMakeAbi)",
            "-DANDROID_PLATFORM_ID=$($abi.PlatformId)",
            "-DANDROID_TOOLCHAIN=clang",
            "-DANDROID_NATIVE_API_LEVEL=24",
            "-DANDROID_COMPILE_SDK_VERSION=32",
            "-DANDROID_TARGET_SDK_VERSION=32",
            "-DANDROID_MIN_SDK_VERSION=24",
            "-DANDROID_GRADLE_PLUGIN_VERSION=7.3.1",
            "-DGRADLE_VERSION=7.5.1",
            "-DKOTLIN_PLUGIN_VERSION=1.5.20",
            "-DPYTHON_DEFAULT_EXECUTABLE=$PythonExecutable",
            "-DPYTHON3_EXECUTABLE=$PythonExecutable"
        )
        $configureOutput = & (Join-Path $cmakeBin "cmake.exe") @cmakeArguments 2>&1
        $configureOutput | Set-Content -LiteralPath $logFile -Encoding utf8
        if ($LASTEXITCODE -ne 0) { throw "OpenCV CMake configuration failed for $($abi.Name)" }
        $configureText = $configureOutput -join "`n"
        if ($configureText -notmatch 'Java wrappers:\s+YES') {
            throw "OpenCV Java wrappers are disabled for $($abi.Name); see $logFile"
        }
        if ($configureText -notmatch [regex]::Escape($PythonExecutable)) {
            throw "OpenCV selected an unexpected Python for $($abi.Name); see $logFile"
        }

        & (Join-Path $cmakeBin "cmake.exe") `
            --build $binaryDirectory `
            --target opencv_java `
            --parallel *>> $logFile
        if ($LASTEXITCODE -ne 0) { throw "OpenCV JNI build failed for $($abi.Name)" }

        $sourceLibrary = Join-Path $binaryDirectory "jni\$($abi.Name)\libopencv_java4.so"
        if (-not (Test-Path -LiteralPath $sourceLibrary)) {
            throw "OpenCV JNI output is missing for $($abi.Name): $sourceLibrary"
        }
        $destinationDirectory = Join-Path $nativeSdkDirectory "sdk\native\libs\$($abi.Name)"
        New-Item -ItemType Directory -Force -Path $destinationDirectory | Out-Null
        $destinationLibrary = Join-Path $destinationDirectory "libopencv_java4.so"
        Copy-Item -LiteralPath $sourceLibrary -Destination $destinationLibrary -Force
        & (Join-Path $ndkDirectory "toolchains\llvm\prebuilt\windows-x86_64\bin\llvm-strip.exe") `
            $destinationLibrary
        if ($LASTEXITCODE -ne 0) { throw "Unable to strip OpenCV JNI for $($abi.Name)" }
    }
} finally {
    $env:PATH = $oldPath
    $env:JAVA_HOME = $oldJavaHome
    $env:ANDROID_HOME = $oldAndroidHome
    $env:ANDROID_SDK = $oldAndroidSdk
    $env:ANDROID_NDK = $oldAndroidNdk
}

& $PythonExecutable (Join-Path $scriptDirectory "package_native_aar.py") `
    --opencv-sdk $nativeSdkDirectory `
    --ndk $ndkDirectory `
    --output (Join-Path $projectDirectory "libs\opencv-native-4.8.0.aar") `
    --provenance (Join-Path $projectDirectory "libs\opencv-native-4.8.0.provenance.json")
if ($LASTEXITCODE -ne 0) { throw "OpenCV native AAR packaging failed" }
