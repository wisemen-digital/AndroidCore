package be.appwise.measurements.units

import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear

class UnitPower(symbol: String, converter: UnitConverter) : Dimension(symbol, converter) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

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

    companion object {
        val terawatts = UnitPower(Symbol.terawatts, Coefficient.terawatts)
        val gigawatts = UnitPower(Symbol.gigawatts, Coefficient.gigawatts)
        val megawatts = UnitPower(Symbol.megawatts, Coefficient.megawatts)
        val kilowatts = UnitPower(Symbol.kilowatts, Coefficient.kilowatts)
        val watts = UnitPower(Symbol.watts, Coefficient.watts)
        val milliwatts = UnitPower(Symbol.milliwatts, Coefficient.milliwatts)
        val microwatts = UnitPower(Symbol.microwatts, Coefficient.microwatts)
        val nanowatts = UnitPower(Symbol.nanowatts, Coefficient.nanowatts)
        val picowatts = UnitPower(Symbol.picowatts, Coefficient.picowatts)
        val femtowatts = UnitPower(Symbol.femtowatts, Coefficient.femtowatts)
        val horsepower = UnitPower(Symbol.horsepower, Coefficient.horsepower)
    }

    override fun baseUnit(): Dimension {
        return watts
    }
}
