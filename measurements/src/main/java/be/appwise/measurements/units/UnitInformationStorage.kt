package be.appwise.measurements.units

import android.icu.util.MeasureUnit
import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear
import be.appwise.measurements.isAtLeastO
import be.appwise.measurements.isAtLeastR
import kotlin.math.pow

class UnitInformationStorage(symbol: String, converter: UnitConverter, measureUnit: MeasureUnit? = null) : Dimension(symbol, converter, measureUnit) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

    private constructor(symbol: String, coefficient: Double, measureUnit: MeasureUnit?) : this(symbol, UnitConverterLinear(coefficient), measureUnit)

    private object Symbol {
        const val bytes = "B"
        const val bits = "bit"
        const val nibbles = "nibble"
        const val yottabytes = "YB"
        const val zettabytes = "ZB"
        const val exabytes = "EB"
        const val petabytes = "PB"
        const val terabytes = "TB"
        const val gigabytes = "GB"
        const val megabytes = "MB"
        const val kilobytes = "kB"

        const val yottabits = "Yb"
        const val zettabits = "Zb"
        const val exabits = "Eb"
        const val petabits = "Pb"
        const val terabits = "Tb"
        const val gigabits = "Gb"
        const val megabits = "Mb"
        const val kilobits = "kb"

        const val yobibytes = "YiB"
        const val zebibytes = "ZiB"
        const val exbibytes = "EiB"
        const val pebibytes = "PiB"
        const val tebibytes = "TiB"
        const val gibibytes = "GiB"
        const val mebibytes = "MiB"
        const val kibibytes = "KiB"

        const val yobibits = "Yib"
        const val zebibits = "Zib"
        const val exbibits = "Eib"
        const val pebibits = "Pib"
        const val tebibits = "Tib"
        const val gibibits = "Gib"
        const val mebibits = "Mib"
        const val kibibits = "Kib"
    }

    private object Coefficient {
        const val bytes = 8.0
        const val bits = 1.0
        const val nibbles = 4.0
        val yottabytes = 8.0 * 1000.0.pow(8.0)
        val zettabytes = 8.0 * 1000.0.pow(7.0)
        val exabytes = 8.0 * 1000.0.pow(6.0)
        val petabytes = 8.0 * 1000.0.pow(5.0)
        val terabytes = 8.0 * 1000.0.pow(4.0)
        val gigabytes = 8.0 * 1000.0.pow(3.0)
        val megabytes = 8.0 * 1000.0.pow(2.0)
        const val kilobytes = 8.0 * 1000

        val yottabits = 1000.0.pow(8.0)
        val zettabits = 1000.0.pow(7.0)
        val exabits = 1000.0.pow(6.0)
        val petabits = 1000.0.pow(5.0)
        val terabits = 1000.0.pow(4.0)
        val gigabits = 1000.0.pow(3.0)
        val megabits = 1000.0.pow(2.0)
        const val kilobits = 1000.0

        val yobibytes = 8 * 1024.0.pow(8.0)
        val zebibytes = 8 * 1024.0.pow(7.0)
        val exbibytes = 8 * 1024.0.pow(6.0)
        val pebibytes = 8 * 1024.0.pow(5.0)
        val tebibytes = 8 * 1024.0.pow(4.0)
        val gibibytes = 8 * 1024.0.pow(3.0)
        val mebibytes = 8 * 1024.0.pow(2.0)
        const val kibibytes = 8 * 1024.0

        val yobibits = 1024.0.pow(8.0)
        val zebibits = 1024.0.pow(7.0)
        val exbibits = 1024.0.pow(6.0)
        val pebibits = 1024.0.pow(5.0)
        val tebibits = 1024.0.pow(4.0)
        val gibibits = 1024.0.pow(3.0)
        val mebibits = 1024.0.pow(2.0)
        const val kibibits = 1024.0
    }

    private object Unit {
        val bytes = if (isAtLeastO) MeasureUnit.BYTE else null
        val bits = if (isAtLeastO) MeasureUnit.BIT else null
        val nibbles = null
        val yottabytes = null
        val zettabytes = null
        val exabytes = null
        val petabytes = if (isAtLeastR) MeasureUnit.PETABYTE else null
        val terabytes = if (isAtLeastO) MeasureUnit.TERABYTE else null
        val gigabytes = if (isAtLeastO) MeasureUnit.GIGABYTE else null
        val megabytes = if (isAtLeastO) MeasureUnit.MEGABYTE else null
        val kilobytes = if (isAtLeastO) MeasureUnit.KILOBYTE else null
        val yottabits = null
        val zettabits = null
        val exabits = null
        val petabits = null
        val terabits = if (isAtLeastO) MeasureUnit.TERABIT else null
        val gigabits = if (isAtLeastO) MeasureUnit.GIGABIT else null
        val megabits = if (isAtLeastO) MeasureUnit.MEGABIT else null
        val kilobits = if (isAtLeastO) MeasureUnit.KILOBIT else null
        val yobibytes = null
        val zebibytes = null
        val exbibytes = null
        val pebibytes = null
        val tebibytes = null
        val gibibytes = null
        val mebibytes = null
        val kibibytes = null
        val yobibits = null
        val zebibits = null
        val exbibits = null
        val pebibits = null
        val tebibits = null
        val gibibits = null
        val mebibits = null
        val kibibits = null
    }

    companion object {
        val bytes = UnitInformationStorage(Symbol.bytes, Coefficient.bytes, Unit.bytes)
        val bits = UnitInformationStorage(Symbol.bits, Coefficient.bits, Unit.bits)
        val nibbles = UnitInformationStorage(Symbol.nibbles, Coefficient.nibbles, Unit.nibbles)
        val yottabytes = UnitInformationStorage(Symbol.yottabytes, Coefficient.yottabytes, Unit.yottabytes)
        val zettabytes = UnitInformationStorage(Symbol.zettabytes, Coefficient.zettabytes, Unit.zettabytes)
        val exabytes = UnitInformationStorage(Symbol.exabytes, Coefficient.exabytes, Unit.exabytes)
        val petabytes = UnitInformationStorage(Symbol.petabytes, Coefficient.petabytes, Unit.petabytes)
        val terabytes = UnitInformationStorage(Symbol.terabytes, Coefficient.terabytes, Unit.terabytes)
        val gigabytes = UnitInformationStorage(Symbol.gigabytes, Coefficient.gigabytes, Unit.gigabytes)
        val megabytes = UnitInformationStorage(Symbol.megabytes, Coefficient.megabytes, Unit.megabytes)
        val kilobytes = UnitInformationStorage(Symbol.kilobytes, Coefficient.kilobytes, Unit.kilobytes)
        val yottabits = UnitInformationStorage(Symbol.yottabits, Coefficient.yottabits, Unit.yottabits)
        val zettabits = UnitInformationStorage(Symbol.zettabits, Coefficient.zettabits, Unit.zettabits)
        val exabits = UnitInformationStorage(Symbol.exabits, Coefficient.exabits, Unit.exabits)
        val petabits = UnitInformationStorage(Symbol.petabits, Coefficient.petabits, Unit.petabits)
        val terabits = UnitInformationStorage(Symbol.terabits, Coefficient.terabits, Unit.terabits)
        val gigabits = UnitInformationStorage(Symbol.gigabits, Coefficient.gigabits, Unit.gigabits)
        val megabits = UnitInformationStorage(Symbol.megabits, Coefficient.megabits, Unit.megabits)
        val kilobits = UnitInformationStorage(Symbol.kilobits, Coefficient.kilobits, Unit.kilobits)
        val yobibytes = UnitInformationStorage(Symbol.yobibytes, Coefficient.yobibytes, Unit.yobibytes)
        val zebibytes = UnitInformationStorage(Symbol.zebibytes, Coefficient.zebibytes, Unit.zebibytes)
        val exbibytes = UnitInformationStorage(Symbol.exbibytes, Coefficient.exbibytes, Unit.exbibytes)
        val pebibytes = UnitInformationStorage(Symbol.pebibytes, Coefficient.pebibytes, Unit.pebibytes)
        val tebibytes = UnitInformationStorage(Symbol.tebibytes, Coefficient.tebibytes, Unit.tebibytes)
        val gibibytes = UnitInformationStorage(Symbol.gibibytes, Coefficient.gibibytes, Unit.gibibytes)
        val mebibytes = UnitInformationStorage(Symbol.mebibytes, Coefficient.mebibytes, Unit.mebibytes)
        val kibibytes = UnitInformationStorage(Symbol.kibibytes, Coefficient.kibibytes, Unit.kibibytes)
        val yobibits = UnitInformationStorage(Symbol.yobibits, Coefficient.yobibits, Unit.yobibits)
        val zebibits = UnitInformationStorage(Symbol.zebibits, Coefficient.zebibits, Unit.zebibits)
        val exbibits = UnitInformationStorage(Symbol.exbibits, Coefficient.exbibits, Unit.exbibits)
        val pebibits = UnitInformationStorage(Symbol.pebibits, Coefficient.pebibits, Unit.pebibits)
        val tebibits = UnitInformationStorage(Symbol.tebibits, Coefficient.tebibits, Unit.tebibits)
        val gibibits = UnitInformationStorage(Symbol.gibibits, Coefficient.gibibits, Unit.gibibits)
        val mebibits = UnitInformationStorage(Symbol.mebibits, Coefficient.mebibits, Unit.mebibits)
        val kibibits = UnitInformationStorage(Symbol.kibibits, Coefficient.kibibits, Unit.kibibits)
    }

    override fun baseUnit() = bits
}