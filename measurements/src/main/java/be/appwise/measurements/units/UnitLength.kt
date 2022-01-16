package be.appwise.measurements.units

import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear

class UnitLength(symbol: String, converter: UnitConverter) : Dimension(symbol, converter) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

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

    companion object {
        val megameters = UnitLength(Symbol.megameters, Coefficient.megameters)
        val kilometers = UnitLength(Symbol.kilometers, Coefficient.kilometers)
        val hectometers = UnitLength(Symbol.hectometers, Coefficient.hectometers)
        val decameters = UnitLength(Symbol.decameters, Coefficient.decameters)
        val meters = UnitLength(Symbol.meters, Coefficient.meters)
        val decimeters = UnitLength(Symbol.decimeters, Coefficient.decimeters)
        val centimeters = UnitLength(Symbol.centimeters, Coefficient.centimeters)
        val millimeters = UnitLength(Symbol.millimeters, Coefficient.millimeters)
        val micrometers = UnitLength(Symbol.micrometers, Coefficient.micrometers)
        val nanometers = UnitLength(Symbol.nanometers, Coefficient.nanometers)
        val picometers = UnitLength(Symbol.picometers, Coefficient.picometers)
        val inches = UnitLength(Symbol.inches, Coefficient.inches)
        val feet = UnitLength(Symbol.feet, Coefficient.feet)
        val yards = UnitLength(Symbol.yards, Coefficient.yards)
        val miles = UnitLength(Symbol.miles, Coefficient.miles)
        val scandinavianMiles = UnitLength(Symbol.scandinavianMiles, Coefficient.scandinavianMiles)
        val lightyears = UnitLength(Symbol.lightyears, Coefficient.lightyears)
        val nauticalMiles = UnitLength(Symbol.nauticalMiles, Coefficient.nauticalMiles)
        val fathoms = UnitLength(Symbol.fathoms, Coefficient.fathoms)
        val furlongs = UnitLength(Symbol.furlongs, Coefficient.furlongs)
        val astronomicalUnits = UnitLength(Symbol.astronomicalUnits, Coefficient.astronomicalUnits)
        val parsecs = UnitLength(Symbol.parsecs, Coefficient.parsecs)
    }

    override fun baseUnit() = meters
}