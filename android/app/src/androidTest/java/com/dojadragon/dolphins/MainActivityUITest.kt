package com.dojadragon.dolphins

import android.app.WallpaperInfo
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private const val DOLPHINS_PACKAGE = "com.dojadragon.dolphins"
private const val LAUNCH_TIMEOUT = 5000L
private const val IDLE_TIMEOUT = 30000L

// UI tests
// https://developer.android.com/training/testing/ui-testing/uiautomator-testing

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class MainActivityUITest {
    private lateinit var device: UiDevice

    @Before
    fun startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Start from the home screen
        device.pressHome()

        // Wait for launcher
        val launcherPackage: String = device.launcherPackageName
        assertNotNull(launcherPackage)
        device.wait(
            Until.hasObject(By.pkg(launcherPackage).depth(0)),
            LAUNCH_TIMEOUT
        )

        // Launch the app
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(DOLPHINS_PACKAGE).apply {
            // Clear out any previous instances
            this!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)

        // Wait for the app to appear
        device.wait(
            Until.hasObject(By.pkg(DOLPHINS_PACKAGE).depth(0)),
            LAUNCH_TIMEOUT
        )
    }

    @Test
    fun setWallpaperHome() = setWallpaper("Home screen")

    @Test
    fun setWallpaperHomeAndLock() = setWallpaper("Home screen and lock screen")

    // Arrange, Act, Assert
    private fun setWallpaper(captionOfChoice: String) {
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        val wallpaperManager = WallpaperManager.getInstance(appContext)
        assertNull("Live wallpaper", wallpaperManager.wallpaperInfo)

        findAndClickWidget("Button", "Set wallpaper")
        findAndClickWidget("TextView", captionOfChoice)

        assertEquals(
            DolphinsWallpaperService::class.qualifiedName,
            findWallpaperInfo(wallpaperManager).serviceName
        )
    }

    private fun findAndClickWidget(type: String, caption: String) {
        val condition = By.clazz("android.widget.$type").text(caption)
        val widget = device.wait(Until.findObject(condition), LAUNCH_TIMEOUT)
        assertNotNull("$type '$caption' expected", widget)
        widget.click()
    }

    private fun findWallpaperInfo(wallpaperManager: WallpaperManager): WallpaperInfo {
        device.waitForIdle(IDLE_TIMEOUT)
        val wallpaperInfo = wallpaperManager.wallpaperInfo
        assertNotNull("Live wallpaper", wallpaperInfo)
        return wallpaperInfo
    }
}
