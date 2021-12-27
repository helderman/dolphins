package com.dojadragon.dolphins

import android.content.Context
import android.os.SystemClock

// Composition root (pure DI)

object DolphinsCompositionRoot {
    fun createFrame(context: Context) =
        DolphinsFrame(
            DolphinsPainter(
                DolphinsIndividualFactory(),
                DolphinsVertexFactory(),
                DolphinsVertexFactory(),
                SystemClock::elapsedRealtime),
            DolphinsCanvasFactory(context)
        )
}
