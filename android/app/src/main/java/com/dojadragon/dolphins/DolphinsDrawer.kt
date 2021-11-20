package com.dojadragon.dolphins

import android.graphics.Canvas
import android.view.SurfaceHolder

// Animation; can be used both in a Live Wallpaper and in a regular SurfaceView
class DolphinsDrawer(private val dolphinsFrame: DolphinsFrame) {
    fun drawFrame(surfaceHolder : SurfaceHolder) {
        var canvas : Canvas? = null
        try {
            canvas = surfaceHolder.lockCanvas()
            if (canvas != null) {
                dolphinsFrame.draw(canvas)
            }
        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas)
            }
        }
    }
}
