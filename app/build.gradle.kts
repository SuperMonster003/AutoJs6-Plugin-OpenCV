import com.android.build.api.variant.FilterConfiguration
import org.gradle.api.GradleException
import org.gradle.api.provider.Property
import java.io.InputStream
import java.security.MessageDigest
import java.util.Properties
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream

plugins {
    id("org.autojs.build.utils")
    id("org.autojs.build.versions")
    id("org.autojs.build.signs")
    id("org.autojs.build.jvm-convention")
    id("com.android.application")
}

val globalApplicationId = "io.github.supermonster003.autojs6.plugin.opencv"
val openCvContractVersion = 1
val openCvVersion = "4.8.0"
val openCvNativeLibrary = "opencv_java4"
val openCvJavaApiSha256 = "340976552fda3cce525021f0b072427cabf0aa1c786fb80cfc4a3a8105d90b3f"
val requiredHostVersionCode = 5236L
val supportedAbis = listOf("arm64-v8a", "armeabi-v7a", "x86", "x86_64")
val openCvNativeAar = file("$rootDir/libs/opencv-native-4.8.0.aar")
val openCvJavaApiAar = file("$rootDir/libs/opencv-java-api-4.8.0.aar")
val openCvLicenseAssets = file("$projectDir/src/main/assets/licenses/opencv-4.8.0")
val requiredOpenCvLicenseAssetNames = setOf(
    "ade-LICENSE",
    "carotene-LICENSE.txt",
    "cpufeatures-LICENSE",
    "cpufeatures-README.md",
    "flatbuffers-LICENSE.txt",
    "ippicv-EULA.txt",
    "ippicv-readme.htm",
    "ippicv-third-party-programs.txt",
    "ippiw-EULA.txt",
    "ippiw-support.txt",
    "ippiw-third-party-programs.txt",
    "ittnotify-LICENSE.BSD",
    "ittnotify-LICENSE.GPL",
    "libjpeg-turbo-LICENSE.md",
    "libjpeg-turbo-README.ijg",
    "libjpeg-turbo-README.md",
    "libopenjp2-LICENSE",
    "libopenjp2-README.md",
    "libpng-LICENSE",
    "libpng-README",
    "libtiff-COPYRIGHT",
    "libwebp-COPYING.txt",
    "opencv-LICENSE.txt",
    "openexr-AUTHORS.ilmbase",
    "openexr-AUTHORS.openexr",
    "openexr-LICENSE",
    "protobuf-LICENSE",
    "protobuf-README.md",
    "quirc-LICENSE",
    "SoftFloat-COPYING.txt",
    "tbb-LICENSE",
    "tbb-README",
    "torch-COPYRIGHT.txt",
)

fun InputStream.sha256Hex(): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val buffer = ByteArray(256 * 1024)
    while (true) {
        val count = read(buffer)
        if (count < 0) break
        digest.update(buffer, 0, count)
    }
    return digest.digest().joinToString("") { "%02x".format(it.toInt() and 0xff) }
}

fun ByteArray.openCvClassDescriptors(): Set<String> =
    Regex("Lorg/opencv/[A-Za-z0-9_$/]+;")
        .findAll(toString(Charsets.ISO_8859_1))
        .map { it.value }
        .toSet()

fun ByteArray.sha256Hex(): String = inputStream().use { it.sha256Hex() }

fun verifyElfHeader(header: ByteArray, abi: String) {
    if (header.size < 20 || !header.copyOfRange(0, 4).contentEquals(byteArrayOf(0x7f, 0x45, 0x4c, 0x46))) {
        throw GradleException("OpenCV payload for $abi is not an ELF file")
    }
    val (expectedMachine, expectedClass) = mapOf(
        "arm64-v8a" to (183 to 2),
        "armeabi-v7a" to (40 to 1),
        "x86" to (3 to 1),
        "x86_64" to (62 to 2),
    ).getValue(abi)
    val actualClass = header[4].toInt() and 0xff
    val actualMachine = when (header[5].toInt() and 0xff) {
        1 -> (header[18].toInt() and 0xff) or ((header[19].toInt() and 0xff) shl 8)
        2 -> ((header[18].toInt() and 0xff) shl 8) or (header[19].toInt() and 0xff)
        else -> throw GradleException("OpenCV payload for $abi has an unsupported ELF byte order")
    }
    if (actualMachine != expectedMachine || actualClass != expectedClass) {
        throw GradleException(
            "OpenCV ELF architecture mismatch for $abi: machine=$actualMachine class=$actualClass",
        )
    }
}

