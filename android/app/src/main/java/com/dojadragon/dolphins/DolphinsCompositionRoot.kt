package com.dojadragon.dolphins

import android.content.Context
import android.os.SystemClock

// Composition root (pure DI)
object DolphinsCompositionRoot {
    fun createDrawer(context: Context) =
        DolphinsDrawer(
            DolphinsFrame(SystemClock::elapsedRealtime),
            DolphinsCanvasFactory(context)
        )
}
