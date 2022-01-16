package be.appwise.measurements.units

import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear

class UnitElectricResistance(symbol: String, converter: UnitConverter) : Dimension(symbol, converter) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

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

    companion object {
        val megaohms = UnitElectricResistance(Symbol.megaohms, Coefficient.megaohms)
        val kiloohms = UnitElectricResistance(Symbol.kiloohms, Coefficient.kiloohms)
        val ohms = UnitElectricResistance(Symbol.ohms, Coefficient.ohms)
        val milliohms = UnitElectricResistance(Symbol.milliohms, Coefficient.milliohms)
        val microohms = UnitElectricResistance(Symbol.microohms, Coefficient.microohms)
    }

    override fun baseUnit() = ohms
}