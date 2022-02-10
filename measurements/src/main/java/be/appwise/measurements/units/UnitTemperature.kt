package be.appwise.measurements.units

import android.icu.util.MeasureUnit
import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear
import be.appwise.measurements.isAtLeastN

class UnitTemperature(symbol: String, converter: UnitConverter, measureUnit: MeasureUnit? = null) : Dimension(symbol, converter, measureUnit) {

    private constructor(symbol: String, coefficient: Double, constant: Double) : this(symbol, UnitConverterLinear(coefficient, constant))

    private constructor(symbol: String, coefficient: Double, constant: Double, measureUnit: MeasureUnit?) : this(symbol, UnitConverterLinear(coefficient, constant), measureUnit)

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

    private object Unit {
        val kelvin = if (isAtLeastN) MeasureUnit.KELVIN else null
        val celsius = if (isAtLeastN) MeasureUnit.CELSIUS else null
        val fahrenheit = if (isAtLeastN) MeasureUnit.FAHRENHEIT else null
    }

    companion object {
        val kelvin = UnitTemperature(Symbol.kelvin, Coefficient.kelvin, Constants.kelvin, Unit.kelvin)
        val celsius = UnitTemperature(Symbol.celsius, Coefficient.celsius, Constants.celsius, Unit.celsius)
        val fahrenheit = UnitTemperature(Symbol.fahrenheit, Coefficient.fahrenheit, Constants.fahrenheit, Unit.fahrenheit)
    }

    override fun baseUnit() = kelvin
}