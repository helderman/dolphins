package com.dojadragon.dolphins

import android.graphics.Canvas
import android.util.Log
import android.view.SurfaceHolder

// Draw a single frame for the animation on canvas
// Can be used both in a Live Wallpaper and in a regular SurfaceView

class DolphinsFrame(private val painter: DolphinsPainter, private val canvasFactory: DolphinsCanvasFactory) {
    fun drawFrame(surfaceHolder: SurfaceHolder) {
        val canvas: Canvas = surfaceHolder.lockCanvas()
        try {
            canvas.save()
            canvasFactory.create(canvas) { painter.draw(it) }
            canvas.restore()
        } finally {
            try {
                surfaceHolder.unlockCanvasAndPost(canvas)
            } catch (ex: IllegalArgumentException) {
                // Ignore; seems impossible to 100% prevent this
                Log.d("Dolphins", "unlockCanvasAndPost failed", ex)
            }
        }
    }
}
