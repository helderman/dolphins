package com.dojadragon.dolphins

import kotlin.math.sin

class DolphinsVertexFactory {
    fun create(individual: IDolphinsIndividual, x: Float, y: Float, z: Float = 0f): IDolphinsVertex {
        val totalX = individual.dolphinX + x
        val totalY = individual.dolphinY + y
        val distance = 150f + totalX * individual.camX + totalY * individual.camY
        return DolphinsVertex(
            distance,
            (totalX * individual.camY - totalY * individual.camX) / distance,
            (z + individual.dolphinZ + 20f * sin(individual.wave + 0.017 * x).toFloat()) / distance
        )
    }

    private inner class DolphinsVertex(
        override val distance: Float,
        override val canvasX: Float,
        override val canvasY: Float) : IDolphinsVertex
}