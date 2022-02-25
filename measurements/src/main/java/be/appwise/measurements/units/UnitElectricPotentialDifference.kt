package be.appwise.measurements.units

import android.icu.util.MeasureUnit
import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear
import be.appwise.measurements.isAtLeastO

class UnitElectricPotentialDifference(symbol: String, converter: UnitConverter, measureUnit: MeasureUnit? = null) : Dimension(symbol, converter, measureUnit) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

    private constructor(symbol: String, coefficient: Double, measureUnit: MeasureUnit?) : this(symbol, UnitConverterLinear(coefficient), measureUnit)

    private object Symbol {
        const val megavolts = "MV"
        const val kilovolts = "kV"
        const val volts = "V"
        const val millivolts = "mV"
        const val microvolts = "µV"
    }

    private object Coefficient {
        const val megavolts = 1e6
        const val kilovolts = 1e3
        const val volts = 1.0
        const val millivolts = 1e-3
        const val microvolts = 1e-6
    }

    private object Unit {
        val megavolts = null
        val kilovolts = null
        val volts = if (isAtLeastO) MeasureUnit.VOLT else null
        val millivolts = null
        val microvolts = null
    }

    companion object {
        val megavolts = UnitElectricPotentialDifference(Symbol.megavolts, Coefficient.megavolts, Unit.megavolts)
        val kilovolts = UnitElectricPotentialDifference(Symbol.kilovolts, Coefficient.kilovolts, Unit.kilovolts)
        val volts = UnitElectricPotentialDifference(Symbol.volts, Coefficient.volts, Unit.volts)
        val millivolts = UnitElectricPotentialDifference(Symbol.millivolts, Coefficient.millivolts, Unit.millivolts)
        val microvolts = UnitElectricPotentialDifference(Symbol.microvolts, Coefficient.microvolts, Unit.microvolts)
    }

    override fun baseUnit() = volts
}