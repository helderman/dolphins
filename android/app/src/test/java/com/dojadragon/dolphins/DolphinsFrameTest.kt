package com.dojadragon.dolphins

import org.junit.Assert.*
import org.junit.Test

// Unit tests for class DolphinsFrame

class DolphinsFrameTest {

    @Test
    // Captures drawing actions at time = 0 ms
    fun draw_0s() = draw_Xs(0L, "frame0.txt")

    @Test
    // Captures drawing actions at time = 1000 ms
    fun draw_1s() = draw_Xs(1000L, "frame1.txt")

    // Arrange, Act, Assert
    private fun draw_Xs(time: Long, filename: String) {
        val expected = getTxt(filename)
        val canvas = DolphinsCanvasMock()
        val frame = DolphinsFrame(DolphinsIndividualFactory(), DolphinsVertexFactory()) { time }

        frame.draw(canvas)

        assertEquals(expected, canvas.log)
    }

    private fun getTxt(filename: String) =
        ClassLoader.getSystemResource(filename).readText().replace("\r", "")
}
