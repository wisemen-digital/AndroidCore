package be.appwise.measurements.units

import android.icu.util.MeasureUnit
import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear

class UnitElectricCharge(symbol: String, converter: UnitConverter, measureUnit: MeasureUnit? = null) : Dimension(symbol, converter, measureUnit) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

    private constructor(symbol: String, coefficient: Double, measureUnit: MeasureUnit?) : this(symbol, UnitConverterLinear(coefficient), measureUnit)

    private object Symbol {
        const val coulombs = "C"
        const val megaampereHours = "MAh"
        const val kiloampereHours = "kAh"
        const val ampereHours = "Ah"
        const val milliampereHours = "mAh"
        const val microampereHours = "µAh"
    }

    private object Coefficient {
        const val coulombs = 1.0
        const val megaampereHours = 3.6e9
        const val kiloampereHours = 3600000.0
        const val ampereHours = 3600.0
        const val milliampereHours = 3.6
        const val microampereHours = 0.0036
    }

    private object Unit {
        val coulombs = null
        val megaampereHours = null
        val kiloampereHours = null
        val ampereHours = null
        val milliampereHours = null
        val microampereHours = null
    }

    companion object {
        val coulombs = UnitElectricCharge(Symbol.coulombs, Coefficient.coulombs, Unit.coulombs)
        val megaampereHours = UnitElectricCharge(Symbol.megaampereHours, Coefficient.megaampereHours, Unit.megaampereHours)
        val kiloampereHours = UnitElectricCharge(Symbol.kiloampereHours, Coefficient.kiloampereHours, Unit.kiloampereHours)
        val ampereHours = UnitElectricCharge(Symbol.ampereHours, Coefficient.ampereHours, Unit.ampereHours)
        val milliampereHours = UnitElectricCharge(Symbol.milliampereHours, Coefficient.milliampereHours, Unit.milliampereHours)
        val microampereHours = UnitElectricCharge(Symbol.microampereHours, Coefficient.microampereHours, Unit.microampereHours)
    }

    override fun baseUnit() = coulombs
}