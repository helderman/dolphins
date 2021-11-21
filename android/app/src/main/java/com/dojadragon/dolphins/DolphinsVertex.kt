package com.dojadragon.dolphins

import kotlin.math.sin

// 3D to 2D projection
class DolphinsVertex(state: DolphinsState, x: Float, y: Float, z: Float = 0f) {
    private val totalX = state.dolphinX + x
    private val totalY = state.dolphinY + y
    val distance = 150f + totalX * state.camX + totalY * state.camY
    val canvasX = (totalX * state.camY - totalY * state.camX) / distance
    val canvasY = (z + state.dolphinZ + 20f * sin(state.wave + 0.017 * x).toFloat()) / distance
}
