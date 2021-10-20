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
    private val backgroundTop = ContextCompat.getColor(context, R.color.dolphins_background_top)
    private val backgroundBottom = ContextCompat.getColor(context, R.color.dolphins_background_bottom)
    private val paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.dolphins_body)
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        style = Paint.Style.STROKE
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
            val origin = Vertex(wave, i - 61f, 0f)
            this.drawStadium(origin, Vertex(wave, -63f, y, 0f), 2f)
            this.drawStadium(origin, Vertex(wave, -63f, -y, 0f), 2f)
        }
    }

    private fun Canvas.drawDorsalFin(wave: Double) {
        for (i in 0..11) {
            this.drawStadium(
                Vertex(wave, i - 12f, 0f),
                Vertex(wave, i - 27f, 0f, -12f * cos(0.07 * (i - 2.0)).toFloat()),
                2f
            )
        }
    }

    private fun Canvas.drawFlippers(wave: Double) {
        for (i in 0..8) {
            val z = 11f * cos(0.07 * i).toFloat()
            val origin = Vertex(wave, i + 9f, 0f)
            this.drawStadium(origin, Vertex(wave, i - 6f, z, z), 2f)
            this.drawStadium(origin, Vertex(wave, i - 6f, -z, z), 2f)
        }
    }

    private fun Canvas.drawBody(wave: Double) {
        for (i in 0..90) {
            this.drawSphere(
                Vertex(wave, i - 61f, 0f),
                10f - 8f * cos(0.7f * sqrt(91f - i) - 0.63f)
            )
        }
    }

    private fun Canvas.drawSphere(vertex: Vertex, size: Float) {
        paint.strokeWidth = size / vertex.distance
        this.drawPoint(vertex.canvasX, vertex.canvasY, paint)
    }

    private fun Canvas.drawStadium(vertex1: Vertex, vertex2: Vertex, size: Float) {
        paint.strokeWidth = size * 2 / (vertex1.distance + vertex2.distance)
        this.drawLine(vertex1.canvasX, vertex1.canvasY, vertex2.canvasX, vertex2.canvasY, paint)
    }

    private inner class Vertex(wave: Double, x: Float, y: Float, z: Float = 0f) {
        private val totalX = dolphinX + x
        private val totalY = dolphinY + y
        val distance = 150f + totalX * camX + totalY * camY
        val canvasX = (totalX * camY - totalY * camX) / distance
        val canvasY = (z + dolphinZ + 20f * sin(wave + 0.017 * x).toFloat()) / distance
    }
}
