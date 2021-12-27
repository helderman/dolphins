package com.dojadragon.dolphins

import android.os.Handler
import android.os.Looper

// Creates an object that drives the animation

class DolphinsAnimationFactory {
    fun create(step: () -> Unit): IDolphinsAnimation {
        return DolphinsAnimationByDelay(step)
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
