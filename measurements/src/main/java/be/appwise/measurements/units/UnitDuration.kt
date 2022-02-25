package be.appwise.measurements.units

import android.icu.util.MeasureUnit
import android.icu.util.TimeUnit
import android.os.Build
import androidx.annotation.RequiresApi
import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear
import be.appwise.measurements.isAtLeastO

class UnitDuration(symbol: String, converter: UnitConverter, measureUnit: MeasureUnit? = null) : Dimension(symbol, converter, measureUnit) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

    private constructor(symbol: String, coefficient: Double, measureUnit: MeasureUnit?) : this(symbol, UnitConverterLinear(coefficient), measureUnit)

    private object Symbol {
        const val seconds = "s"
        const val minutes = "m"
        const val hours = "h"
    }

    private object Coefficient {
        const val seconds = 1.0
        const val minutes = 60.0
        const val hours = 3600.0
    }

    companion object {
        val seconds: UnitDuration = UnitDuration(Symbol.seconds, Coefficient.seconds)
        val minutes: UnitDuration = UnitDuration(Symbol.minutes, Coefficient.minutes)
        val hours: UnitDuration = UnitDuration(Symbol.hours, Coefficient.hours)
    }

    override fun baseUnit(): UnitDuration {
        return seconds
    }
}
