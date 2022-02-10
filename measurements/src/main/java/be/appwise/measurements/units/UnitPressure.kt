package be.appwise.measurements.units

import android.icu.util.MeasureUnit
import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear
import be.appwise.measurements.isAtLeastO

class UnitPressure(symbol: String, converter: UnitConverter, measureUnit: MeasureUnit? = null) : Dimension(symbol, converter, measureUnit) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

    private constructor(symbol: String, coefficient: Double, measureUnit: MeasureUnit?) : this(symbol, UnitConverterLinear(coefficient), measureUnit)

    private object Symbol {
        const val newtonsPerMetersSquared = "N/m²"
        const val pascal = "Pa"
        const val gigapascals = "GPa"
        const val megapascals = "MPa"
        const val kilopascals = "kPa"
        const val hectopascals = "hPa"
        const val inchesOfMercury = "inHg"
        const val bars = "bar"
        const val millibars = "mbar"
        const val millimetersOfMercury = "mmHg"
        const val poundsForcePerSquareInch = "psi"
    }

    private object Coefficient {
        const val newtonsPerMetersSquared = 1.0
        const val pascal = 1.0
        const val gigapascals = 1e9
        const val megapascals = 1e6
        const val kilopascals = 1e3
        const val hectopascals = 1e2
        const val inchesOfMercury = 3386.39
        const val bars = 1e5
        const val millibars = 1e2
        const val millimetersOfMercury = 133.322
        const val poundsForcePerSquareInch = 6894.76
    }

    private object Unit {
        val newtonsPerMetersSquared = null
        val pascal = null
        val gigapascals = null
        val megapascals = null
        val kilopascals = null
        val hectopascals = if (isAtLeastO) MeasureUnit.HECTOPASCAL else null
        val inchesOfMercury = null
        val bars = null
        val millibars = if (isAtLeastO) MeasureUnit.MILLIBAR else null
        val millimetersOfMercury = if (isAtLeastO) MeasureUnit.MILLIMETER_OF_MERCURY else null
        val poundsForcePerSquareInch = if (isAtLeastO) MeasureUnit.POUND_PER_SQUARE_INCH else null
    }

    companion object {
        val newtonsPerMetersSquared = UnitPressure(Symbol.newtonsPerMetersSquared, Coefficient.newtonsPerMetersSquared, Unit.newtonsPerMetersSquared)
        val pascal = UnitPressure(Symbol.pascal, Coefficient.pascal, Unit.pascal)
        val gigapascals = UnitPressure(Symbol.gigapascals, Coefficient.gigapascals, Unit.gigapascals)
        val megapascals = UnitPressure(Symbol.megapascals, Coefficient.megapascals, Unit.megapascals)
        val kilopascals = UnitPressure(Symbol.kilopascals, Coefficient.kilopascals, Unit.kilopascals)
        val hectopascals = UnitPressure(Symbol.hectopascals, Coefficient.hectopascals, Unit.hectopascals)
        val inchesOfMercury = UnitPressure(Symbol.inchesOfMercury, Coefficient.inchesOfMercury, Unit.inchesOfMercury)
        val bars = UnitPressure(Symbol.bars, Coefficient.bars, Unit.bars)
        val millibars = UnitPressure(Symbol.millibars, Coefficient.millibars, Unit.millibars)
        val millimetersOfMercury = UnitPressure(Symbol.millimetersOfMercury, Coefficient.millimetersOfMercury, Unit.millimetersOfMercury)
        val poundsForcePerSquareInch = UnitPressure(Symbol.poundsForcePerSquareInch, Coefficient.poundsForcePerSquareInch, Unit.poundsForcePerSquareInch)
    }

    override fun baseUnit() = pascal
}