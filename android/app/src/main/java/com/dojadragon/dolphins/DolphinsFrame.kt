package com.dojadragon.dolphins

import android.graphics.Canvas
import android.util.Log
import android.view.SurfaceHolder
import androidx.core.graphics.withSave

// Draw a single frame for the animation on canvas
// Can be used both in a Live Wallpaper and in a regular SurfaceView

class DolphinsFrame(private val painter: DolphinsPainter, private val canvasFactory: DolphinsCanvasFactory) {
    fun drawFrame(surfaceHolder: SurfaceHolder) {
        val canvas: Canvas? = surfaceHolder.lockCanvas()
        if (canvas != null) {
            try {
                canvas.withSave() {
                    canvasFactory.create(this) { painter.draw(it) }
                }
            } finally {
                try {
                    surfaceHolder.unlockCanvasAndPost(canvas)
                } catch (ex: IllegalArgumentException) {
                    // Ignore; seems impossible to 100% prevent this
                    Log.d("Dolphins", "unlockCanvasAndPost failed", ex)
                } catch (ex: IllegalStateException) {
                    // "Surface has already been released" - June 27, 2022
                    Log.d("Dolphins", "unlockCanvasAndPost failed", ex)
                }
            }
        }
    }
}
