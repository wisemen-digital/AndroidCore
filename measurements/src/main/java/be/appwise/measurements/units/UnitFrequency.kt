package be.appwise.measurements.units

import android.icu.util.MeasureUnit
import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear
import be.appwise.measurements.isAtLeastO

class UnitFrequency(symbol: String, converter: UnitConverter, measureUnit: MeasureUnit? = null) : Dimension(symbol, converter, measureUnit) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

    private constructor(symbol: String, coefficient: Double, measureUnit: MeasureUnit?) : this(symbol, UnitConverterLinear(coefficient), measureUnit)

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

    private object Unit {
        val terahertz = null
        val gigahertz = if (isAtLeastO) MeasureUnit.GIGAHERTZ else null
        val megahertz = if (isAtLeastO) MeasureUnit.MEGAHERTZ else null
        val kilohertz = if (isAtLeastO) MeasureUnit.KILOHERTZ else null
        val hertz = if (isAtLeastO) MeasureUnit.HERTZ else null
        val millihertz = null
        val microhertz = null
        val nanohertz = null
    }

    companion object {
        val terahertz = UnitFrequency(Symbol.terahertz, Coefficient.terahertz, Unit.terahertz)
        val gigahertz = UnitFrequency(Symbol.gigahertz, Coefficient.gigahertz, Unit.gigahertz)
        val megahertz = UnitFrequency(Symbol.megahertz, Coefficient.megahertz, Unit.megahertz)
        val kilohertz = UnitFrequency(Symbol.kilohertz, Coefficient.kilohertz, Unit.kilohertz)
        val hertz = UnitFrequency(Symbol.hertz, Coefficient.hertz, Unit.hertz)
        val millihertz = UnitFrequency(Symbol.millihertz, Coefficient.millihertz, Unit.millihertz)
        val microhertz = UnitFrequency(Symbol.microhertz, Coefficient.microhertz, Unit.microhertz)
        val nanohertz = UnitFrequency(Symbol.nanohertz, Coefficient.nanohertz, Unit.nanohertz)
    }

    override fun baseUnit() = hertz
}