package com.dojadragon.dolphins

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat

// Creates a wrapper around a Canvas
class DolphinsCanvasFactory(private val context: Context) {
    fun Create(canvas: Canvas): IDolphinsCanvas = DolphinsCanvas(canvas)

    private inner class DolphinsCanvas(private val canvas: Canvas) : IDolphinsCanvas {
        @ColorInt
        private val backgroundTop = ContextCompat.getColor(context, R.color.dolphins_background_top)
        @ColorInt
        private val backgroundBottom = ContextCompat.getColor(context, R.color.dolphins_background_bottom)

        private val paint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.dolphins_body)
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
            style = Paint.Style.STROKE
        }

        override fun center() {
            canvas.translate(0.5f * canvas.width, 0.5f * canvas.height)
        }

        override fun drawBackground() {
            paint.shader = LinearGradient(
                0f, 0f, 0f, 1f * canvas.height,
                backgroundTop, backgroundBottom, Shader.TileMode.CLAMP
            )
            canvas.drawPaint(paint)
            paint.shader = null
        }

        override fun drawLine(x1: Float, y1: Float, x2: Float, y2: Float) {
            canvas.drawLine(x1, y1, x2, y2, paint)
        }

        override fun drawPoint(x: Float, y: Float) {
            canvas.drawPoint(x, y, paint)
        }

        override fun setStrokeWidth(strokeWidth: Float) {
            paint.strokeWidth = strokeWidth
        }

        override fun zoom(factor: Float) {
            val s = factor * minOf(canvas.width, canvas.height)
            canvas.scale(s, s)
        }
    }
}
