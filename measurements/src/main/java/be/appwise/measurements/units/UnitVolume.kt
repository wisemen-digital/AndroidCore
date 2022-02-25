package be.appwise.measurements.units

import android.icu.util.MeasureUnit
import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear
import be.appwise.measurements.isAtLeastN
import be.appwise.measurements.isAtLeastP

class UnitVolume(symbol: String, converter: UnitConverter, measureUnit: MeasureUnit? = null) : Dimension(symbol, converter, measureUnit) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

    private constructor(symbol: String, coefficient: Double, measureUnit: MeasureUnit?) : this(symbol, UnitConverterLinear(coefficient), measureUnit)

    private object Symbol {
        const val megaliters = "ML"
        const val kiloliters = "kL"
        const val liters = "L"
        const val deciliters = "dl"
        const val centiliters = "cL"
        const val milliliters = "mL"
        const val cubicKilometers = "km³"
        const val cubicMeters = "m³"
        const val cubicDecimeters = "dm³"
        const val cubicCentimeters = "cm³"
        const val cubicMillimeters = "mm³"
        const val cubicInches = "in³"
        const val cubicFeet = "ft³"
        const val cubicYards = "yd³"
        const val cubicMiles = "mi³"
        const val acreFeet = "af"
        const val bushels = "bsh"
        const val teaspoons = "tsp"
        const val tablespoons = "tbsp"
        const val fluidOunces = "fl oz"
        const val cups = "cup"
        const val pints = "pt"
        const val quarts = "qt"
        const val gallons = "gal"
        const val imperialTeaspoons = "tsp Imperial"
        const val imperialTablespoons = "tbsp Imperial"
        const val imperialFluidOunces = "fl oz Imperial"
        const val imperialPints = "pt Imperial"
        const val imperialQuarts = "qt Imperial"
        const val imperialGallons = "gal Imperial"
        const val metricCups = "metric cup Imperial"
    }

    private object Coefficient {
        const val megaliters = 1e6
        const val kiloliters = 1e3
        const val liters = 1.0
        const val deciliters = 1e-1
        const val centiliters = 1e-2
        const val milliliters = 1e-3
        const val cubicKilometers = 1e12
        const val cubicMeters = 1000.0
        const val cubicDecimeters = 1.0
        const val cubicCentimeters = 1e-3
        const val cubicMillimeters = 1e-6
        const val cubicInches = 0.0163871
        const val cubicFeet = 28.3168
        const val cubicYards = 764.555
        const val cubicMiles = 4.168e+12
        const val acreFeet = 1.233e+6
        const val bushels = 35.2391
        const val teaspoons = 0.00492892
        const val tablespoons = 0.0147868
        const val fluidOunces = 0.0295735
        const val cups = 0.24
        const val pints = 0.473176
        const val quarts = 0.946353
        const val gallons = 3.78541
        const val imperialTeaspoons = 0.00591939
        const val imperialTablespoons = 0.0177582
        const val imperialFluidOunces = 0.0284131
        const val imperialPints = 0.568261
        const val imperialQuarts = 1.13652
        const val imperialGallons = 4.54609
        const val metricCups = 0.25
    }

    private object Unit {
        val megaliters = if (isAtLeastN) MeasureUnit.MEGALITER else null
        val kiloliters = null
        val liters = if (isAtLeastN) MeasureUnit.LITER else null
        val deciliters = if (isAtLeastN) MeasureUnit.DECILITER else null
        val centiliters = if (isAtLeastN) MeasureUnit.CENTILITER else null
        val milliliters = if (isAtLeastN) MeasureUnit.MILLILITER else null
        val cubicKilometers = if (isAtLeastN) MeasureUnit.CUBIC_KILOMETER else null
        val cubicMeters = if (isAtLeastN) MeasureUnit.CUBIC_METER else null
        val cubicDecimeters = null
        val cubicCentimeters = if (isAtLeastN) MeasureUnit.CUBIC_CENTIMETER else null
        val cubicMillimeters = null
        val cubicInches = if (isAtLeastN) MeasureUnit.CUBIC_INCH else null
        val cubicFeet = if (isAtLeastN) MeasureUnit.CUBIC_FOOT else null
        val cubicYards = if (isAtLeastN) MeasureUnit.CUBIC_YARD else null
        val cubicMiles = if (isAtLeastN) MeasureUnit.CUBIC_MILE else null
        val acreFeet = if (isAtLeastN) MeasureUnit.ACRE_FOOT else null
        val bushels = if (isAtLeastN) MeasureUnit.BUSHEL else null
        val teaspoons = if (isAtLeastN) MeasureUnit.TEASPOON else null
        val tablespoons = if (isAtLeastN) MeasureUnit.TABLESPOON else null
        val fluidOunces = if (isAtLeastN) MeasureUnit.FLUID_OUNCE else null
        val cups = if (isAtLeastN) MeasureUnit.CUP else null
        val pints = if (isAtLeastN) MeasureUnit.PINT else null
        val quarts = if (isAtLeastN) MeasureUnit.QUART else null
        val gallons = if (isAtLeastN) MeasureUnit.GALLON else null
        val imperialTeaspoons = null
        val imperialTablespoons = null
        val imperialFluidOunces = null
        val imperialPints = null
        val imperialQuarts = null
        val imperialGallons = if (isAtLeastP) MeasureUnit.GALLON_IMPERIAL else null
        val metricCups = if (isAtLeastP) MeasureUnit.CUP_METRIC else null
    }

    companion object {
        val megaliters = UnitVolume(Symbol.megaliters, Coefficient.megaliters, Unit.megaliters)
        val kiloliters = UnitVolume(Symbol.kiloliters, Coefficient.kiloliters, Unit.kiloliters)
        val liters = UnitVolume(Symbol.liters, Coefficient.liters, Unit.liters)
        val deciliters = UnitVolume(Symbol.deciliters, Coefficient.deciliters, Unit.deciliters)
        val centiliters = UnitVolume(Symbol.centiliters, Coefficient.centiliters, Unit.centiliters)
        val milliliters = UnitVolume(Symbol.milliliters, Coefficient.milliliters, Unit.milliliters)
        val cubicKilometers = UnitVolume(Symbol.cubicKilometers, Coefficient.cubicKilometers, Unit.cubicKilometers)
        val cubicMeters = UnitVolume(Symbol.cubicMeters, Coefficient.cubicMeters, Unit.cubicMeters)
        val cubicDecimeters = UnitVolume(Symbol.cubicDecimeters, Coefficient.cubicDecimeters, Unit.cubicDecimeters)
        val cubicCentimeters = UnitVolume(Symbol.cubicCentimeters, Coefficient.cubicCentimeters, Unit.cubicCentimeters)
        val cubicMillimeters = UnitVolume(Symbol.cubicMillimeters, Coefficient.cubicMillimeters, Unit.cubicMillimeters)
        val cubicInches = UnitVolume(Symbol.cubicInches, Coefficient.cubicInches, Unit.cubicInches)
        val cubicFeet = UnitVolume(Symbol.cubicFeet, Coefficient.cubicFeet, Unit.cubicFeet)
        val cubicYards = UnitVolume(Symbol.cubicYards, Coefficient.cubicYards, Unit.cubicYards)
        val cubicMiles = UnitVolume(Symbol.cubicMiles, Coefficient.cubicMiles, Unit.cubicMiles)
        val acreFeet = UnitVolume(Symbol.acreFeet, Coefficient.acreFeet, Unit.acreFeet)
        val bushels = UnitVolume(Symbol.bushels, Coefficient.bushels, Unit.bushels)
        val teaspoons = UnitVolume(Symbol.teaspoons, Coefficient.teaspoons, Unit.teaspoons)
        val tablespoons = UnitVolume(Symbol.tablespoons, Coefficient.tablespoons, Unit.tablespoons)
        val fluidOunces = UnitVolume(Symbol.fluidOunces, Coefficient.fluidOunces, Unit.fluidOunces)
        val cups = UnitVolume(Symbol.cups, Coefficient.cups, Unit.cups)
        val pints = UnitVolume(Symbol.pints, Coefficient.pints, Unit.pints)
        val quarts = UnitVolume(Symbol.quarts, Coefficient.quarts, Unit.quarts)
        val gallons = UnitVolume(Symbol.gallons, Coefficient.gallons, Unit.gallons)
        val imperialTeaspoons = UnitVolume(Symbol.imperialTeaspoons, Coefficient.imperialTeaspoons, Unit.imperialTeaspoons)
        val imperialTablespoons = UnitVolume(Symbol.imperialTablespoons, Coefficient.imperialTablespoons, Unit.imperialTablespoons)
        val imperialFluidOunces = UnitVolume(Symbol.imperialFluidOunces, Coefficient.imperialFluidOunces, Unit.imperialFluidOunces)
        val imperialPints = UnitVolume(Symbol.imperialPints, Coefficient.imperialPints, Unit.imperialPints)
        val imperialQuarts = UnitVolume(Symbol.imperialQuarts, Coefficient.imperialQuarts, Unit.imperialQuarts)
        val imperialGallons = UnitVolume(Symbol.imperialGallons, Coefficient.imperialGallons, Unit.imperialGallons)
        val metricCups = UnitVolume(Symbol.metricCups, Coefficient.metricCups, Unit.metricCups)
    }

    override fun baseUnit() = liters
}