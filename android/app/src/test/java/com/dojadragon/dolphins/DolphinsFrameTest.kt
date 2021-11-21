package com.dojadragon.dolphins

import org.junit.Test
import org.junit.Assert.*

// Unit tests for class DolphinsFrame
class DolphinsFrameTest {
    @Test
    fun draw_time0() {
        //val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        //val canvas = Canvas(bitmap)
        val canvas = DolphinsCanvasMock()
        val frame = DolphinsFrame() { 0L }
        frame.draw(canvas)
        //print(canvas.log)
        //assertEquals("TODO", canvas.log)
    }
}
