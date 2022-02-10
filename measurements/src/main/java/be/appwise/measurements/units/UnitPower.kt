package be.appwise.measurements.units

import android.icu.util.MeasureUnit
import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear
import be.appwise.measurements.isAtLeastO

class UnitPower(symbol: String, converter: UnitConverter, measureUnit: MeasureUnit? = null) : Dimension(symbol, converter, measureUnit) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

    private constructor(symbol: String, coefficient: Double, measureUnit: MeasureUnit?) : this(symbol, UnitConverterLinear(coefficient), measureUnit)

    private object Symbol {
        const val terawatts = "TW"
        const val gigawatts = "GW"
        const val megawatts = "MW"
        const val kilowatts = "kW"
        const val watts = "W"
        const val milliwatts = "mW"
        const val microwatts = "µW"
        const val nanowatts = "nW"
        const val picowatts = "pW"
        const val femtowatts = "fW"
        const val horsepower = "hp"
    }

    private object Coefficient {
        const val terawatts = 1e12
        const val gigawatts = 1e9
        const val megawatts = 1e6
        const val kilowatts = 1e3
        const val watts = 1.0
        const val milliwatts = 1e-3
        const val microwatts = 1e-6
        const val nanowatts = 1e-9
        const val picowatts = 1e-12
        const val femtowatts = 1e-15
        const val horsepower = 745.7
    }

    private object Unit {
        val terawatts = null
        val gigawatts = if (isAtLeastO) MeasureUnit.GIGAWATT else null
        val megawatts = if (isAtLeastO) MeasureUnit.MEGAWATT else null
        val kilowatts = if (isAtLeastO) MeasureUnit.KILOWATT else null
        val watts = if (isAtLeastO) MeasureUnit.WATT else null
        val milliwatts = if (isAtLeastO) MeasureUnit.MILLIWATT else null
        val microwatts = null
        val nanowatts = null
        val picowatts = null
        val femtowatts = null
        val horsepower = if (isAtLeastO) MeasureUnit.HORSEPOWER else null
    }

    companion object {
        val terawatts = UnitPower(Symbol.terawatts, Coefficient.terawatts, Unit.terawatts)
        val gigawatts = UnitPower(Symbol.gigawatts, Coefficient.gigawatts, Unit.gigawatts)
        val megawatts = UnitPower(Symbol.megawatts, Coefficient.megawatts, Unit.megawatts)
        val kilowatts = UnitPower(Symbol.kilowatts, Coefficient.kilowatts, Unit.kilowatts)
        val watts = UnitPower(Symbol.watts, Coefficient.watts, Unit.watts)
        val milliwatts = UnitPower(Symbol.milliwatts, Coefficient.milliwatts, Unit.milliwatts)
        val microwatts = UnitPower(Symbol.microwatts, Coefficient.microwatts, Unit.microwatts)
        val nanowatts = UnitPower(Symbol.nanowatts, Coefficient.nanowatts, Unit.nanowatts)
        val picowatts = UnitPower(Symbol.picowatts, Coefficient.picowatts, Unit.picowatts)
        val femtowatts = UnitPower(Symbol.femtowatts, Coefficient.femtowatts, Unit.femtowatts)
        val horsepower = UnitPower(Symbol.horsepower, Coefficient.horsepower, Unit.horsepower)
    }

    override fun baseUnit(): Dimension {
        return watts
    }
}
