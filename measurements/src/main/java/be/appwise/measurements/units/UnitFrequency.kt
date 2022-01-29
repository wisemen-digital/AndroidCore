package be.appwise.measurements.units

import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear

class UnitFrequency(symbol: String, converter: UnitConverter) : Dimension(symbol, converter) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

    private object Symbol {
        const val terahertz = "THz"
        const val gigahertz = "GHz"
        const val megahertz = "MHz"
        const val kilohertz = "kHz"
        const val hertz = "Hz"
        const val millihertz = "mHz"
        const val microhertz = "µHz"
        const val nanohertz = "nHz"
    }

    private object Coefficient {
        const val terahertz = 1e12
        const val gigahertz = 1e9
        const val megahertz = 1e6
        const val kilohertz = 1e3
        const val hertz = 1.0
        const val millihertz = 1e-3
        const val microhertz = 1e-6
        const val nanohertz = 1e-9
    }

    companion object {
        val terahertz = UnitFrequency(Symbol.terahertz, Coefficient.terahertz)
        val gigahertz = UnitFrequency(Symbol.gigahertz, Coefficient.gigahertz)
        val megahertz = UnitFrequency(Symbol.megahertz, Coefficient.megahertz)
        val kilohertz = UnitFrequency(Symbol.kilohertz, Coefficient.kilohertz)
        val hertz = UnitFrequency(Symbol.hertz, Coefficient.hertz)
        val millihertz = UnitFrequency(Symbol.millihertz, Coefficient.millihertz)
        val microhertz = UnitFrequency(Symbol.microhertz, Coefficient.microhertz)
        val nanohertz = UnitFrequency(Symbol.nanohertz, Coefficient.nanohertz)
    }

    override fun baseUnit() = hertz
}