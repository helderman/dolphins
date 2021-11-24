package com.dojadragon.dolphins

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Base64
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import java.io.ByteArrayOutputStream
import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

// Integration tests for class DolphinsFrame
@RunWith(AndroidJUnit4::class)
class DolphinsFrameIntegrationTest {

    @Test
    // Captures bitmap at time = 0 ms
    fun draw_0s() = draw_Xs(0L, R.raw.frame0)

    @Test
    // Captures bitmap at time = 1000 ms
    fun draw_1s() = draw_Xs(1000L, R.raw.frame1)

    // Arrange, Act, Assert
    private fun draw_Xs(time: Long, resourceId: Int) {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val expected = getExpected(appContext, resourceId)
        val bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)
        val canvas = DolphinsCanvasFactory(appContext).Create(Canvas(bitmap))
        val frame = DolphinsFrame { time }

        frame.draw(canvas)

        assertTrue(bitmap.sameAs(expected))
    }

    @Ignore
    @Test
    // Run this test to (re)generate the baseline PNG file for one of the 'real' tests.
    // Filter Logcat with "BaselinePNG" to find the BASE64-encoded data. Feed the data into:
    // grep -oP '\S+$' | base64 -d > frameXXX.png
    fun generateBaselinePNG() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)
        val canvas = DolphinsCanvasFactory(appContext).Create(Canvas(bitmap))
        val frame = DolphinsFrame { 1000L }

        frame.draw(canvas)

        Log.d("BaselinePNG", base64png(bitmap))
    }

    private fun getExpected(context: Context, id: Int) =
        BitmapFactory.decodeResource(
            context.resources,
            id,
            BitmapFactory.Options().apply { inScaled = false }
        )

    private fun base64png(bitmap: Bitmap): String {
        val output = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
        return Base64.encodeToString(output.toByteArray(), Base64.DEFAULT)
    }
}
