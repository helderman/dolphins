package com.dojadragon.dolphins

import android.view.Choreographer

// Creates an object that drives the animation

class DolphinsAnimationFactory {
    fun create(step: () -> Unit): IDolphinsAnimation {
        return DolphinsAnimationByVSync(step)
    }

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
}
