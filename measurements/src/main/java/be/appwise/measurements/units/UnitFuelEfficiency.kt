package be.appwise.measurements.units

import android.icu.util.MeasureUnit
import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterReciprocal
import be.appwise.measurements.isAtLeastO
import be.appwise.measurements.isAtLeastP

class UnitFuelEfficiency(symbol: String, converter: UnitConverter, measureUnit: MeasureUnit? = null) : Dimension(symbol, converter, measureUnit) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterReciprocal(coefficient))

    private constructor(symbol: String, coefficient: Double, measureUnit: MeasureUnit?) : this(symbol, UnitConverterReciprocal(coefficient), measureUnit)

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

    private object Unit {
        val litersPer100Kilometers = if (isAtLeastO) MeasureUnit.LITER_PER_100KILOMETERS else null
        val milesPerImperialGallon = if (isAtLeastP) MeasureUnit.MILE_PER_GALLON_IMPERIAL else null
        val milesPerGallon = if (isAtLeastO) MeasureUnit.MILE_PER_GALLON else null
    }

    companion object {
        val litersPer100Kilometers = UnitFuelEfficiency(Symbol.litersPer100Kilometers, Coefficient.litersPer100Kilometers, Unit.litersPer100Kilometers)
        val milesPerImperialGallon = UnitFuelEfficiency(Symbol.milesPerImperialGallon, Coefficient.milesPerImperialGallon, Unit.milesPerImperialGallon)
        val milesPerGallon = UnitFuelEfficiency(Symbol.milesPerGallon, Coefficient.milesPerGallon, Unit.milesPerGallon)
    }

    override fun baseUnit() = litersPer100Kilometers
}