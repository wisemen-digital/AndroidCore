package be.appwise.measurements.units

import android.icu.util.MeasureUnit
import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear
import be.appwise.measurements.isAtLeastO

class UnitArea(symbol: String, converter: UnitConverter, measureUnit: MeasureUnit? = null) : Dimension(symbol, converter, measureUnit) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

    private constructor(symbol: String, coefficient: Double, measureUnit: MeasureUnit?) : this(symbol, UnitConverterLinear(coefficient), measureUnit)

    private object Symbol {
        const val squareMegameters = "Mm²"
        const val squareKilometers = "km²"
        const val squareMeters = "m²"
        const val squareCentimeters = "cm²"
        const val squareMillimeters = "mm²"
        const val squareMicrometers = "µm²"
        const val squareNanometers = "nm²"
        const val squareInches = "in²"
        const val squareFeet = "ft²"
        const val squareYards = "yd²"
        const val squareMiles = "mi²"
        const val acres = "ac"
        const val ares = "a"
        const val hectares = "ha"
    }

    private object Coefficient {
        const val squareMegameters = 1e12
        const val squareKilometers = 1e6
        const val squareMeters = 1.0
        const val squareCentimeters = 1e-4
        const val squareMillimeters = 1e-6
        const val squareMicrometers = 1e-12
        const val squareNanometers = 1e-18
        const val squareInches = 0.00064516
        const val squareFeet = 0.092903
        const val squareYards = 0.836127
        const val squareMiles = 2.59e+6
        const val acres = 4046.86
        const val ares = 100.0
        const val hectares = 10000.0
    }

    private object Unit {
        val squareMegameters = null
        val squareKilometers = if (isAtLeastO) MeasureUnit.SQUARE_KILOMETER else null
        val squareMeters = if (isAtLeastO) MeasureUnit.SQUARE_METER else null
        val squareCentimeters = if (isAtLeastO) MeasureUnit.SQUARE_CENTIMETER else null
        val squareMillimeters = null
        val squareMicrometers = null
        val squareNanometers = null
        val squareInches = if (isAtLeastO) MeasureUnit.SQUARE_INCH else null
        val squareFeet = if (isAtLeastO) MeasureUnit.SQUARE_FOOT else null
        val squareYards = if (isAtLeastO) MeasureUnit.SQUARE_YARD else null
        val squareMiles = if (isAtLeastO) MeasureUnit.SQUARE_MILE else null
        val acres = if (isAtLeastO) MeasureUnit.ACRE else null
        val ares = null
        val hectares = if (isAtLeastO) MeasureUnit.HECTARE else null
    }

    companion object {
        val squareMegameters = UnitArea(Symbol.squareMegameters, Coefficient.squareMegameters, Unit.squareMegameters)
        val squareKilometers = UnitArea(Symbol.squareKilometers, Coefficient.squareKilometers, Unit.squareKilometers)
        val squareMeters = UnitArea(Symbol.squareMeters, Coefficient.squareMeters, Unit.squareMeters)
        val squareCentimeters = UnitArea(Symbol.squareCentimeters, Coefficient.squareCentimeters, Unit.squareCentimeters)
        val squareMillimeters = UnitArea(Symbol.squareMillimeters, Coefficient.squareMillimeters, Unit.squareMillimeters)
        val squareMicrometers = UnitArea(Symbol.squareMicrometers, Coefficient.squareMicrometers, Unit.squareMicrometers)
        val squareNanometers = UnitArea(Symbol.squareNanometers, Coefficient.squareNanometers, Unit.squareNanometers)
        val squareInches = UnitArea(Symbol.squareInches, Coefficient.squareInches, Unit.squareInches)
        val squareFeet = UnitArea(Symbol.squareFeet, Coefficient.squareFeet, Unit.squareFeet)
        val squareYards = UnitArea(Symbol.squareYards, Coefficient.squareYards, Unit.squareYards)
        val squareMiles = UnitArea(Symbol.squareMiles, Coefficient.squareMiles, Unit.squareMiles)
        val acres = UnitArea(Symbol.acres, Coefficient.acres, Unit.acres)
        val ares = UnitArea(Symbol.ares, Coefficient.ares, Unit.ares)
        val hectares = UnitArea(Symbol.hectares, Coefficient.hectares, Unit.hectares)
    }

    override fun baseUnit() = squareMeters
}