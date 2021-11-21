package com.dojadragon.dolphins

import org.junit.Test
import org.junit.Assert.*

// Unit tests for class DolphinsFrame
class DolphinsFrameTest {

    // Captures drawing actions at time = 0 ms
    @Test
    fun draw_0s() {
        val canvas = DolphinsCanvasMock()
        val frame = DolphinsFrame() { 0L }
        val expected = getTxt("frame0.txt")

        frame.draw(canvas)

        assertEquals(expected, canvas.log)
    }

    // Captures drawing actions at time = 1000 ms
    @Test
    fun draw_1s() {
        val canvas = DolphinsCanvasMock()
        val frame = DolphinsFrame() { 1000L }
        val expected = getTxt("frame1.txt")

        frame.draw(canvas)

        assertEquals(expected, canvas.log)
    }

    private fun getTxt(filename: String) =
        ClassLoader.getSystemResource(filename).readText().replace("\r", "")
}
