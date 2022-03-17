package be.appwise.measurements.units

import android.icu.util.MeasureUnit
import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear
import be.appwise.measurements.isAtLeastO

class UnitEnergy(symbol: String, converter: UnitConverter, measureUnit: MeasureUnit? = null) : Dimension(symbol, converter, measureUnit) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

    private constructor(symbol: String, coefficient: Double, measureUnit: MeasureUnit?) : this(symbol, UnitConverterLinear(coefficient), measureUnit)

    private object Symbol {
        const val kilojoules = "kJ"
        const val joules = "J"
        const val kilocalories = "kCal"
        const val calories = "cal"
        const val gigawattHours = "GWh"
        const val megawattHours = "MWh"
        const val kilowattHours = "kWh"
        const val wattHours = "Wh"
    }

    private object Coefficient {
        const val kilojoules = 1e3
        const val joules = 1.0
        const val kilocalories = 4184.0
        const val calories = 4.184
        const val gigawattHours = 3_600_000_000_000.0
        const val megawattHours = 3_600_000_000.0
        const val kilowattHours = 3_600_000.0
        const val wattHours = 3600.0
    }

    private object Unit {
        val kilojoules = if (isAtLeastO) MeasureUnit.KILOJOULE else null
        val joules = if (isAtLeastO) MeasureUnit.JOULE else null
        val kilocalories = if (isAtLeastO) MeasureUnit.KILOCALORIE else null
        val calories = if (isAtLeastO) MeasureUnit.CALORIE else null
        val gigawattHours = null
        val megawattHours = null
        val kilowattHours = if (isAtLeastO) MeasureUnit.KILOWATT_HOUR else null
        val wattHours = null
    }

    companion object {
        val kilojoules = UnitEnergy(Symbol.kilojoules, Coefficient.kilojoules, Unit.kilojoules)
        val joules = UnitEnergy(Symbol.joules, Coefficient.joules, Unit.joules)
        val kilocalories = UnitEnergy(Symbol.kilocalories, Coefficient.kilocalories, Unit.kilocalories)
        val calories = UnitEnergy(Symbol.calories, Coefficient.calories, Unit.calories)
        val gigawattHours = UnitEnergy(Symbol.gigawattHours, Coefficient.gigawattHours, Unit.gigawattHours)
        val megawattHours = UnitEnergy(Symbol.megawattHours, Coefficient.megawattHours, Unit.megawattHours)
        val kilowattHours = UnitEnergy(Symbol.kilowattHours, Coefficient.kilowattHours, Unit.kilowattHours)
        val wattHours = UnitEnergy(Symbol.wattHours, Coefficient.wattHours, Unit.wattHours)
    }

    override fun baseUnit() = joules
}
