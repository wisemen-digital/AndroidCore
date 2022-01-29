package be.appwise.measurements.units

import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterReciprocal

class UnitFuelEfficiency(symbol: String, converter: UnitConverter) : Dimension(symbol, converter) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterReciprocal(coefficient))

    private object Symbol {
        const val litersPer100Kilometers = "L/100km"
        const val milesPerImperialGallon = "mpg"
        const val milesPerGallon = "mpg"
    }

    private object Coefficient {
        const val litersPer100Kilometers = 1.0
        const val milesPerImperialGallon = 282.481
        const val milesPerGallon = 235.215
    }

    companion object {
        val litersPer100Kilometers = UnitFuelEfficiency(Symbol.litersPer100Kilometers, Coefficient.litersPer100Kilometers)
        val milesPerImperialGallon = UnitFuelEfficiency(Symbol.milesPerImperialGallon, Coefficient.milesPerImperialGallon)
        val milesPerGallon = UnitFuelEfficiency(Symbol.milesPerGallon, Coefficient.milesPerGallon)
    }

    override fun baseUnit() = litersPer100Kilometers
}