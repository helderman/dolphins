package com.dojadragon.dolphins

// Canvas abstraction.
// Need this to be able to mock Canvas in unit tests.

interface IDolphinsCanvas {
    fun center()
    fun drawBackground()
    fun drawLine(x1: Float, y1: Float, x2: Float, y2: Float)
    fun drawPoint(x: Float, y: Float)
    fun setStrokeWidth(strokeWidth: Float)
    fun zoom(factor: Float)
}
