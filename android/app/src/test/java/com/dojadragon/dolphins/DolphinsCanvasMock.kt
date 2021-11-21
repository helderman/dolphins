package com.dojadragon.dolphins

class DolphinsCanvasMock : IDolphinsCanvas {
    var log = ""

    override fun center() {
        log += "center()\n"
    }
    override fun drawBackground() {
        log += "drawBackground()\n"
    }
    override fun drawLine(x1: Float, y1: Float, x2: Float, y2: Float) {
        log += "drawLine($x1, $y1, $x2, $y2)\n"
    }
    override fun drawPoint(x: Float, y: Float) {
        log += "drawPoint($x, $y)\n"
    }
    override fun setStrokeWidth(strokeWidth: Float) {
        log += "setStrokeWidth($strokeWidth)\n"
    }
    override fun zoom(factor: Float) {
        log += "zoom($factor)\n"
    }
}
