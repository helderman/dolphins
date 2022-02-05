package com.dojadragon.dolphins

import android.util.Log
import android.view.SurfaceHolder

// Draw a single frame for the animation on canvas
// Can be used both in a Live Wallpaper and in a regular SurfaceView

class DolphinsFrame(
    private val painter: DolphinsPainter,
    private val canvasFactory: DolphinsCanvasFactory
) {
    fun drawFrame(surfaceHolder: SurfaceHolder, synchronizer: IDolphinsSynchronizer) {
        synchronizer.ifAllowed {
            changedSurface = false
            surfaceHolder.lockCanvas()
        }?.let { canvas ->
            val surface = surfaceHolder.surface
            try {
                canvas.save()
                canvasFactory.create(canvas) { painter.draw(it) }
                canvas.restore()
                Thread.sleep(1000L) // RACE CONDITION TEST, TODO remove this
            } finally {
                synchronizer.ifAllowed {
                    if (changedSurface || surfaceHolder.surface !== surface) {
                        Log.d("Ruud", "Deliberately not unlocking")
                    } else {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    }
                }
            }
        }
    }

    @Volatile
    var changedSurface: Boolean = false
}
