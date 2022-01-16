package be.appwise.measurements.units

import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear
import kotlin.math.pow

class UnitInformationStorage(symbol: String, converter: UnitConverter) : Dimension(symbol, converter) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

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

    companion object {
        val bytes = UnitInformationStorage(Symbol.bytes, Coefficient.bytes)
        val bits = UnitInformationStorage(Symbol.bits, Coefficient.bits)
        val nibbles = UnitInformationStorage(Symbol.nibbles, Coefficient.nibbles)
        val yottabytes = UnitInformationStorage(Symbol.yottabytes, Coefficient.yottabytes)
        val zettabytes = UnitInformationStorage(Symbol.zettabytes, Coefficient.zettabytes)
        val exabytes = UnitInformationStorage(Symbol.exabytes, Coefficient.exabytes)
        val petabytes = UnitInformationStorage(Symbol.petabytes, Coefficient.petabytes)
        val terabytes = UnitInformationStorage(Symbol.terabytes, Coefficient.terabytes)
        val gigabytes = UnitInformationStorage(Symbol.gigabytes, Coefficient.gigabytes)
        val megabytes = UnitInformationStorage(Symbol.megabytes, Coefficient.megabytes)
        val kilobytes = UnitInformationStorage(Symbol.kilobytes, Coefficient.kilobytes)
        val yottabits = UnitInformationStorage(Symbol.yottabits, Coefficient.yottabits)
        val zettabits = UnitInformationStorage(Symbol.zettabits, Coefficient.zettabits)
        val exabits = UnitInformationStorage(Symbol.exabits, Coefficient.exabits)
        val petabits = UnitInformationStorage(Symbol.petabits, Coefficient.petabits)
        val terabits = UnitInformationStorage(Symbol.terabits, Coefficient.terabits)
        val gigabits = UnitInformationStorage(Symbol.gigabits, Coefficient.gigabits)
        val megabits = UnitInformationStorage(Symbol.megabits, Coefficient.megabits)
        val kilobits = UnitInformationStorage(Symbol.kilobits, Coefficient.kilobits)
        val yobibytes = UnitInformationStorage(Symbol.yobibytes, Coefficient.yobibytes)
        val zebibytes = UnitInformationStorage(Symbol.zebibytes, Coefficient.zebibytes)
        val exbibytes = UnitInformationStorage(Symbol.exbibytes, Coefficient.exbibytes)
        val pebibytes = UnitInformationStorage(Symbol.pebibytes, Coefficient.pebibytes)
        val tebibytes = UnitInformationStorage(Symbol.tebibytes, Coefficient.tebibytes)
        val gibibytes = UnitInformationStorage(Symbol.gibibytes, Coefficient.gibibytes)
        val mebibytes = UnitInformationStorage(Symbol.mebibytes, Coefficient.mebibytes)
        val kibibytes = UnitInformationStorage(Symbol.kibibytes, Coefficient.kibibytes)
        val yobibits = UnitInformationStorage(Symbol.yobibits, Coefficient.yobibits)
        val zebibits = UnitInformationStorage(Symbol.zebibits, Coefficient.zebibits)
        val exbibits = UnitInformationStorage(Symbol.exbibits, Coefficient.exbibits)
        val pebibits = UnitInformationStorage(Symbol.pebibits, Coefficient.pebibits)
        val tebibits = UnitInformationStorage(Symbol.tebibits, Coefficient.tebibits)
        val gibibits = UnitInformationStorage(Symbol.gibibits, Coefficient.gibibits)
        val mebibits = UnitInformationStorage(Symbol.mebibits, Coefficient.mebibits)
        val kibibits = UnitInformationStorage(Symbol.kibibits, Coefficient.kibibits)
    }

    override fun baseUnit() = bits
}