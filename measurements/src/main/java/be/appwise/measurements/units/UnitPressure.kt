package be.appwise.measurements.units

import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear

class UnitPressure(symbol: String, converter: UnitConverter) : Dimension(symbol, converter) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

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

    companion object {
        val newtonsPerMetersSquared = UnitPressure(Symbol.newtonsPerMetersSquared, Coefficient.newtonsPerMetersSquared)
        val pascal = UnitPressure(Symbol.pascal, Coefficient.pascal)
        val gigapascals = UnitPressure(Symbol.gigapascals, Coefficient.gigapascals)
        val megapascals = UnitPressure(Symbol.megapascals, Coefficient.megapascals)
        val kilopascals = UnitPressure(Symbol.kilopascals, Coefficient.kilopascals)
        val hectopascals = UnitPressure(Symbol.hectopascals, Coefficient.hectopascals)
        val inchesOfMercury = UnitPressure(Symbol.inchesOfMercury, Coefficient.inchesOfMercury)
        val bars = UnitPressure(Symbol.bars, Coefficient.bars)
        val millibars = UnitPressure(Symbol.millibars, Coefficient.millibars)
        val millimetersOfMercury = UnitPressure(Symbol.millimetersOfMercury, Coefficient.millimetersOfMercury)
        val poundsForcePerSquareInch = UnitPressure(Symbol.poundsForcePerSquareInch, Coefficient.poundsForcePerSquareInch)
    }

    override fun baseUnit() = pascal
}