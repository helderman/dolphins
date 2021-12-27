package com.dojadragon.dolphins

import android.graphics.Canvas
import android.view.SurfaceHolder

// Animation; can be used both in a Live Wallpaper and in a regular SurfaceView
class DolphinsDrawer(private val painter: DolphinsPainter, private val canvasFactory: DolphinsCanvasFactory) {
    fun drawFrame(surfaceHolder : SurfaceHolder) {
        var canvas : Canvas? = null
        try {
            canvas = surfaceHolder.lockCanvas()
            if (canvas != null) {
                canvas.save()
                painter.draw(canvasFactory.create(canvas))
            }
        } finally {
            if (canvas != null) {
                canvas.restore()
                surfaceHolder.unlockCanvasAndPost(canvas)
            }
        }
    }
}