fun verifyNativeOnlyOpenCvAar(
    nativeAarFile: File,
    supportedAbis: List<String>,
    nativeLibraryName: String,
): Map<String, String> {
    val expectedEntries = mutableSetOf<String>()
    for (abi in supportedAbis) {
        expectedEntries += "jni/$abi/lib$nativeLibraryName.so"
    }

    ZipFile(nativeAarFile).use { aar ->
        val classEntry = aar.getEntry("classes.jar")
            ?: throw GradleException("Native-only OpenCV AAR has no classes.jar")
        ZipInputStream(aar.getInputStream(classEntry)).use { classes ->
            while (true) {
                val entry = classes.nextEntry ?: break
                if (!entry.isDirectory && entry.name.endsWith(".class")) {
                    throw GradleException("Native-only OpenCV AAR unexpectedly contains ${entry.name}")
                }
            }
        }

        val actualEntries = mutableSetOf<String>()
        val entries = aar.entries()
        while (entries.hasMoreElements()) {
            val entry = entries.nextElement()
            if (!entry.isDirectory && entry.name.startsWith("jni/") && entry.name.endsWith(".so")) {
                actualEntries += entry.name
            }
        }
        if (actualEntries != expectedEntries) {
            throw GradleException("Unexpected native-only AAR inventory: $actualEntries")
        }

        val hashes = linkedMapOf<String, String>()
        for (abi in supportedAbis) {
            val entry = aar.getEntry("jni/$abi/lib$nativeLibraryName.so")
                ?: throw GradleException("Native-only AAR is missing $abi")
            hashes[abi] = aar.getInputStream(entry).use { it.sha256Hex() }
        }
        return hashes
    }
}

fun verifyOpenCvJavaApiFingerprint(javaApiAarFile: File, expectedSha256: String) {
    val actualSha256 = ZipFile(javaApiAarFile).use { aar ->
        val entry = aar.getEntry("classes.jar")
            ?: throw GradleException("OpenCV Java API AAR has no classes.jar")
        aar.getInputStream(entry).use { it.sha256Hex() }
    }
    if (actualSha256 != expectedSha256) {
        throw GradleException("OpenCV Java API fingerprint mismatch: $actualSha256")
    }
}

fun verifyOpenCvLicenseSource(
    licenseDirectory: File,
    requiredNames: Set<String>,
) {
    val files = licenseDirectory.listFiles()?.filter(File::isFile).orEmpty()
    val actualNames = files.map(File::getName).toSet()
    if (actualNames != requiredNames) {
        throw GradleException("Unexpected OpenCV license inventory: $actualNames")
    }
    files.firstOrNull { it.length() <= 0L }?.let { emptyFile ->
        throw GradleException("OpenCV license asset is empty: ${emptyFile.name}")
    }
}

fun collectApkFiles(directory: File, destination: MutableList<File>) {
    val children = directory.listFiles() ?: return
    for (child in children) {
        when {
            child.isDirectory -> collectApkFiles(child, destination)
            child.isFile && child.extension == "apk" -> destination += child
        }
    }
}

fun verifyOpenCvApk(
    apk: File,
    expectedLibraryEntries: Map<String, String>,
    nativeHashes: Map<String, String>,
    requiredLicenseAssetNames: Set<String>,
): Set<String> {
    ZipFile(apk).use { zip ->
        val actualAbis = linkedSetOf<String>()
        val expectedNativeEntries = linkedSetOf<String>()
        for ((abi, path) in expectedLibraryEntries) {
            if (zip.getEntry(path) != null) {
                actualAbis += abi
                expectedNativeEntries += path
            }
        }

        val actualNativeEntries = mutableSetOf<String>()
        val dexEntries = mutableListOf<String>()
        val entries = zip.entries()
        val dexNamePattern = Regex("classes\\d*\\.dex")
        while (entries.hasMoreElements()) {
            val entry = entries.nextElement()
            if (entry.isDirectory) continue
            if (entry.name.startsWith("lib/") && entry.name.endsWith(".so")) {
                actualNativeEntries += entry.name
            }
            if (dexNamePattern.matches(entry.name)) {
                dexEntries += entry.name
            }
        }
        if (actualNativeEntries != expectedNativeEntries) {
            throw GradleException("Unexpected native APK inventory in ${apk.name}: $actualNativeEntries")
        }

        val licensePrefix = "assets/licenses/opencv-4.8.0/"
        val packagedLicenseNames = mutableSetOf<String>()
        val licenseEntries = zip.entries()
        while (licenseEntries.hasMoreElements()) {
            val entry = licenseEntries.nextElement()
            if (!entry.isDirectory && entry.name.startsWith(licensePrefix)) {
                packagedLicenseNames += entry.name.removePrefix(licensePrefix)
            }
        }
        if (packagedLicenseNames != requiredLicenseAssetNames) {
            throw GradleException("Unexpected OpenCV license assets in ${apk.name}: $packagedLicenseNames")
        }

        for (abi in actualAbis) {
            val path = expectedLibraryEntries.getValue(abi)
            val entry = zip.getEntry(path)
                ?: throw GradleException("${apk.name} is missing $path")
            val payload = zip.getInputStream(entry).use { it.readBytes() }
            verifyElfHeader(payload.copyOf(minOf(payload.size, 20)), abi)
            if (payload.sha256Hex() != nativeHashes.getValue(abi)) {
                throw GradleException("OpenCV payload hash mismatch in ${apk.name} for $abi")
            }
        }

        for (dexPath in dexEntries) {
            val entry = zip.getEntry(dexPath)
                ?: throw GradleException("${apk.name} is missing $dexPath")
            val dex = zip.getInputStream(entry).use { it.readBytes() }
            val unexpectedDescriptors = dex.openCvClassDescriptors().filterNot { descriptor ->
                descriptor == "Lorg/opencv/R;" || descriptor.startsWith("Lorg/opencv/R$")
            }
            if (unexpectedDescriptors.isNotEmpty()) {
                throw GradleException(
                    "${apk.name} unexpectedly packages OpenCV Java classes: $unexpectedDescriptors",
                )
            }
        }
        return actualAbis
    }
}

