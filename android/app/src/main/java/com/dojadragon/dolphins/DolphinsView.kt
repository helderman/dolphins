package com.dojadragon.dolphins

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

// Unused, but makes a nice example
class DolphinsView(context: Context, attrs: AttributeSet) : SurfaceView(context, attrs), SurfaceHolder.Callback {
    private val drawer = DolphinsDrawer(context, SystemClock::elapsedRealtime)

    init {
        holder.addCallback(this)
        isFocusable = true
    }

    private val runnable = Runnable { step() }

    private var willDraw = false

    override fun surfaceCreated(p0: SurfaceHolder) {
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        drawer.setDimensions(width, height)
        willDraw = true
        step()
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        willDraw = false
        step()
    }

    private fun step() {
        if (willDraw) {
            drawer.drawFrame(holder)
        }
        handler.removeCallbacks(runnable)
        if (willDraw) {
            handler.postDelayed(runnable, 20)
        }
    }
}
