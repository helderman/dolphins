package com.dojadragon.dolphins

import android.os.Handler
import android.os.Looper
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder

// Live Wallpaper

class DolphinsWallpaperService : WallpaperService() {
    override fun onCreateEngine(): Engine {
        return DolphinsEngine(DolphinsCompositionRoot.createFrame(this))
    }

    private inner class DolphinsEngine(private val frame: DolphinsFrame) : Engine() {
        private val handler = Handler(Looper.getMainLooper())
        private val runnable = Runnable { step(isVisible) }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            step(visible)
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)
            step(false)
        }

        override fun onDestroy() {
            super.onDestroy()
            step(false)
        }

        private fun step(willDraw: Boolean) {
            if (willDraw) {
                frame.drawFrame(surfaceHolder)
            }
            handler.removeCallbacks(runnable)
            if (willDraw) {
                handler.postDelayed(runnable, 20)
            }
        }
    }
}
