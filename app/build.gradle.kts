import com.android.build.api.variant.FilterConfiguration
import groovy.json.JsonSlurper
import org.gradle.api.GradleException
import org.gradle.api.provider.Property
import java.io.InputStream
import java.security.MessageDigest
import java.util.Properties
import javax.xml.parsers.DocumentBuilderFactory
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
val openCvContractVersion = 2
val openCvVersion = "4.8.0"
val openCvNativeLibrary = "opencv_java4"
val openCvNativeNdkVersion = "26.1.10909125"
val openCvNativeNdkReleasePattern = Regex("r26b")
val openCvNativeApiLevel = 24
val openCvJavaApiSha256 = "340976552fda3cce525021f0b072427cabf0aa1c786fb80cfc4a3a8105d90b3f"
val requiredHostVersionCode = 5237L
val supportedAbis = listOf("arm64-v8a", "armeabi-v7a", "x86", "x86_64")
val openCvNativeAar = file("$rootDir/libs/opencv-native-4.8.0.aar")
val openCvNativeProvenance = file("$rootDir/libs/opencv-native-4.8.0.provenance.json")
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

data class AndroidNativeIdent(
    val apiLevel: Int,
    val ndkRelease: String,
)

fun ByteArray.readElfUnsigned(offset: Int, size: Int, littleEndian: Boolean): Long {
    if (offset < 0 || size !in 1..8 || offset + size > this.size) {
        throw GradleException("Invalid ELF field at offset $offset with size $size")
    }
    var value = 0L
    for (index in 0 until size) {
        val sourceIndex = if (littleEndian) offset + index else offset + size - index - 1
        value = value or ((this[sourceIndex].toLong() and 0xffL) shl (index * 8))
    }
    return value
}

fun alignElfNote(value: Int): Int = (value + 3) and -4

fun ByteArray.readAndroidNativeIdent(): AndroidNativeIdent {
    if (size < 64 || !copyOfRange(0, 4).contentEquals(byteArrayOf(0x7f, 0x45, 0x4c, 0x46))) {
        throw GradleException("OpenCV payload is not a complete ELF file")
    }
    val elfClass = this[4].toInt() and 0xff
    val littleEndian = when (this[5].toInt() and 0xff) {
        1 -> true
        2 -> false
        else -> throw GradleException("OpenCV ELF has an unsupported byte order")
    }
    val sectionOffset = when (elfClass) {
        1 -> readElfUnsigned(32, 4, littleEndian)
        2 -> readElfUnsigned(40, 8, littleEndian)
        else -> throw GradleException("OpenCV ELF has an unsupported class: $elfClass")
    }.toInt()
    val sectionEntrySize = when (elfClass) {
        1 -> readElfUnsigned(46, 2, littleEndian)
        else -> readElfUnsigned(58, 2, littleEndian)
    }.toInt()
    val sectionCount = when (elfClass) {
        1 -> readElfUnsigned(48, 2, littleEndian)
        else -> readElfUnsigned(60, 2, littleEndian)
    }.toInt()
    val stringTableIndex = when (elfClass) {
        1 -> readElfUnsigned(50, 2, littleEndian)
        else -> readElfUnsigned(62, 2, littleEndian)
    }.toInt()
    if (sectionEntrySize <= 0 || sectionCount <= 0 || stringTableIndex !in 0 until sectionCount) {
        throw GradleException("OpenCV ELF section table is invalid")
    }

    fun sectionField(index: Int, offset32: Int, offset64: Int, size32: Int, size64: Int): Long {
        val header = sectionOffset + index * sectionEntrySize
        return if (elfClass == 1) {
            readElfUnsigned(header + offset32, size32, littleEndian)
        } else {
            readElfUnsigned(header + offset64, size64, littleEndian)
        }
    }

    val stringTableOffset = sectionField(stringTableIndex, 16, 24, 4, 8).toInt()
    val stringTableSize = sectionField(stringTableIndex, 20, 32, 4, 8).toInt()
    if (stringTableOffset < 0 || stringTableSize <= 0 || stringTableOffset + stringTableSize > size) {
        throw GradleException("OpenCV ELF section name table is invalid")
    }
    fun sectionName(index: Int): String {
        val nameOffset = sectionField(index, 0, 0, 4, 4).toInt()
        if (nameOffset !in 0 until stringTableSize) return ""
        val start = stringTableOffset + nameOffset
        var end = start
        while (end < stringTableOffset + stringTableSize && this[end] != 0.toByte()) end++
        return copyOfRange(start, end).toString(Charsets.US_ASCII)
    }

    val androidIdentIndex = (0 until sectionCount).firstOrNull { sectionName(it) == ".note.android.ident" }
        ?: throw GradleException("OpenCV ELF has no .note.android.ident section")
    val noteOffset = sectionField(androidIdentIndex, 16, 24, 4, 8).toInt()
    val noteSize = sectionField(androidIdentIndex, 20, 32, 4, 8).toInt()
    if (noteOffset < 0 || noteSize < 12 || noteOffset + noteSize > size) {
        throw GradleException("OpenCV ELF Android ident section is invalid")
    }
    val nameSize = readElfUnsigned(noteOffset, 4, littleEndian).toInt()
    val descriptionSize = readElfUnsigned(noteOffset + 4, 4, littleEndian).toInt()
    val nameOffset = noteOffset + 12
    val descriptionOffset = alignElfNote(nameOffset + nameSize)
    if (nameSize <= 0 || descriptionSize < 68 || descriptionOffset + descriptionSize > noteOffset + noteSize) {
        throw GradleException("OpenCV ELF Android ident note is invalid")
    }
    val owner = copyOfRange(nameOffset, nameOffset + nameSize)
        .takeWhile { it != 0.toByte() }
        .toByteArray()
        .toString(Charsets.US_ASCII)
    if (owner != "Android") {
        throw GradleException("Unexpected OpenCV ELF Android ident owner: $owner")
    }
    val apiLevel = readElfUnsigned(descriptionOffset, 4, littleEndian).toInt()
    val ndkBytes = copyOfRange(descriptionOffset + 4, descriptionOffset + 68)
    val ndkRelease = ndkBytes.takeWhile { it != 0.toByte() }
        .toByteArray()
        .toString(Charsets.US_ASCII)
    return AndroidNativeIdent(apiLevel, ndkRelease)
}

