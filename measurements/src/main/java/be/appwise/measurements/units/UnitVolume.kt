package be.appwise.measurements.units

import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear

class UnitVolume(symbol: String, converter: UnitConverter) : Dimension(symbol, converter) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

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

    companion object {
        val megaliters = UnitVolume(Symbol.megaliters, Coefficient.megaliters)
        val kiloliters = UnitVolume(Symbol.kiloliters, Coefficient.kiloliters)
        val liters = UnitVolume(Symbol.liters, Coefficient.liters)
        val deciliters = UnitVolume(Symbol.deciliters, Coefficient.deciliters)
        val centiliters = UnitVolume(Symbol.centiliters, Coefficient.centiliters)
        val milliliters = UnitVolume(Symbol.milliliters, Coefficient.milliliters)
        val cubicKilometers = UnitVolume(Symbol.cubicKilometers, Coefficient.cubicKilometers)
        val cubicMeters = UnitVolume(Symbol.cubicMeters, Coefficient.cubicMeters)
        val cubicDecimeters = UnitVolume(Symbol.cubicDecimeters, Coefficient.cubicDecimeters)
        val cubicCentimeters = UnitVolume(Symbol.cubicCentimeters, Coefficient.cubicCentimeters)
        val cubicMillimeters = UnitVolume(Symbol.cubicMillimeters, Coefficient.cubicMillimeters)
        val cubicInches = UnitVolume(Symbol.cubicInches, Coefficient.cubicInches)
        val cubicFeet = UnitVolume(Symbol.cubicFeet, Coefficient.cubicFeet)
        val cubicYards = UnitVolume(Symbol.cubicYards, Coefficient.cubicYards)
        val cubicMiles = UnitVolume(Symbol.cubicMiles, Coefficient.cubicMiles)
        val acreFeet = UnitVolume(Symbol.acreFeet, Coefficient.acreFeet)
        val bushels = UnitVolume(Symbol.bushels, Coefficient.bushels)
        val teaspoons = UnitVolume(Symbol.teaspoons, Coefficient.teaspoons)
        val tablespoons = UnitVolume(Symbol.tablespoons, Coefficient.tablespoons)
        val fluidOunces = UnitVolume(Symbol.fluidOunces, Coefficient.fluidOunces)
        val cups = UnitVolume(Symbol.cups, Coefficient.cups)
        val pints = UnitVolume(Symbol.pints, Coefficient.pints)
        val quarts = UnitVolume(Symbol.quarts, Coefficient.quarts)
        val gallons = UnitVolume(Symbol.gallons, Coefficient.gallons)
        val imperialTeaspoons = UnitVolume(Symbol.imperialTeaspoons, Coefficient.imperialTeaspoons)
        val imperialTablespoons = UnitVolume(Symbol.imperialTablespoons, Coefficient.imperialTablespoons)
        val imperialFluidOunces = UnitVolume(Symbol.imperialFluidOunces, Coefficient.imperialFluidOunces)
        val imperialPints = UnitVolume(Symbol.imperialPints, Coefficient.imperialPints)
        val imperialQuarts = UnitVolume(Symbol.imperialQuarts, Coefficient.imperialQuarts)
        val imperialGallons = UnitVolume(Symbol.imperialGallons, Coefficient.imperialGallons)
        val metricCups = UnitVolume(Symbol.metricCups, Coefficient.metricCups)
    }

    override fun baseUnit() = liters
}