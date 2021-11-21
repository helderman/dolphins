package com.dojadragon.dolphins

import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

// Drawing a frame of the animation
class DolphinsFrame(private val clock: () -> Long) {
    fun draw(canvas: IDolphinsCanvas) {
        val time = clock()
        canvas.drawBackground()
        canvas.center()
        canvas.zoom(1.4f)
        canvas.drawDolphins(time)
    }

    private fun IDolphinsCanvas.drawDolphins(time: Long) {
        val camX = cos(0.0002 * time).toFloat()
        val camY = sin(0.0002 * time).toFloat()
        for (i in -1..1) {
            this.drawDolphin(DolphinsState(
                camX,
                camY,
                20f * i,
                -40f * i,
                20f * i,
                0.002 * (time + 300 * i)
            ))
        }
    }

    private fun IDolphinsCanvas.drawDolphin(state: DolphinsState) {
        this.drawTailFin(state)
        this.drawDorsalFin(state)
        this.drawFlippers(state)
        this.drawBody(state)
    }

    private fun IDolphinsCanvas.drawTailFin(state: DolphinsState) {
        for (i in 0..8) {
            val y = 12f * (1.0 - sin(0.14 * abs(i - 5.75))).toFloat()
            val origin = DolphinsVertex(state, i - 61f, 0f)
            this.drawStadium(origin, DolphinsVertex(state, -63f, y, 0f), 2f)
            this.drawStadium(origin, DolphinsVertex(state, -63f, -y, 0f), 2f)
        }
    }

    private fun IDolphinsCanvas.drawDorsalFin(state: DolphinsState) {
        for (i in 0..11) {
            this.drawStadium(
                DolphinsVertex(state, i - 12f, 0f),
                DolphinsVertex(state, i - 27f, 0f, -12f * cos(0.07 * (i - 2.0)).toFloat()),
                2f
            )
        }
    }

    private fun IDolphinsCanvas.drawFlippers(state: DolphinsState) {
        for (i in 0..8) {
            val z = 11f * cos(0.07 * i).toFloat()
            val origin = DolphinsVertex(state, i + 9f, 0f)
            this.drawStadium(origin, DolphinsVertex(state, i - 6f, z, z), 2f)
            this.drawStadium(origin, DolphinsVertex(state, i - 6f, -z, z), 2f)
        }
    }

    private fun IDolphinsCanvas.drawBody(state: DolphinsState) {
        for (i in 0..90) {
            this.drawSphere(
                DolphinsVertex(state, i - 61f, 0f),
                10f - 8f * cos(0.7f * sqrt(91f - i) - 0.63f)
            )
        }
    }

    private fun IDolphinsCanvas.drawSphere(vertex: DolphinsVertex, size: Float) {
        this.setStrokeWidth(size / vertex.distance)
        this.drawPoint(vertex.canvasX, vertex.canvasY)
    }

    private fun IDolphinsCanvas.drawStadium(vertex1: DolphinsVertex, vertex2: DolphinsVertex, size: Float) {
        this.setStrokeWidth(size * 2 / (vertex1.distance + vertex2.distance))
        this.drawLine(vertex1.canvasX, vertex1.canvasY, vertex2.canvasX, vertex2.canvasY)
    }
}
