package com.dojadragon.dolphins

import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder

// Live Wallpaper

class DolphinsWallpaperService : WallpaperService() {
    override fun onCreateEngine(): Engine {
        return DolphinsEngine(
            DolphinsCompositionRoot.createAnimationFactory(),
            DolphinsCompositionRoot.createFrame(this)
        )
    }

    private inner class DolphinsEngine(
        animationFactory: DolphinsAnimationFactory,
        private val frame: DolphinsFrame
    ) : Engine() {
        private val animation = animationFactory.create { step(isVisible) }

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

        private fun step(visible: Boolean) {
            if (visible) {
                frame.drawFrame(surfaceHolder)
            }
            animation.animate(visible)
        }
    }
}
