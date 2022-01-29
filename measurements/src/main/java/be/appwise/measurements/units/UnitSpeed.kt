package be.appwise.measurements.units

import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear

class UnitSpeed(symbol: String, converter: UnitConverter) : Dimension(symbol, converter) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

    private object Symbol {
        const val metersPerSecond = "m/s"
        const val kilometersPerHour = "km/h"
        const val milesPerHour = "mph"
        const val knots = "kn"
    }

    private object Coefficient {
        const val metersPerSecond = 1.0
        const val kilometersPerHour = 0.277778
        const val milesPerHour = 0.44704
        const val knots = 0.514444
    }

    companion object {
        val metersPerSecond = UnitSpeed(Symbol.metersPerSecond, Coefficient.metersPerSecond)
        val kilometersPerHour = UnitSpeed(Symbol.kilometersPerHour, Coefficient.kilometersPerHour)
        val milesPerHour = UnitSpeed(Symbol.milesPerHour, Coefficient.milesPerHour)
        val knots = UnitSpeed(Symbol.knots, Coefficient.knots)
    }

    override fun baseUnit() = metersPerSecond
}