package io.github.supermonster003.autojs6.plugin.opencv

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.autojs.plugin.common.api.IPluginInfoProvider
import org.autojs.plugin.common.api.PluginCapabilityKeys
import org.autojs.plugin.common.api.PluginInfo
import org.autojs.plugin.opencv.api.OpenCvPluginContract
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

@RunWith(AndroidJUnit4::class)
class OpenCvPluginInfoServiceTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun bothDiscoveryActionsBindAndPublishCompleteRuntimeMetadata() {
        val infoAction = bindForInfo("org.autojs.plugin.INFO")
        val openCvAction = bindForInfo("org.autojs.plugin.OPENCV")

        assertPluginInfo(infoAction)
        assertPluginInfo(openCvAction)
        assertEquals(infoAction.id, openCvAction.id)
        assertEquals(infoAction.engine, openCvAction.engine)
        assertEquals(infoAction.variant, openCvAction.variant)
        assertArrayEquals(infoAction.supportedAbis, openCvAction.supportedAbis)
    }

    private fun bindForInfo(action: String): PluginInfo {
        val latch = CountDownLatch(1)
        val binder = AtomicReference<IBinder?>()
        val connection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                binder.set(service)
                latch.countDown()
            }

            override fun onServiceDisconnected(name: ComponentName?) = Unit

            override fun onNullBinding(name: ComponentName?) {
                latch.countDown()
            }

            override fun onBindingDied(name: ComponentName?) {
                latch.countDown()
            }
        }
        val intent = Intent(action)
            .setClass(context, OpenCvPluginInfoService::class.java)
            .addCategory("opencv-runtime")
        assertTrue("Unable to bind $action", context.bindService(intent, connection, Context.BIND_AUTO_CREATE))
        try {
            assertTrue("Timed out binding $action", latch.await(10, TimeUnit.SECONDS))
            val service = binder.get()
            assertNotNull("Service returned a null Binder for $action", service)
            return IPluginInfoProvider.Stub.asInterface(service).info
        } finally {
            context.unbindService(connection)
        }
    }

    private fun assertPluginInfo(info: PluginInfo) {
        assertEquals(OpenCvPluginContract.PLUGIN_ID, info.id)
        assertEquals(OpenCvPluginContract.ENGINE, info.engine)
        assertEquals(OpenCvPluginContract.VARIANT_4_8_0, info.variant)
        assertEquals(BuildConfig.VERSION_NAME, info.versionName)
        assertTrue(info.name?.isNotBlank() == true)
        assertTrue(info.description?.isNotBlank() == true)
        assertTrue(info.instruction?.isNotBlank() == true)

        val processAbis = if (android.os.Process.is64Bit()) {
            Build.SUPPORTED_64_BIT_ABIS
        } else {
            Build.SUPPORTED_32_BIT_ABIS
        }
        val expectedAbi = processAbis.first { it in OpenCvPluginContract.SUPPORTED_ABIS }
        val supportedAbis = info.supportedAbis.orEmpty()
        assertTrue(
            "Plugin APK does not advertise the current process ABI $expectedAbi: ${supportedAbis.contentToString()}",
            expectedAbi in supportedAbis,
        )

        val capabilities = requireNotNull(info.capabilities) { "Plugin capabilities are missing" }
        assertEquals(
            OpenCvPluginContract.REQUIRED_HOST_VERSION_CODE,
            capabilities.getLong(PluginCapabilityKeys.REQUIRES_HOST_VERSION),
        )
        assertEquals(
            OpenCvPluginContract.VERSION,
            capabilities.getInt(OpenCvPluginContract.META_DATA_CONTRACT_VERSION),
        )
        assertEquals(
            OpenCvPluginContract.VARIANT_4_8_0,
            capabilities.getString(OpenCvPluginContract.META_DATA_OPENCV_VERSION),
        )
        assertEquals(
            OpenCvPluginContract.NATIVE_LIBRARY_NAME,
            capabilities.getString(OpenCvPluginContract.META_DATA_NATIVE_LIBRARY),
        )
        assertEquals(
            OpenCvPluginContract.EXPECTED_NATIVE_NDK_VERSION,
            capabilities.getString(OpenCvPluginContract.META_DATA_NATIVE_NDK_VERSION),
        )
        assertEquals(
            OpenCvPluginContract.JAVA_API_SHA256,
            capabilities.getString(OpenCvPluginContract.META_DATA_JAVA_API_SHA256),
        )
    }
}
