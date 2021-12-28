package com.dojadragon.dolphins

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.Choreographer
import androidx.annotation.RequiresApi

// Creates an object that drives the animation

class DolphinsAnimationFactory {
    fun create(step: () -> Unit): IDolphinsAnimation {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            DolphinsAnimationByVSync(step)
        } else {
            DolphinsAnimationByDelay(step)
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private class DolphinsAnimationByVSync(step: () -> Unit) : IDolphinsAnimation {
        private val choreographer = Choreographer.getInstance()
        private val callback = Choreographer.FrameCallback { step() }

        override fun animate(visible: Boolean) {
            choreographer.removeFrameCallback(callback)
            if (visible) {
                choreographer.postFrameCallback(callback)
            }
        }
    }

    private class DolphinsAnimationByDelay(step: () -> Unit) : IDolphinsAnimation {
        private val handler = Handler(Looper.getMainLooper())
        private val runnable = Runnable { step() }

        override fun animate(visible: Boolean) {
            handler.removeCallbacks(runnable)
            if (visible) {
                handler.postDelayed(runnable, DELAY_MS)
            }
        }

        companion object {
            private const val DELAY_MS = 20L
        }
    }
}
