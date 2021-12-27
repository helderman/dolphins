package com.dojadragon.dolphins

import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

// Painting 3D shapes that resemble a group of dolphins

class DolphinsPainter(
    private val individualFactory: DolphinsIndividualFactory,
    private val vertexFactory1: DolphinsVertexFactory,
    private val vertexFactory2: DolphinsVertexFactory,
    private val clock: () -> Long
) {
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
            drawDolphin(individualFactory.create(
                camX,
                camY,
                20f * i,
                -40f * i,
                20f * i,
                0.002 * (time + 300 * i)
            ))
        }
    }

    private fun IDolphinsCanvas.drawDolphin(individual: IDolphinsIndividual) {
        drawTailFin(individual)
        drawDorsalFin(individual)
        drawFlippers(individual)
        drawBody(individual)
    }

    private fun IDolphinsCanvas.drawTailFin(individual: IDolphinsIndividual) {
        for (i in 0..8) {
            val y = 12f * (1.0 - sin(0.14 * abs(i - 5.75))).toFloat()
            val origin = vertexFactory1.create(individual, i - 61f, 0f)
            drawStadium(origin, vertexFactory2.create(individual, -63f, y), 2f)
            drawStadium(origin, vertexFactory2.create(individual, -63f, -y), 2f)
        }
    }

    private fun IDolphinsCanvas.drawDorsalFin(individual: IDolphinsIndividual) {
        for (i in 0..11) {
            drawStadium(
                vertexFactory1.create(individual, i - 12f, 0f),
                vertexFactory2.create(individual, i - 27f, 0f, -12f * cos(0.07 * (i - 2.0)).toFloat()),
                2f
            )
        }
    }

    private fun IDolphinsCanvas.drawFlippers(individual: IDolphinsIndividual) {
        for (i in 0..8) {
            val z = 11f * cos(0.07 * i).toFloat()
            val origin = vertexFactory1.create(individual, i + 9f, 0f)
            drawStadium(origin, vertexFactory2.create(individual, i - 6f, z, z), 2f)
            drawStadium(origin, vertexFactory2.create(individual, i - 6f, -z, z), 2f)
        }
    }

    private fun IDolphinsCanvas.drawBody(individual: IDolphinsIndividual) {
        for (i in 0..90) {
            drawSphere(
                vertexFactory1.create(individual, i - 61f, 0f),
                10f - 8f * cos(0.7f * sqrt(91f - i) - 0.63f)
            )
        }
    }

    private fun IDolphinsCanvas.drawSphere(vertex: IDolphinsVertex, size: Float) {
        setStrokeWidth(size / vertex.distance)
        drawPoint(vertex.canvasX, vertex.canvasY)
    }

    private fun IDolphinsCanvas.drawStadium(vertex1: IDolphinsVertex, vertex2: IDolphinsVertex, size: Float) {
        setStrokeWidth(size * 2 / (vertex1.distance + vertex2.distance))
        drawLine(vertex1.canvasX, vertex1.canvasY, vertex2.canvasX, vertex2.canvasY)
    }
}