fun verifyAndroidNativeIdent(
    payload: ByteArray,
    abi: String,
    expectedApiLevel: Int,
    expectedNdkReleasePattern: Regex,
) {
    val ident = payload.readAndroidNativeIdent()
    if (ident.apiLevel != expectedApiLevel || !expectedNdkReleasePattern.matches(ident.ndkRelease)) {
        throw GradleException(
            "OpenCV Android ident mismatch for $abi: " +
                "api=${ident.apiLevel} ndk=${ident.ndkRelease}; " +
                "expected api=$expectedApiLevel ndk=${expectedNdkReleasePattern.pattern}",
        )
    }
}

fun verifyNativeOnlyOpenCvAar(
    nativeAarFile: File,
    supportedAbis: List<String>,
    nativeLibraryName: String,
    expectedApiLevel: Int,
    expectedNdkReleasePattern: Regex,
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
            val payload = aar.getInputStream(entry).use { it.readBytes() }
            verifyElfHeader(payload.copyOf(minOf(payload.size, 20)), abi)
            verifyAndroidNativeIdent(payload, abi, expectedApiLevel, expectedNdkReleasePattern)
            hashes[abi] = payload.sha256Hex()
        }
        return hashes
    }
}

fun verifyOpenCvNativeProvenance(
    provenanceFile: File,
    nativeAarFile: File,
    nativeHashes: Map<String, String>,
    expectedNdkVersion: String,
    expectedApiLevel: Int,
) {
    if (!provenanceFile.isFile) {
        throw GradleException("OpenCV native provenance is missing: $provenanceFile")
    }
    @Suppress("UNCHECKED_CAST")
    val provenance = JsonSlurper().parse(provenanceFile) as? Map<String, Any?>
        ?: throw GradleException("OpenCV native provenance is malformed")
    val opencv = provenance["opencv"] as? Map<*, *>
        ?: throw GradleException("OpenCV provenance has no source metadata")
    val build = provenance["build"] as? Map<*, *>
        ?: throw GradleException("OpenCV provenance has no build metadata")
    val artifact = provenance["artifact"] as? Map<*, *>
        ?: throw GradleException("OpenCV provenance has no artifact metadata")
    if (opencv["version"] != openCvVersion ||
        opencv["commitSha"] != "f9a59f2592993d3dcc080e495f4f5e02dd8ec7ef"
    ) {
        throw GradleException("Unexpected OpenCV source provenance: $opencv")
    }
    if (build["androidNdkVersion"] != expectedNdkVersion ||
        build["androidNdkRelease"] !is String ||
        !openCvNativeNdkReleasePattern.matches(build["androidNdkRelease"] as String) ||
        (build["androidMinSdk"] as? Number)?.toInt() != expectedApiLevel ||
        build["cmakeVersion"] != "3.22.1" ||
        (build["compilerIdent"] as? String)?.contains("clang version 17.0.2") != true ||
        build["stl"] != "c++_shared" ||
        build["libcxxSharedPackaged"] != false
    ) {
        throw GradleException("Unexpected OpenCV native build provenance: $build")
    }
    val aarSha256 = nativeAarFile.inputStream().use { it.sha256Hex() }
    if (artifact["sha256"] != aarSha256) {
        throw GradleException("OpenCV native AAR provenance hash mismatch: $aarSha256")
    }
    val abiMetadata = artifact["abis"] as? Map<*, *>
        ?: throw GradleException("OpenCV provenance has no ABI metadata")
    for ((abi, hash) in nativeHashes) {
        val metadata = abiMetadata[abi] as? Map<*, *>
            ?: throw GradleException("OpenCV provenance is missing $abi")
        if (metadata["sha256"] != hash ||
            (metadata["androidApiLevel"] as? Number)?.toInt() != expectedApiLevel ||
            metadata["androidNdkRelease"] !is String ||
            !openCvNativeNdkReleasePattern.matches(metadata["androidNdkRelease"] as String) ||
            (metadata["neededLibraries"] as? Collection<*>)?.contains("libc++_shared.so") != true
        ) {
            throw GradleException("OpenCV provenance mismatch for $abi: $metadata")
        }
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

fun findApkAnalyzer(sdkDirectory: File): File {
    val executableName = if (System.getProperty("os.name").startsWith("Windows")) {
        "apkanalyzer.bat"
    } else {
        "apkanalyzer"
    }
    val commandLineTools = File(sdkDirectory, "cmdline-tools")
    val candidates = commandLineTools.listFiles()
        .orEmpty()
        .filter(File::isDirectory)
        .sortedWith(compareByDescending<File> { it.name == "latest" }.thenByDescending { it.name })
        .map { File(it, "bin/$executableName") } + File(sdkDirectory, "tools/bin/$executableName")
    return candidates.firstOrNull(File::isFile)
        ?: throw GradleException("Android SDK apkanalyzer is required to verify OpenCV metadata")
}

fun dumpApkManifest(apk: File, sdkDirectory: File): String {
    val analyzer = findApkAnalyzer(sdkDirectory)
    val command = if (System.getProperty("os.name").startsWith("Windows")) {
        listOf("cmd.exe", "/d", "/c", analyzer.absolutePath, "manifest", "print", apk.absolutePath)
    } else {
        listOf(analyzer.absolutePath, "manifest", "print", apk.absolutePath)
    }
    val process = ProcessBuilder(command).redirectErrorStream(true).start()
    val output = process.inputStream.bufferedReader(Charsets.UTF_8).use { it.readText() }
    if (process.waitFor() != 0) {
        throw GradleException("Unable to inspect ${apk.name} manifest:\n$output")
    }
    val manifestStart = output.indexOf("<manifest")
    if (manifestStart < 0) {
        throw GradleException("apkanalyzer returned no manifest for ${apk.name}:\n$output")
    }
    val manifestEnd = output.indexOf("</manifest>", manifestStart)
    if (manifestEnd < 0) {
        throw GradleException("apkanalyzer returned an incomplete manifest for ${apk.name}:\n$output")
    }
    return output.substring(manifestStart, manifestEnd + "</manifest>".length)
}

fun directMetaData(element: org.w3c.dom.Element): Map<String, String> {
    val androidNamespace = "http://schemas.android.com/apk/res/android"
    val result = linkedMapOf<String, String>()
    val children = element.childNodes
    for (index in 0 until children.length) {
        val child = children.item(index) as? org.w3c.dom.Element ?: continue
        if (child.tagName != "meta-data") continue
        result[child.getAttributeNS(androidNamespace, "name")] =
            child.getAttributeNS(androidNamespace, "value")
    }
    return result
}

fun verifyOpenCvManifestMetadata(
    apk: File,
    sdkDirectory: File,
    expectedContractVersion: Int,
    expectedNdkVersion: String,
) {
    val manifest = dumpApkManifest(apk, sdkDirectory)
    val document = DocumentBuilderFactory.newInstance().apply { isNamespaceAware = true }
        .newDocumentBuilder()
        .parse(manifest.byteInputStream(Charsets.UTF_8))
    val application = document.getElementsByTagName("application").item(0) as? org.w3c.dom.Element
        ?: throw GradleException("${apk.name} has no application manifest element")
    val service = (0 until application.childNodes.length)
        .mapNotNull { application.childNodes.item(it) as? org.w3c.dom.Element }
        .firstOrNull { element ->
            element.tagName == "service" &&
                element.getAttributeNS(
                    "http://schemas.android.com/apk/res/android",
                    "name",
                ).endsWith(".OpenCvPluginInfoService")
        }
        ?: throw GradleException("${apk.name} has no OpenCV info service")
    val expected = mapOf(
        "org.autojs.plugin.opencv.CONTRACT_VERSION" to expectedContractVersion.toString(),
        "org.autojs.plugin.opencv.NATIVE_NDK_VERSION" to expectedNdkVersion,
    )
    for ((scope, metadata) in mapOf(
        "application" to directMetaData(application),
        "service" to directMetaData(service),
    )) {
        for ((name, value) in expected) {
            if (metadata[name] != value) {
                throw GradleException(
                    "${apk.name} $scope metadata mismatch for $name: ${metadata[name]}",
                )
            }
        }
    }
}

fun verifyOpenCvApk(
    apk: File,
    expectedLibraryEntries: Map<String, String>,
    nativeHashes: Map<String, String>,
    requiredLicenseAssetNames: Set<String>,
    expectedApiLevel: Int,
    expectedNdkReleasePattern: Regex,
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
            verifyAndroidNativeIdent(payload, abi, expectedApiLevel, expectedNdkReleasePattern)
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
    sdkDirectory: File,
    nativeAarFile: File,
    nativeProvenanceFile: File,
    javaApiAarFile: File,
    licenseAssetDirectory: File,
    supportedAbis: List<String>,
    nativeLibraryName: String,
    contractVersion: Int,
    nativeNdkVersion: String,
    nativeApiLevel: Int,
    nativeNdkReleasePattern: Regex,
    javaApiSha256: String,
    requiredLicenseAssetNames: Set<String>,
) {
    val expectedLibraryEntries = linkedMapOf<String, String>()
    for (abi in supportedAbis) {
        expectedLibraryEntries[abi] = "lib/$abi/lib$nativeLibraryName.so"
    }
    val nativeHashes = verifyNativeOnlyOpenCvAar(
        nativeAarFile,
        supportedAbis,
        nativeLibraryName,
        nativeApiLevel,
        nativeNdkReleasePattern,
    )
    verifyOpenCvNativeProvenance(
        nativeProvenanceFile,
        nativeAarFile,
        nativeHashes,
        nativeNdkVersion,
        nativeApiLevel,
    )
    verifyOpenCvJavaApiFingerprint(javaApiAarFile, javaApiSha256)
    verifyOpenCvLicenseSource(licenseAssetDirectory, requiredLicenseAssetNames)

    val apkFiles = mutableListOf<File>()
    collectApkFiles(apkDirectory, apkFiles)
    apkFiles.sortBy { it.name }
    val actualAbiSets = mutableListOf<Set<String>>()
    for (apk in apkFiles) {
        verifyOpenCvManifestMetadata(
            apk,
            sdkDirectory,
            contractVersion,
            nativeNdkVersion,
        )
        actualAbiSets += verifyOpenCvApk(
            apk,
            expectedLibraryEntries,
            nativeHashes,
            requiredLicenseAssetNames,
            nativeApiLevel,
            nativeNdkReleasePattern,
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
        buildConfigField("String", "OPENCV_NATIVE_NDK_VERSION", "\"$openCvNativeNdkVersion\"")

        manifestPlaceholders["openCvContractVersion"] = openCvContractVersion
        manifestPlaceholders["openCvVersion"] = openCvVersion
        manifestPlaceholders["openCvNativeLibrary"] = openCvNativeLibrary
        manifestPlaceholders["openCvNativeNdkVersion"] = openCvNativeNdkVersion
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
            inputs.files(openCvNativeAar, openCvNativeProvenance, openCvJavaApiAar)
            inputs.dir(openCvLicenseAssets)

            doLast {
                val apkDir = layout.buildDirectory.dir("outputs/apk/$variantName").get().asFile
                verifyOpenCvVariantApks(
                    variantName = variantName,
                    apkDirectory = apkDir,
                    sdkDirectory = androidComponents.sdkComponents.sdkDirectory.get().asFile,
                    nativeAarFile = openCvNativeAar,
                    nativeProvenanceFile = openCvNativeProvenance,
                    javaApiAarFile = openCvJavaApiAar,
                    licenseAssetDirectory = openCvLicenseAssets,
                    supportedAbis = supportedAbis,
                    nativeLibraryName = openCvNativeLibrary,
                    contractVersion = openCvContractVersion,
                    nativeNdkVersion = openCvNativeNdkVersion,
                    nativeApiLevel = openCvNativeApiLevel,
                    nativeNdkReleasePattern = openCvNativeNdkReleasePattern,
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
