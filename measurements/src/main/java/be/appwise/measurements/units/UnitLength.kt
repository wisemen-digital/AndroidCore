package be.appwise.measurements.units

import android.icu.util.MeasureUnit
import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear
import be.appwise.measurements.isAtLeastN
import be.appwise.measurements.isAtLeastO

class UnitLength(symbol: String, converter: UnitConverter, measureUnit: MeasureUnit? = null) : Dimension(symbol, converter, measureUnit) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

    private constructor(symbol: String, coefficient: Double, measureUnit: MeasureUnit?) : this(symbol, UnitConverterLinear(coefficient), measureUnit)

    private object Symbol {
        const val megameters = "Mm"
        const val kilometers = "km"
        const val hectometers = "hm"
        const val decameters = "dam"
        const val meters = "m"
        const val decimeters = "dm"
        const val centimeters = "cm"
        const val millimeters = "mm"
        const val micrometers = "µm"
        const val nanometers = "nm"
        const val picometers = "pm"
        const val inches = "in"
        const val feet = "ft"
        const val yards = "yd"
        const val miles = "mi"
        const val scandinavianMiles = "smi"
        const val lightyears = "ly"
        const val nauticalMiles = "NM"
        const val fathoms = "ftm"
        const val furlongs = "fur"
        const val astronomicalUnits = "ua"
        const val parsecs = "pc"
    }

    private object Coefficient {
        const val megameters = 1e6
        const val kilometers = 1e3
        const val hectometers = 1e2
        const val decameters = 1e1
        const val meters = 1.0
        const val decimeters = 1e-1
        const val centimeters = 1e-2
        const val millimeters = 1e-3
        const val micrometers = 1e-6
        const val nanometers = 1e-9
        const val picometers = 1e-12
        const val inches = 0.0254
        const val feet = 0.3048
        const val yards = 0.9144
        const val miles = 1609.34
        const val scandinavianMiles = 10000.0
        const val lightyears = 9.461e+15
        const val nauticalMiles = 1852.0
        const val fathoms = 1.8288
        const val furlongs = 201.168
        const val astronomicalUnits = 1.496e+11
        const val parsecs = 3.086e+16
    }

    private object Unit {
        val megameters = null
        val kilometers = if (isAtLeastN) MeasureUnit.KILOMETER else null
        val hectometers = null
        val decameters = null
        val meters = if (isAtLeastN) MeasureUnit.METER else null
        val decimeters = if (isAtLeastN) MeasureUnit.DECIMETER else null
        val centimeters = if (isAtLeastN) MeasureUnit.CENTIMETER else null
        val millimeters = if (isAtLeastN) MeasureUnit.MILLIMETER else null
        val micrometers = if (isAtLeastN) MeasureUnit.MICROMETER else null
        val nanometers = if (isAtLeastN) MeasureUnit.NANOMETER else null
        val picometers = if (isAtLeastN) MeasureUnit.PICOMETER else null
        val inches = if (isAtLeastN) MeasureUnit.INCH else null
        val feet = if (isAtLeastN) MeasureUnit.FOOT else null
        val yards = if (isAtLeastN) MeasureUnit.YARD else null
        val miles = if (isAtLeastN) MeasureUnit.MILE else null
        val scandinavianMiles = if (isAtLeastO) MeasureUnit.MILE_SCANDINAVIAN else null
        val lightyears = if (isAtLeastN) MeasureUnit.LIGHT_YEAR else null
        val nauticalMiles = if (isAtLeastN) MeasureUnit.NAUTICAL_MILE else null
        val fathoms = if (isAtLeastN) MeasureUnit.FATHOM else null
        val furlongs = if (isAtLeastN) MeasureUnit.FURLONG else null
        val astronomicalUnits = if (isAtLeastN) MeasureUnit.ASTRONOMICAL_UNIT else null
        val parsecs = if (isAtLeastN) MeasureUnit.PARSEC else null
    }

    companion object {
        val megameters = UnitLength(Symbol.megameters, Coefficient.megameters, Unit.megameters)
        val kilometers = UnitLength(Symbol.kilometers, Coefficient.kilometers, Unit.kilometers)
        val hectometers = UnitLength(Symbol.hectometers, Coefficient.hectometers, Unit.hectometers)
        val decameters = UnitLength(Symbol.decameters, Coefficient.decameters, Unit.decameters)
        val meters = UnitLength(Symbol.meters, Coefficient.meters, Unit.meters)
        val decimeters = UnitLength(Symbol.decimeters, Coefficient.decimeters, Unit.decimeters)
        val centimeters = UnitLength(Symbol.centimeters, Coefficient.centimeters, Unit.centimeters)
        val millimeters = UnitLength(Symbol.millimeters, Coefficient.millimeters, Unit.millimeters)
        val micrometers = UnitLength(Symbol.micrometers, Coefficient.micrometers, Unit.micrometers)
        val nanometers = UnitLength(Symbol.nanometers, Coefficient.nanometers, Unit.nanometers)
        val picometers = UnitLength(Symbol.picometers, Coefficient.picometers, Unit.picometers)
        val inches = UnitLength(Symbol.inches, Coefficient.inches, Unit.inches)
        val feet = UnitLength(Symbol.feet, Coefficient.feet, Unit.feet)
        val yards = UnitLength(Symbol.yards, Coefficient.yards, Unit.yards)
        val miles = UnitLength(Symbol.miles, Coefficient.miles, Unit.miles)
        val scandinavianMiles = UnitLength(Symbol.scandinavianMiles, Coefficient.scandinavianMiles, Unit.scandinavianMiles)
        val lightyears = UnitLength(Symbol.lightyears, Coefficient.lightyears, Unit.lightyears)
        val nauticalMiles = UnitLength(Symbol.nauticalMiles, Coefficient.nauticalMiles, Unit.nauticalMiles)
        val fathoms = UnitLength(Symbol.fathoms, Coefficient.fathoms, Unit.fathoms)
        val furlongs = UnitLength(Symbol.furlongs, Coefficient.furlongs, Unit.furlongs)
        val astronomicalUnits = UnitLength(Symbol.astronomicalUnits, Coefficient.astronomicalUnits, Unit.astronomicalUnits)
        val parsecs = UnitLength(Symbol.parsecs, Coefficient.parsecs, Unit.parsecs)
    }

    override fun baseUnit() = meters
}