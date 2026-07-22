package io.github.supermonster003.autojs6.plugin.opencv

import android.content.Context
import android.content.pm.PackageInfo
import android.os.Build
import android.os.Bundle
import org.autojs.plugin.common.api.PluginCapabilityKeys
import org.autojs.plugin.common.api.PluginInfo
import org.autojs.plugin.opencv.api.OpenCvPluginContract

internal fun Context.openCvPluginInfo(): PluginInfo {
    val packageInfo = packageManager.getPackageInfo(packageName, 0)
    return PluginInfo().apply {
        name = getString(R.string.app_name)
        description = getString(R.string.plugin_description)
        instruction = resources.openRawResource(R.raw.plugin_instruction)
            .bufferedReader(Charsets.UTF_8)
            .use { it.readText().trim() }
        author = "SuperMonster003"
        versionName = packageInfo.versionName.orEmpty()
        versionCode = packageInfo.versionCodeCompat()
        versionDate = BuildConfig.VERSION_DATE
        id = OpenCvPluginContract.PLUGIN_ID
        engine = OpenCvPluginContract.ENGINE
        variant = OpenCvPluginContract.VARIANT_4_8_0
        supportedAbis = NativeLibraryInventory.supportedAbis(this@openCvPluginInfo)
        capabilities = Bundle().apply {
            putLong(PluginCapabilityKeys.REQUIRES_HOST_VERSION, OpenCvPluginContract.REQUIRED_HOST_VERSION_CODE)
            putInt(OpenCvPluginContract.META_DATA_CONTRACT_VERSION, OpenCvPluginContract.VERSION)
            putString(OpenCvPluginContract.META_DATA_OPENCV_VERSION, OpenCvPluginContract.VARIANT_4_8_0)
            putString(OpenCvPluginContract.META_DATA_NATIVE_LIBRARY, OpenCvPluginContract.NATIVE_LIBRARY_NAME)
            putString(
                OpenCvPluginContract.META_DATA_NATIVE_NDK_VERSION,
                BuildConfig.OPENCV_NATIVE_NDK_VERSION,
            )
            putString(OpenCvPluginContract.META_DATA_JAVA_API_SHA256, OpenCvPluginContract.JAVA_API_SHA256)
        }
    }
}

private fun PackageInfo.versionCodeCompat(): Long {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) return longVersionCode
    @Suppress("DEPRECATION")
    return versionCode.toLong()
}
