package be.appwise.measurements.units

import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear

class UnitTemperature(symbol: String, converter: UnitConverter) : Dimension(symbol, converter) {

    private constructor(symbol: String, coefficient: Double, constant: Double) : this(symbol, UnitConverterLinear(coefficient, constant))

    private object Symbol {
        const val kelvin = "K"
        const val celsius = "°C"
        const val fahrenheit = "°F"
    }

    private object Coefficient {
        const val kelvin = 1.0
        const val celsius = 1.0
        const val fahrenheit = 0.55555555555556
    }

    private object Constants {
        const val kelvin = 0.0
        const val celsius = 273.15
        const val fahrenheit = 255.37222222222427
    }

    companion object {
        val kelvin = UnitTemperature(Symbol.kelvin, Coefficient.kelvin, Constants.kelvin)
        val celsius = UnitTemperature(Symbol.celsius, Coefficient.celsius, Constants.celsius)
        val fahrenheit = UnitTemperature(Symbol.fahrenheit, Coefficient.fahrenheit, Constants.fahrenheit)
    }

    override fun baseUnit() = kelvin
}