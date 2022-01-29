package be.appwise.measurements.units

import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear

class UnitAngle(symbol: String, converter: UnitConverter) : Dimension(symbol, converter) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

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

    companion object {
        val degrees = UnitAngle(Symbol.degrees, Coefficient.degrees)
        val arcMinutes = UnitAngle(Symbol.arcMinutes, Coefficient.arcMinutes)
        val arcSeconds = UnitAngle(Symbol.arcSeconds, Coefficient.arcSeconds)
        val radians = UnitAngle(Symbol.radians, Coefficient.radians)
        val gradians = UnitAngle(Symbol.gradians, Coefficient.gradians)
        val revolutions = UnitAngle(Symbol.revolutions, Coefficient.revolutions)
    }

    override fun baseUnit() = degrees
}