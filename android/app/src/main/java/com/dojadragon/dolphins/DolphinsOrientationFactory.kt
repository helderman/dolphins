package com.dojadragon.dolphins

// Creates an object that describes the current state of a single dolphin.
// This implementation is extremely economic:
// it overwrites and returns the same (mutable) instance of DolphinsOrientation over and over again.
// Generally this is bad practice, but it helps make the app more efficient (by avoiding GC).

class DolphinsOrientationFactory {
    fun create(
        camX: Float,
        camY: Float,
        dolphinX: Float,
        dolphinY: Float,
        dolphinZ: Float,
        wave: Double
    ) : IDolphinsOrientation = orientation.apply {
        this.camX = camX
        this.camY = camY
        this.dolphinX = dolphinX
        this.dolphinY = dolphinY
        this.dolphinZ = dolphinZ
        this.wave = wave
    }

    private val orientation = DolphinsOrientation()

    private class DolphinsOrientation : IDolphinsOrientation {
        override var camX = 0f
        override var camY = 0f
        override var dolphinX = 0f
        override var dolphinY = 0f
        override var dolphinZ = 0f
        override var wave = 0.0
    }
}
