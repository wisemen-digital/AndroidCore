package be.appwise.measurements.units

import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear

class UnitElectricCurrent(symbol: String, converter: UnitConverter) : Dimension(symbol, converter) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

    private object Symbol {
        const val megaamperes = "MA"
        const val kiloamperes = "kA"
        const val amperes = "A"
        const val milliamperes = "mA"
        const val microamperes = "µA"
    }

    private object Coefficient {
        const val megaamperes = 1e6
        const val kiloamperes = 1e3
        const val amperes = 1.0
        const val milliamperes = 1e-3
        const val microamperes = 1e-6
    }

    companion object {
        val megaamperes = UnitElectricCurrent(Symbol.megaamperes, Coefficient.megaamperes)
        val kiloamperes = UnitElectricCurrent(Symbol.kiloamperes, Coefficient.kiloamperes)
        val amperes = UnitElectricCurrent(Symbol.amperes, Coefficient.amperes)
        val milliamperes = UnitElectricCurrent(Symbol.milliamperes, Coefficient.milliamperes)
        val microamperes = UnitElectricCurrent(Symbol.microamperes, Coefficient.microamperes)
    }

    override fun baseUnit() = amperes
}