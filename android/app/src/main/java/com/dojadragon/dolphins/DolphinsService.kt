package com.dojadragon.dolphins

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder

class DolphinsService : WallpaperService() {
    override fun onCreateEngine(): Engine {
        return DolphinsEngine(DolphinsDrawer(this, SystemClock::elapsedRealtime))
    }

    inner class DolphinsEngine(private val drawer: DolphinsDrawer) : Engine() {
        private val handler = Handler(Looper.getMainLooper())
        private val runnable = Runnable { step() }

        private var willDraw = false

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            willDraw = visible
            step()
        }

        override fun onSurfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)
            drawer.setDimensions(width, height)
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)
            willDraw = false
            step()
        }

        override fun onDestroy() {
            super.onDestroy()
            willDraw = false
            step()
        }

        private fun step() {
            if (willDraw) {
                drawer.drawFrame(surfaceHolder)
            }
            handler.removeCallbacks(runnable)
            if (willDraw) {
                handler.postDelayed(runnable, 20)
            }
        }
    }
}