fun verifyOpenCvVariantApks(
    variantName: String,
    apkDirectory: File,
    nativeAarFile: File,
    javaApiAarFile: File,
    licenseAssetDirectory: File,
    supportedAbis: List<String>,
    nativeLibraryName: String,
    javaApiSha256: String,
    requiredLicenseAssetNames: Set<String>,
) {
    val expectedLibraryEntries = linkedMapOf<String, String>()
    for (abi in supportedAbis) {
        expectedLibraryEntries[abi] = "lib/$abi/lib$nativeLibraryName.so"
    }
    val nativeHashes = verifyNativeOnlyOpenCvAar(nativeAarFile, supportedAbis, nativeLibraryName)
    verifyOpenCvJavaApiFingerprint(javaApiAarFile, javaApiSha256)
    verifyOpenCvLicenseSource(licenseAssetDirectory, requiredLicenseAssetNames)

    val apkFiles = mutableListOf<File>()
    collectApkFiles(apkDirectory, apkFiles)
    apkFiles.sortBy { it.name }
    val actualAbiSets = mutableListOf<Set<String>>()
    for (apk in apkFiles) {
        actualAbiSets += verifyOpenCvApk(
            apk,
            expectedLibraryEntries,
            nativeHashes,
            requiredLicenseAssetNames,
        )
    }

    val expectedAbiSets = mutableSetOf<Set<String>>()
    for (abi in supportedAbis) {
        expectedAbiSets += setOf(abi)
    }
    expectedAbiSets += supportedAbis.toSet()
    if (apkFiles.size != supportedAbis.size + 1 || actualAbiSets.toSet() != expectedAbiSets) {
        val inventory = apkFiles.indices.joinToString { index ->
            "${apkFiles[index].name}=${actualAbiSets[index]}"
        }
        throw GradleException("Unexpected OpenCV APK inventory: $inventory")
    }

    val inventory = apkFiles.indices.joinToString { index ->
        "${apkFiles[index].name}=${actualAbiSets[index]}"
    }
    println("Verified OpenCV $variantName APKs: $inventory")
}

var isSignsValid = false

android {
    namespace = globalApplicationId
    compileSdk = versions.sdkVersionCompile

    defaultConfig {
        applicationId = globalApplicationId
        minSdk = versions.sdkVersionMin
        targetSdk = versions.sdkVersionTarget
        versionCode = versions.appVersionCode
        versionName = versions.appVersionName

        buildConfigField("String", "VERSION_DATE", "\"${utils.getDateString("MMM d, yyyy", "GMT+08:00")}\"")
        buildConfigField("String", "OPENCV_VERSION", "\"$openCvVersion\"")
        buildConfigField("String", "OPENCV_NATIVE_LIBRARY", "\"$openCvNativeLibrary\"")

        manifestPlaceholders["openCvContractVersion"] = openCvContractVersion
        manifestPlaceholders["openCvVersion"] = openCvVersion
        manifestPlaceholders["openCvNativeLibrary"] = openCvNativeLibrary
        manifestPlaceholders["openCvJavaApiSha256"] = openCvJavaApiSha256
        manifestPlaceholders["openCvRequiredHostVersion"] = requiredHostVersionCode

        ndk {
            abiFilters += supportedAbis
        }
    }

    signingConfigs {
        val props = Properties().also { properties ->
            File("${project.rootDir}/sign.properties").takeIf { it.exists() }?.let { file ->
                file.inputStream().use { properties.load(it) }
                isSignsValid = properties.isNotEmpty()
            }
        }
        if (isSignsValid) {
            create("release") {
                storeFile = props["storeFile"]?.let { file(it as String) }
                keyPassword = props["keyPassword"] as String
                keyAlias = props["keyAlias"] as String
                storePassword = props["storePassword"] as String
            }
        }
    }

    buildTypes {
        val releaseSigningConfig = takeIf { isSignsValid }?.let {
            signingConfigs.getByName("release")
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            releaseSigningConfig?.let { signingConfig = it }
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            releaseSigningConfig?.let { signingConfig = it }
        }
    }

    splits {
        abi {
            isEnable = true
            reset()
            include(*supportedAbis.toTypedArray())
            isUniversalApk = true
        }
    }

    buildFeatures {
        buildConfig = true
    }

    packaging {
        jniLibs {
            useLegacyPackaging = true
            keepDebugSymbols += "**/libopencv_java4.so"
        }
    }
}

