package com.dojadragon.dolphins

import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder

// Live Wallpaper

class DolphinsWallpaperService : WallpaperService() {
    override fun onCreateEngine(): Engine {
        return DolphinsEngine(
            DolphinsCompositionRoot.createAnimationFactory(),
            DolphinsCompositionRoot.createSynchronizerFactory(),
            DolphinsCompositionRoot.createFrame(this)
        )
    }

    private inner class DolphinsEngine(
        animationFactory: DolphinsAnimationFactory,
        synchronizerFactory: DolphinsSynchronizerFactory,
        private val frame: DolphinsFrame
    ) : Engine() {
        private val animation = animationFactory.create { step() }
        private val synchronizer = synchronizerFactory.create()

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            animation.animate(visible)
        }

        override fun onSurfaceChanged(
            holder: SurfaceHolder?,
            format: Int,
            width: Int,
            height: Int
        ) {
            frame.changedSurface = true
            super.onSurfaceChanged(holder, format, width, height)
        }

        override fun onSurfaceCreated(holder: SurfaceHolder?) {
            super.onSurfaceCreated(holder)
            frame.changedSurface = true
            synchronizer.allow()
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            synchronizer.disallow()
            super.onSurfaceDestroyed(holder)
        }

        private fun step() {
            if (isVisible) {
                frame.drawFrame(surfaceHolder, synchronizer)
            }
            animation.animate(isVisible)
        }
    }
}
