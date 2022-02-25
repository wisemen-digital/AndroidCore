package be.appwise.measurements.units

import android.icu.util.MeasureUnit
import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear
import be.appwise.measurements.isAtLeastN
import be.appwise.measurements.isAtLeastO

class UnitAngle(symbol: String, converter: UnitConverter, measureUnit: MeasureUnit? = null) : Dimension(symbol, converter, measureUnit) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

    private constructor(symbol: String, coefficient: Double, measureUnit: MeasureUnit?) : this(symbol, UnitConverterLinear(coefficient), measureUnit)

    private object Symbol {
        const val degrees = "°"
        const val arcMinutes = "ʹ"
        const val arcSeconds = "ʹʹ"
        const val radians = "rad"
        const val gradians = "grad"
        const val revolutions = "rev"
    }

    private object Coefficient {
        const val degrees = 1.0
        const val arcMinutes = 1.0 / 60.0
        const val arcSeconds = 1.0 / 3600.0
        const val radians = 180.0 / Math.PI
        const val gradians = 0.9
        const val revolutions = 360.0
    }

    private object Unit {
        val degrees = if (isAtLeastN) MeasureUnit.DEGREE else null
        val arcMinutes = if (isAtLeastN) MeasureUnit.ARC_MINUTE else null
        val arcSeconds = if (isAtLeastN) MeasureUnit.ARC_SECOND else null
        val radians = if (isAtLeastN) MeasureUnit.RADIAN else null
        val gradians = null
        val revolutions = if (isAtLeastO) MeasureUnit.REVOLUTION_ANGLE else null
    }

    companion object {
        val degrees = UnitAngle(Symbol.degrees, Coefficient.degrees, Unit.degrees)
        val arcMinutes = UnitAngle(Symbol.arcMinutes, Coefficient.arcMinutes, Unit.arcMinutes)
        val arcSeconds = UnitAngle(Symbol.arcSeconds, Coefficient.arcSeconds, Unit.arcSeconds)
        val radians = UnitAngle(Symbol.radians, Coefficient.radians, Unit.radians)
        val gradians = UnitAngle(Symbol.gradians, Coefficient.gradians, Unit.gradians)
        val revolutions = UnitAngle(Symbol.revolutions, Coefficient.revolutions, Unit.revolutions)
    }

    override fun baseUnit() = degrees
}