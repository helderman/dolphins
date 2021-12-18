package com.dojadragon.dolphins

import kotlin.math.sin

// Creates a vertex with 3D coordinates mapped to 2D canvas coordinates.
// This implementation is extremely economic:
// it overwrites and returns the same (mutable) instance of DolphinsVertex over and over again.
// Generally this is bad practice, but it helps make the app more efficient (by avoiding GC).
// This implies that if you want to keep 2 vertices at the same time, you need 2 factories.

class DolphinsVertexFactory {
    fun create(individual: IDolphinsIndividual, x: Float, y: Float, z: Float = 0f): IDolphinsVertex {
        val totalX = individual.dolphinX + x
        val totalY = individual.dolphinY + y
        return vertex.apply {
            distance = 150f + totalX * individual.camX + totalY * individual.camY
            canvasX = (totalX * individual.camY - totalY * individual.camX) / distance
            canvasY = (z + individual.dolphinZ + 20f * sin(individual.wave + 0.017 * x).toFloat()) / distance
        }
    }

    private val vertex = DolphinsVertex()

    private inner class DolphinsVertex : IDolphinsVertex {
        override var distance = 0f
        override var canvasX = 0f
        override var canvasY = 0f
    }
}
