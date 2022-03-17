package be.appwise.measurements.units

import android.icu.util.MeasureUnit
import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear
import be.appwise.measurements.isAtLeastO

class UnitElectricCurrent(symbol: String, converter: UnitConverter, measureUnit: MeasureUnit? = null) : Dimension(symbol, converter, measureUnit) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

    private constructor(symbol: String, coefficient: Double, measureUnit: MeasureUnit?) : this(symbol, UnitConverterLinear(coefficient), measureUnit)

    private object Symbol {
        const val megaamperes = "MA"
        const val kiloamperes = "kA"
        const val amperes = "A"
        const val deciamperes = "dA"
        const val milliamperes = "mA"
        const val microamperes = "µA"
    }

    private object Coefficient {
        const val megaamperes = 1e6
        const val kiloamperes = 1e3
        const val amperes = 1.0
        const val deciamperes = 1e-2
        const val milliamperes = 1e-3
        const val microamperes = 1e-6
    }

    private object Unit {
        val megaamperes = null
        val kiloamperes = null
        val amperes = if (isAtLeastO) MeasureUnit.AMPERE else null
        val deciamperes = null
        val milliamperes = if (isAtLeastO) MeasureUnit.MILLIAMPERE else null
        val microamperes = null
    }

    companion object {
        val megaamperes = UnitElectricCurrent(Symbol.megaamperes, Coefficient.megaamperes, Unit.megaamperes)
        val kiloamperes = UnitElectricCurrent(Symbol.kiloamperes, Coefficient.kiloamperes, Unit.kiloamperes)
        val amperes = UnitElectricCurrent(Symbol.amperes, Coefficient.amperes, Unit.amperes)
        val deciamperes = UnitElectricCurrent(Symbol.deciamperes, Coefficient.deciamperes, Unit.deciamperes)
        val milliamperes = UnitElectricCurrent(Symbol.milliamperes, Coefficient.milliamperes, Unit.milliamperes)
        val microamperes = UnitElectricCurrent(Symbol.microamperes, Coefficient.microamperes, Unit.microamperes)
    }

    override fun baseUnit() = amperes
}