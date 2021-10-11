package com.dojadragon.dolphins

import android.content.Context
import android.graphics.*
import android.view.SurfaceHolder
import androidx.core.content.ContextCompat
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class DolphinsDrawer(context: Context, private val clock: () -> Long) {
    private val paint = Paint()
    private val backgroundTop = ContextCompat.getColor(context, R.color.dolphins_background_top)
    private val backgroundBottom = ContextCompat.getColor(context, R.color.dolphins_background_bottom)

    init {
        paint.color = ContextCompat.getColor(context, R.color.dolphins_body)
        paint.isAntiAlias = true
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeJoin = Paint.Join.ROUND
        paint.style = Paint.Style.STROKE
    }

    private var width = 0
    private var height = 0

    fun setDimensions(width : Int, height : Int) {
        this.width = width
        this.height = height
    }

    private var camX = 0f
    private var camY = 0f
    private var dolphinX = 0f
    private var dolphinY = 0f
    private var dolphinZ = 0f

    fun drawFrame(surfaceHolder : SurfaceHolder) {
        var canvas : Canvas? = null
        try {
            canvas = surfaceHolder.lockCanvas()
            if (canvas != null) {
                val zoom = 1.4f * minOf(width, height)
                val time = clock()
                camX = cos(0.0002 * time).toFloat()
                camY = sin(0.0002 * time).toFloat()
                canvas.save()
                canvas.drawBackground()
                canvas.translate(0.5f * width, 0.5f * height)
                canvas.scale(zoom, zoom)
                canvas.drawDolphins(time)
                canvas.restore()
            }
        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas)
            }
        }
    }

    private fun Canvas.drawBackground() {
        paint.shader = LinearGradient(
            0f, 0f, 0f, 1f * height,
            backgroundTop, backgroundBottom, Shader.TileMode.CLAMP)
        this.drawPaint(paint)
        paint.shader = null
    }

    private fun Canvas.drawDolphins(time: Long) {
        for (i in -1..1) {
            dolphinX = 20f * i
            dolphinY = -40f * i
            dolphinZ = 20f * i
            this.drawDolphin(0.002 * (time + 300 * i))
        }
    }

    private fun Canvas.drawDolphin(wave: Double) {
        this.drawTailFin(wave)
        this.drawDorsalFin(wave)
        this.drawFlippers(wave)
        this.drawBody(wave)
    }

    private fun Canvas.drawTailFin(wave: Double) {
        for (i in 0..8) {
            val y = 12f * (1.0 - sin(0.14 * abs(i - 5.75))).toFloat()
            this.drawInMotion(wave, i - 61f, 0f, -63f, y, 0f, 2f)
            this.drawInMotion(wave, i - 61f, 0f, -63f, -y, 0f, 2f)
        }
    }

    private fun Canvas.drawDorsalFin(wave: Double) {
        for (i in 0..11) {
            val z = -12f * cos(0.07 * (i - 2.0)).toFloat()
            this.drawInMotion(wave, i - 12f, 0f, i - 27f, 0f, z, 2f)
        }
    }

    private fun Canvas.drawFlippers(wave: Double) {
        for (i in 0..8) {
            val z = 11f * cos(0.07 * i).toFloat()
            this.drawInMotion(wave, i + 9f, 0f, i - 6f, z, z, 2f)
            this.drawInMotion(wave, i + 9f, 0f, i - 6f, -z, z, 2f)
        }
    }

    private fun Canvas.drawBody(wave: Double) {
        for (i in 0..90) {
            this.drawInMotion(wave, i - 61f, 0f, 10f - 8f * cos(0.7f * sqrt(91f - i) - 0.63f))
        }
    }

    private fun Canvas.drawInMotion(wave: Double, x1: Float, y1: Float, x2: Float, y2: Float, z2: Float, size: Float) {
        val totalX1 = dolphinX + x1
        val totalY1 = dolphinY + y1
        val totalX2 = dolphinX + x2
        val totalY2 = dolphinY + y2
        val distance1 = 150f + totalX1 * camX + totalY1 * camY
        val distance2 = 150f + totalX2 * camX + totalY2 * camY
        paint.strokeWidth = size * 2 / (distance1 + distance2)
        this.drawLine(
            (totalX1 * camY - totalY1 * camX) / distance1,
            (dolphinZ + 20f * sin(wave + 0.017 * x1).toFloat()) / distance1,
            (totalX2 * camY - totalY2 * camX) / distance2,
            (z2 + dolphinZ + 20f * sin(wave + 0.017 * x2).toFloat()) / distance2,
            paint)
    }

    private fun Canvas.drawInMotion(wave: Double, x: Float, y: Float, size: Float) {
        val totalX = dolphinX + x
        val totalY = dolphinY + y
        val distance = 150f + totalX * camX + totalY * camY
        paint.strokeWidth = size / distance
        this.drawPoint(
            (totalX * camY - totalY * camX) / distance,
            (dolphinZ + 20f * sin(wave + 0.017 * x).toFloat()) / distance,
            paint)
    }
}
