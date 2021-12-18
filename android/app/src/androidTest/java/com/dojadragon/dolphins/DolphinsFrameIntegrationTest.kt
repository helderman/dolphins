package com.dojadragon.dolphins

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color.*
import android.util.Base64
import android.util.Log
import androidx.annotation.ColorInt
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import java.io.ByteArrayOutputStream
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.abs

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
        val canvas = DolphinsCanvasFactory(appContext).create(Canvas(bitmap))
        val frame = DolphinsFrame(
            DolphinsIndividualFactory(),
            DolphinsVertexFactory(),
            DolphinsVertexFactory()) { time }

        frame.draw(canvas)

        assertMax("Bitmap difference", 60000, bitmapDifference(expected, bitmap))
    }

    @Ignore("For development purposes only")
    @Test
    // Run this test to (re)generate the baseline PNG file for one of the 'real' tests.
    // Filter Logcat with "OutputPNG" to find the BASE64-encoded data. Feed the data into:
    // grep -oP ': \K\S+' | base64 -d > frameXXX.png
    fun generateBaselinePNG() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)
        val canvas = DolphinsCanvasFactory(appContext).create(Canvas(bitmap))
        val frame = DolphinsFrame(
            DolphinsIndividualFactory(),
            DolphinsVertexFactory(),
            DolphinsVertexFactory()) { 1000L }

        frame.draw(canvas)

        base64png(bitmap).lines().forEach { Log.d("OutputPNG", it) }
    }

    private fun base64png(bitmap: Bitmap): String {
        val output = ByteArrayOutputStream().apply {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, this)
        }
        return Base64.encodeToString(output.toByteArray(), Base64.DEFAULT)
    }

    private fun getExpected(context: Context, id: Int) =
        BitmapFactory.decodeResource(
            context.resources,
            id,
            BitmapFactory.Options().apply { inScaled = false }
        )

    // Asserts that the actual value is less than or equal to the expected value.
    private fun assertMax(message: String, expected: Int, actual: Int) =
        assertTrue("$message = $actual, expected <= $expected", actual <= expected)

    private fun bitmapDifference(bitmap1: Bitmap, bitmap2: Bitmap): Int =
        (toPixels(bitmap1) zip toPixels(bitmap2)).sumOf { colorDifference(it.first, it.second) }

    private fun colorDifference(@ColorInt color1: Int, @ColorInt color2: Int) =
        abs(red(color1) - red(color2)) +
        abs(green(color1) - green(color2)) +
        abs(blue(color1) - blue(color2))

    private fun toPixels(bitmap: Bitmap) =
        IntArray(bitmap.width * bitmap.height).apply {
            bitmap.getPixels(this, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        }
}
