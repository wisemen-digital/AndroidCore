package be.appwise.measurements.units

import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear

class UnitElectricPotentialDifference(symbol: String, converter: UnitConverter) : Dimension(symbol, converter) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

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

    companion object {
        val megavolts = UnitElectricPotentialDifference(Symbol.megavolts, Coefficient.megavolts)
        val kilovolts = UnitElectricPotentialDifference(Symbol.kilovolts, Coefficient.kilovolts)
        val volts = UnitElectricPotentialDifference(Symbol.volts, Coefficient.volts)
        val millivolts = UnitElectricPotentialDifference(Symbol.millivolts, Coefficient.millivolts)
        val microvolts = UnitElectricPotentialDifference(Symbol.microvolts, Coefficient.microvolts)
    }

    override fun baseUnit() = volts
}