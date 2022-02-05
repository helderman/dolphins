package com.dojadragon.dolphins

import android.content.Context
import android.os.SystemClock

// Composition root (pure DI)

object DolphinsCompositionRoot {
    fun createAnimationFactory() =
        DolphinsAnimationFactory()

    fun createSynchronizerFactory() =
        DolphinsSynchronizerFactory()

    fun createFrame(context: Context) =
        DolphinsFrame(
            DolphinsPainter(
                DolphinsOrientationFactory(),
                DolphinsVertexFactory(),
                DolphinsVertexFactory(),
                SystemClock::elapsedRealtime
            ),
            DolphinsCanvasFactory(context)
        )
}
