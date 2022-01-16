package be.appwise.measurements.units

import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear

class UnitElectricCharge(symbol: String, converter: UnitConverter) : Dimension(symbol, converter) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

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

    companion object {
        val coulombs = UnitElectricCharge(Symbol.coulombs, Coefficient.coulombs)
        val megaampereHours = UnitElectricCharge(Symbol.megaampereHours, Coefficient.megaampereHours)
        val kiloampereHours = UnitElectricCharge(Symbol.kiloampereHours, Coefficient.kiloampereHours)
        val ampereHours = UnitElectricCharge(Symbol.ampereHours, Coefficient.ampereHours)
        val milliampereHours = UnitElectricCharge(Symbol.milliampereHours, Coefficient.milliampereHours)
        val microampereHours = UnitElectricCharge(Symbol.microampereHours, Coefficient.microampereHours)
    }

    override fun baseUnit() = coulombs
}