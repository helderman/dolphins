package com.dojadragon.dolphins

// Creates an object that describes the current state of a single dolphin.
// This implementation is extremely economic:
// it overwrites and returns the same (mutable) instance of DolphinsIndividual over and over again.
// Generally this is bad practice, but it helps make the app more efficient (by avoiding GC).

class DolphinsIndividualFactory {
    fun create(
        camX: Float,
        camY: Float,
        dolphinX: Float,
        dolphinY: Float,
        dolphinZ: Float,
        wave: Double
    ) : IDolphinsIndividual = individual.apply {
        this.camX = camX
        this.camY = camY
        this.dolphinX = dolphinX
        this.dolphinY = dolphinY
        this.dolphinZ = dolphinZ
        this.wave = wave
    }

    private val individual = DolphinsIndividual()

    private class DolphinsIndividual : IDolphinsIndividual {
        override var camX = 0f
        override var camY = 0f
        override var dolphinX = 0f
        override var dolphinY = 0f
        override var dolphinZ = 0f
        override var wave = 0.0
    }
}
