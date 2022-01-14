package be.appwise.measurements.units

import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear

class UnitEnergy(symbol: String, converter: UnitConverter) : Dimension(symbol, converter) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

    private object Symbol {
        const val kilojoules = "kJ"
        const val joules = "J"
        const val kilocalories = "kCal"
        const val calories = "cal"
        const val kilowattHours = "kWh"
    }

    private object Coefficient {
        const val kilojoules = 1e3
        const val joules = 1.0
        const val kilocalories = 4184.0
        const val calories = 4.184
        const val kilowattHours = 3_600_000.0
    }

    companion object {
        val kilojoules = UnitEnergy(Symbol.kilojoules, Coefficient.kilojoules)
        val joules = UnitEnergy(Symbol.joules, Coefficient.joules)
        val kilocalories = UnitEnergy(Symbol.kilocalories, Coefficient.kilocalories)
        val calories = UnitEnergy(Symbol.calories, Coefficient.calories)
        val kilowattHours = UnitEnergy(Symbol.kilowattHours, Coefficient.kilowattHours)
    }

    override fun baseUnit(): Dimension {
        return joules
    }
}
