package be.appwise.measurements.units

import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear

class UnitDuration(symbol: String, converter: UnitConverter) : Dimension(symbol, converter) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

    private object Symbol {
        const val Seconds = "s"
        const val Minutes = "m"
        const val Hours = "h"
    }

    private object Coefficient {
        const val Seconds = 1.0
        const val Minutes = 60.0
        const val Hours = 3600.0
    }

    companion object {
        val seconds: UnitDuration = UnitDuration(Symbol.Seconds, Coefficient.Seconds)
        val minutes: UnitDuration = UnitDuration(Symbol.Minutes, Coefficient.Minutes)
        val hours: UnitDuration = UnitDuration(Symbol.Hours, Coefficient.Hours)
    }

    override fun baseUnit(): UnitDuration {
        return seconds
    }
}
