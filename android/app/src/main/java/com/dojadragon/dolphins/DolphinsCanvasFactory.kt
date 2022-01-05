package com.dojadragon.dolphins

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat

// Creates a wrapper around a Canvas (so that it can be mocked in unit tests).
// This implementation is extremely economic:
// it overwrites and returns the same (mutable) instance of DolphinsCanvas over and over again.
// Generally this is bad practice, but it helps make the app more efficient (by avoiding GC).

class DolphinsCanvasFactory(private val context: Context) {
    fun create(canvas: Canvas, action: (IDolphinsCanvas) -> Unit) {
        dolphinsCanvas.let {
            it.canvas = canvas
            action(it)
            it.canvas = null
        }
    }

    private val dolphinsCanvas = DolphinsCanvas()

    private inner class DolphinsCanvas : IDolphinsCanvas {
        var canvas: Canvas? = null

        private val paint = Paint().apply {
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
        }

        override fun center() {
            canvas!!.translate(0.5f * canvas!!.width, 0.5f * canvas!!.height)
        }

        override fun drawBackground() {
            paint.color = ContextCompat.getColor(context, R.color.dolphins_body)
            paint.shader = LinearGradient(
                0f, 0f, 0f, 1f * canvas!!.height,
                ContextCompat.getColor(context, R.color.dolphins_background_top),
                ContextCompat.getColor(context, R.color.dolphins_background_bottom),
                Shader.TileMode.CLAMP
            )
            canvas!!.drawPaint(paint)
            paint.shader = null
        }

        override fun drawLine(x1: Float, y1: Float, x2: Float, y2: Float) {
            canvas!!.drawLine(x1, y1, x2, y2, paint)
        }

        override fun drawPoint(x: Float, y: Float) {
            canvas!!.drawPoint(x, y, paint)
        }

        override fun setStrokeWidth(strokeWidth: Float) {
            paint.strokeWidth = strokeWidth
        }

        override fun zoom(factor: Float) {
            val s = factor * minOf(canvas!!.width, canvas!!.height)
            canvas!!.scale(s, s)
        }
    }
}
