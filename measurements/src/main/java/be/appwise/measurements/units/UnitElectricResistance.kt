package be.appwise.measurements.units

import android.icu.util.MeasureUnit
import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear
import be.appwise.measurements.isAtLeastO

class UnitElectricResistance(symbol: String, converter: UnitConverter, measureUnit: MeasureUnit? = null) : Dimension(symbol, converter, measureUnit) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

    private constructor(symbol: String, coefficient: Double, measureUnit: MeasureUnit?) : this(symbol, UnitConverterLinear(coefficient), measureUnit)

    private object Symbol {
        const val megaohms = "MΩ"
        const val kiloohms = "kΩ"
        const val ohms = "Ω"
        const val milliohms = "mΩ"
        const val microohms = "µΩ"
    }

    private object Coefficient {
        const val megaohms = 1e6
        const val kiloohms = 1e3
        const val ohms = 1.0
        const val milliohms = 1e-3
        const val microohms = 1e-6
    }

    private object Unit {
        val megaohms = null
        val kiloohms = null
        val ohms = if (isAtLeastO) MeasureUnit.OHM else null
        val milliohms = null
        val microohms = null
    }

    companion object {
        val megaohms = UnitElectricResistance(Symbol.megaohms, Coefficient.megaohms, Unit.megaohms)
        val kiloohms = UnitElectricResistance(Symbol.kiloohms, Coefficient.kiloohms, Unit.kiloohms)
        val ohms = UnitElectricResistance(Symbol.ohms, Coefficient.ohms, Unit.ohms)
        val milliohms = UnitElectricResistance(Symbol.milliohms, Coefficient.milliohms, Unit.milliohms)
        val microohms = UnitElectricResistance(Symbol.microohms, Coefficient.microohms, Unit.microohms)
    }

    override fun baseUnit() = ohms
}