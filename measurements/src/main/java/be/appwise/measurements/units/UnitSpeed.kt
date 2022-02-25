package be.appwise.measurements.units

import android.icu.util.MeasureUnit
import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear
import be.appwise.measurements.isAtLeastO

class UnitSpeed(symbol: String, converter: UnitConverter, measureUnit: MeasureUnit? = null) : Dimension(symbol, converter, measureUnit) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

    private constructor(symbol: String, coefficient: Double, measureUnit: MeasureUnit?) : this(symbol, UnitConverterLinear(coefficient), measureUnit)

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

    private object Unit {
        val metersPerSecond = if (isAtLeastO) MeasureUnit.METER_PER_SECOND else null
        val kilometersPerHour = if (isAtLeastO) MeasureUnit.KILOMETER_PER_HOUR else null
        val milesPerHour = if (isAtLeastO) MeasureUnit.MILE_PER_HOUR else null
        val knots = if (isAtLeastO) MeasureUnit.KNOT else null
    }

    companion object {
        val metersPerSecond = UnitSpeed(Symbol.metersPerSecond, Coefficient.metersPerSecond, Unit.metersPerSecond)
        val kilometersPerHour = UnitSpeed(Symbol.kilometersPerHour, Coefficient.kilometersPerHour, Unit.kilometersPerHour)
        val milesPerHour = UnitSpeed(Symbol.milesPerHour, Coefficient.milesPerHour, Unit.milesPerHour)
        val knots = UnitSpeed(Symbol.knots, Coefficient.knots, Unit.knots)
    }

    override fun baseUnit() = metersPerSecond
}