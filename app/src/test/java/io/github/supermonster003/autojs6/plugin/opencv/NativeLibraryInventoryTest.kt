package io.github.supermonster003.autojs6.plugin.opencv

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test
import org.autojs.plugin.opencv.api.OpenCvPluginContract

class NativeLibraryInventoryTest {

    @Test
    fun `reports only packaged OpenCV ABIs in canonical order`() {
        val entries = listOf(
            "lib/x86_64/libopencv_java4.so",
            "lib/arm64-v8a/libopencv_java4.so",
            "lib/x86_64/libunrelated.so",
            "classes.dex",
        )

        assertArrayEquals(
            arrayOf("arm64-v8a", "x86_64"),
            NativeLibraryInventory.supportedAbis(entries),
        )
    }

    @Test
    fun `ignores wrong library names and unsupported ABIs`() {
        val entries = listOf(
            "lib/arm64-v8a/libopencv_java3.so",
            "lib/riscv64/libopencv_java4.so",
        )

        assertArrayEquals(emptyArray<String>(), NativeLibraryInventory.supportedAbis(entries))
    }

    @Test
    fun `publishes stable compatibility contract`() {
        assertEquals("opencv", OpenCvPluginContract.PLUGIN_ID)
        assertEquals("opencv", OpenCvPluginContract.ENGINE)
        assertEquals("4.8.0", OpenCvPluginContract.VARIANT_4_8_0)
        assertEquals(2, OpenCvPluginContract.VERSION)
        assertEquals(5237L, OpenCvPluginContract.REQUIRED_HOST_VERSION_CODE)
        assertEquals("opencv_java4", OpenCvPluginContract.NATIVE_LIBRARY_NAME)
        assertEquals("26.1.10909125", OpenCvPluginContract.EXPECTED_NATIVE_NDK_VERSION)
        assertEquals(
            OpenCvPluginContract.EXPECTED_NATIVE_NDK_VERSION,
            BuildConfig.OPENCV_NATIVE_NDK_VERSION,
        )
        assertEquals(
            "340976552fda3cce525021f0b072427cabf0aa1c786fb80cfc4a3a8105d90b3f",
            OpenCvPluginContract.JAVA_API_SHA256,
        )
    }

    @Test
    fun `fallback ABI follows current process bitness`() {
        val supported32BitAbis = listOf("armeabi-v7a", "x86")
        val supported64BitAbis = listOf("arm64-v8a", "x86_64")

        assertEquals(
            supported32BitAbis,
            NativeLibraryInventory.processSupportedAbis(false, supported32BitAbis, supported64BitAbis),
        )
        assertEquals(
            supported64BitAbis,
            NativeLibraryInventory.processSupportedAbis(true, supported32BitAbis, supported64BitAbis),
        )
    }
}
