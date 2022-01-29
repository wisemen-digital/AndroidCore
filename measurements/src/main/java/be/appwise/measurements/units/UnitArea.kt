package be.appwise.measurements.units

import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear

class UnitArea(symbol: String, converter: UnitConverter) : Dimension(symbol, converter) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

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

    companion object {
        val squareMegameters = UnitArea(Symbol.squareMegameters, Coefficient.squareMegameters)
        val squareKilometers = UnitArea(Symbol.squareKilometers, Coefficient.squareKilometers)
        val squareMeters = UnitArea(Symbol.squareMeters, Coefficient.squareMeters)
        val squareCentimeters = UnitArea(Symbol.squareCentimeters, Coefficient.squareCentimeters)
        val squareMillimeters = UnitArea(Symbol.squareMillimeters, Coefficient.squareMillimeters)
        val squareMicrometers = UnitArea(Symbol.squareMicrometers, Coefficient.squareMicrometers)
        val squareNanometers = UnitArea(Symbol.squareNanometers, Coefficient.squareNanometers)
        val squareInches = UnitArea(Symbol.squareInches, Coefficient.squareInches)
        val squareFeet = UnitArea(Symbol.squareFeet, Coefficient.squareFeet)
        val squareYards = UnitArea(Symbol.squareYards, Coefficient.squareYards)
        val squareMiles = UnitArea(Symbol.squareMiles, Coefficient.squareMiles)
        val acres = UnitArea(Symbol.acres, Coefficient.acres)
        val ares = UnitArea(Symbol.ares, Coefficient.ares)
        val hectares = UnitArea(Symbol.hectares, Coefficient.hectares)
    }

    override fun baseUnit() = squareMeters
}