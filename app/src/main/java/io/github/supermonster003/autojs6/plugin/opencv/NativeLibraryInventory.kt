package io.github.supermonster003.autojs6.plugin.opencv

import android.content.Context
import android.os.Build
import org.autojs.plugin.opencv.api.OpenCvPluginContract
import java.io.File
import java.util.zip.ZipFile

internal object NativeLibraryInventory {

    fun supportedAbis(context: Context): Array<String> {
        val applicationInfo = context.applicationInfo
        return supportedAbisFromApks(
            apkPaths = (listOfNotNull(applicationInfo.sourceDir) + applicationInfo.splitSourceDirs.orEmpty()).map(::File),
            extractedLibrary = applicationInfo.nativeLibraryDir?.let {
                File(it, OpenCvPluginContract.NATIVE_LIBRARY_FILE_NAME)
            },
            is64Bit = android.os.Process.is64Bit(),
            supported32BitAbis = Build.SUPPORTED_32_BIT_ABIS.toList(),
            supported64BitAbis = Build.SUPPORTED_64_BIT_ABIS.toList(),
        )
    }

    fun supportedAbisFromApks(
        apkPaths: Iterable<File>,
        extractedLibrary: File?,
        is64Bit: Boolean,
        supported32BitAbis: List<String>,
        supported64BitAbis: List<String>,
    ): Array<String> {
        val entries = buildSet {
            apkPaths.forEach { path ->
                runCatching {
                    ZipFile(path).use { zip ->
                        val enumeration = zip.entries()
                        while (enumeration.hasMoreElements()) {
                            add(enumeration.nextElement().name)
                        }
                    }
                }
            }
        }
        val packagedAbis = supportedAbis(entries)
        if (packagedAbis.isNotEmpty()) return packagedAbis

        val loadedAbi = processSupportedAbis(
            is64Bit = is64Bit,
            supported32BitAbis = supported32BitAbis,
            supported64BitAbis = supported64BitAbis,
        ).firstOrNull { it in OpenCvPluginContract.SUPPORTED_ABIS }.takeIf { extractedLibrary?.isFile == true }
        return listOfNotNull(loadedAbi).toTypedArray()
    }

    fun supportedAbis(entryNames: Iterable<String>): Array<String> {
        val entries = entryNames.toHashSet()
        return OpenCvPluginContract.SUPPORTED_ABIS.filter { abi ->
            "lib/$abi/${OpenCvPluginContract.NATIVE_LIBRARY_FILE_NAME}" in entries
        }.toTypedArray()
    }

    fun processSupportedAbis(
        is64Bit: Boolean,
        supported32BitAbis: List<String>,
        supported64BitAbis: List<String>,
    ): List<String> = if (is64Bit) supported64BitAbis else supported32BitAbis
}
