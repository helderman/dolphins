package com.dojadragon.dolphins

class DolphinsIndividualFactory {
    fun create(
        camX: Float,
        camY: Float,
        dolphinX: Float,
        dolphinY: Float,
        dolphinZ: Float,
        wave: Double
    ) : IDolphinsIndividual =
        DolphinsIndividual(camX, camY, dolphinX, dolphinY, dolphinZ, wave)

    private inner class DolphinsIndividual(
        override val camX: Float,
        override val camY: Float,
        override val dolphinX: Float,
        override val dolphinY: Float,
        override val dolphinZ: Float,
        override val wave: Double) : IDolphinsIndividual
}