androidComponents {
    onVariants { variant ->
        variant.outputs.forEach { output ->
            val architecture = output.filters.find {
                it.filterType == FilterConfiguration.FilterType.ABI
            }?.identifier ?: "universal"
            val outputFileNameProperty = output.javaClass.methods.firstOrNull {
                it.name == "getOutputFileName" && it.parameterTypes.isEmpty()
            }?.invoke(output) as? Property<*>

            @Suppress("UNCHECKED_CAST")
            (outputFileNameProperty as? Property<String>)?.set(
                output.versionName.map { versionName ->
                    "${rootProject.name}-v$versionName-$architecture.apk".lowercase()
                },
            )
        }
    }
}

dependencies {
    implementation(files("$rootDir/libs/common-plugin-api.aar"))
    implementation(files("$rootDir/libs/opencv-api.aar"))
    implementation(files("$rootDir/libs/opencv-native-4.8.0.aar"))

    testImplementation(libs.junit)
}

tasks {
    withType(JavaCompile::class.java) {
        options.encoding = "UTF-8"
    }

    val verificationTasks = listOf("Debug" to "debug", "Release" to "release").map { (variantTitle, variantName) ->
        register("verifyOpenCv${variantTitle}Apks") {
            group = "verification"
            description = "Verifies OpenCV native payloads in all $variantName APKs"
            dependsOn("assemble$variantTitle")
            inputs.files(openCvNativeAar, openCvJavaApiAar)
            inputs.dir(openCvLicenseAssets)

            doLast {
                val apkDir = layout.buildDirectory.dir("outputs/apk/$variantName").get().asFile
                verifyOpenCvVariantApks(
                    variantName = variantName,
                    apkDirectory = apkDir,
                    nativeAarFile = openCvNativeAar,
                    javaApiAarFile = openCvJavaApiAar,
                    licenseAssetDirectory = openCvLicenseAssets,
                    supportedAbis = supportedAbis,
                    nativeLibraryName = openCvNativeLibrary,
                    javaApiSha256 = openCvJavaApiSha256,
                    requiredLicenseAssetNames = requiredOpenCvLicenseAssetNames,
                )
            }
        }
    }

    register("verifyOpenCvApks") {
        group = "verification"
        description = "Verifies debug and release OpenCV APK artifacts"
        dependsOn(verificationTasks)
    }

    val verifyOpenCvReleaseSigning = register("verifyOpenCvReleaseSigning") {
        group = "verification"
        description = "Requires a configured signing identity for publishable release APKs"
        dependsOn("assembleRelease")

        doLast {
            if (!isSignsValid) {
                throw GradleException(
                    "OpenCV release APKs are unsigned. Configure the ignored sign.properties before publishing.",
                )
            }
            println("Verified configured signing for OpenCV release APKs")
        }
    }

    register("verifyOpenCvPublishableApks") {
        group = "verification"
        description = "Verifies OpenCV APK payloads and requires signed release artifacts"
        dependsOn("verifyOpenCvApks", verifyOpenCvReleaseSigning)
    }

    register<Copy>("appendDigestToReleasedFiles") {
        val buildTypeRelease = "release"
        val ext = utils.FILE_EXTENSION_APK
        val dst = "${buildTypeRelease}s"
        val srcDirs = listOf(file(buildTypeRelease)) + android.productFlavors.map { flavor ->
            file("${flavor.name}/$buildTypeRelease")
        }

        from(srcDirs) {
            include("*.$ext")
            eachFile {
                val suffix = ".$ext"
                val digest = utils.digestCRC32(file)
                name = "${name.removeSuffix(suffix)}-$digest$suffix"
            }
        }
        into(dst)
        includeEmptyDirs = false
        duplicatesStrategy = DuplicatesStrategy.FAIL

        doLast { println("Destination: ${file(dst)}") }
    }
}
