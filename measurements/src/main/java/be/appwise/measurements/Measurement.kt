package be.appwise.measurements

import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.os.Build
import androidx.annotation.RequiresApi
import be.appwise.measurements.units.Dimension
import be.appwise.measurements.units.UnitEnergy
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*
import kotlin.math.absoluteValue

// Based this complete thing on swift
// https://github.com/apple/swift-corelibs-foundation/blob/main/Sources/Foundation/Unit.swift
// https://github.com/apple/swift-corelibs-foundation/blob/main/Sources/Foundation/Measurement.swift
class Measurement<UnitType : Dimension>(var value: Double, unit: UnitType) {

    companion object;

    var unit: UnitType = unit
        private set

    /**
     * Returns a new measurement created by converting to the specified unit.
     *
     * @param otherUnit: A unit of the same '[Dimension]'
     * @return A converted measurement
     */
    fun converted(otherUnit: UnitType): Measurement<UnitType> {
        return if (otherUnit.javaClass == this.unit.javaClass) {
            if (unit == otherUnit) {
                Measurement(value, otherUnit)
            } else {
                val valueInTermsOfBase = unit.converter.baseUnitValue(value)
                if (otherUnit == otherUnit.baseUnit()) {
                    Measurement(valueInTermsOfBase, otherUnit)
                } else {
                    val otherValueFromTermsOfBase = otherUnit.converter.value(valueInTermsOfBase)
                    Measurement(otherValueFromTermsOfBase, otherUnit)
                }
            }
        } else {
            throw Exception("Attempt to add measurements with non-equal units")
        }
    }

    /**
     * Converts the measurement to the specified result.
     * Use this function if you don't want to create a new object but rather change the current object.
     *
     * @param otherUnit: A unit of the same '[Dimension]'
     */
    fun convert(otherUnit: UnitType) {
        val newMeasurement = converted(otherUnit)
        value = newMeasurement.value
        unit = newMeasurement.unit
    }

    // <editor-fold desc="calculations">
    /**
     * Add two measurements of the same Dimension.
     *
     * If the [unit] of this object and [other] are [equals], then this returns the result of adding the [value] of each [Measurement].
     * If they are not equal, then this will convert both to the base unit of the [Dimension] and return the result as a [Measurement] of that base unit.
     * @return The result of adding the two measurements.
     */
    operator fun plus(other: Measurement<*>): Measurement<Dimension> {
        return if (other.unit.javaClass == this.unit.javaClass) {
            if (other.unit == unit) {
                Measurement(value + other.value, unit)
            } else {
                val lhsValueInTermsOfBase = unit.converter.baseUnitValue(value)
                val otherValueInTermsOfBase = other.unit.converter.baseUnitValue(other.value)
                Measurement(lhsValueInTermsOfBase + otherValueInTermsOfBase, unit.baseUnit())
            }
        } else {
            throw Exception("Attempt to add measurements with non-equal units")
        }
    }

    operator fun plus(other: Double): Measurement<Dimension> = Measurement(value + other, unit)
    operator fun plus(other: Float): Measurement<Dimension> = Measurement(value + other, unit)
    operator fun plus(other: Int): Measurement<Dimension> = Measurement(value + other, unit)
    operator fun plus(other: Long): Measurement<Dimension> = Measurement(value + other, unit)
    operator fun plus(other: Short): Measurement<Dimension> = Measurement(value + other, unit)

    /**
     *
     *  Subtract two measurements of the same Dimension.
     *
     *  If the [unit] of this object and [other] are [equals], then this returns the result of subtracting the [value] of each [Measurement].
     *  If they are not equal, then this will convert both to the base unit of the [Dimension] and return the result as a [Measurement] of that base unit.
     *  @return The result of adding the two measurements.
     */
    operator fun minus(other: Measurement<*>): Measurement<Dimension> {
        return if (other.unit.javaClass == this.unit.javaClass) {
            if (other.unit == unit) {
                Measurement(value - other.value, unit)
            } else {
                val lhsValueInTermsOfBase = unit.converter.baseUnitValue(value)
                val otherValueInTermsOfBase = other.unit.converter.baseUnitValue(other.value)
                Measurement(lhsValueInTermsOfBase - otherValueInTermsOfBase, unit.baseUnit())
            }
        } else {
            throw Exception("Attempt to subtract measurements with non-equal units")
        }
    }

    operator fun minus(other: Double): Measurement<Dimension> = Measurement(value - other, unit)
    operator fun minus(other: Float): Measurement<Dimension> = Measurement(value - other, unit)
    operator fun minus(other: Int): Measurement<Dimension> = Measurement(value - other, unit)
    operator fun minus(other: Long): Measurement<Dimension> = Measurement(value - other, unit)
    operator fun minus(other: Short): Measurement<Dimension> = Measurement(value - other, unit)

    /**
     * Divide two measurements of the same Dimension.
     *
     * If the [unit] of this object and [other] are [equals], then this returns the result of dividing the [value] of each [Measurement].
     * If they are not equal, then this will convert both to the base unit of the [Dimension] and return the result as a [Measurement] of that base unit.
     * @return The result of adding the two measurements.
     */
    operator fun div(other: Measurement<*>): Measurement<Dimension> {
        return if (other.unit.javaClass == this.unit.javaClass) {
            if (other.unit == unit) {
                Measurement(value / other.value, unit)
            } else {
                val lhsValueInTermsOfBase = unit.converter.baseUnitValue(value)
                val otherValueInTermsOfBase = other.unit.converter.baseUnitValue(other.value)
                Measurement(lhsValueInTermsOfBase / otherValueInTermsOfBase, unit.baseUnit())
            }
        } else {
            throw Exception("Attempt to divide measurements with non-equal units")
        }
    }

    operator fun div(other: Double): Measurement<Dimension> = Measurement(value / other, unit)
    operator fun div(other: Float): Measurement<Dimension> = Measurement(value / other, unit)
    operator fun div(other: Int): Measurement<Dimension> = Measurement(value / other, unit)
    operator fun div(other: Long): Measurement<Dimension> = Measurement(value / other, unit)
    operator fun div(other: Short): Measurement<Dimension> = Measurement(value / other, unit)

    /**
     * Multiply two measurements of the same Dimension.
     *
     * If the [unit] of this object and [other] are [equals], then this returns the result of multiplying the [value] of each [Measurement].
     * If they are not equal, then this will convert both to the base unit of the [Dimension] and return the result as a [Measurement] of that base unit.
     * @return The result of adding the two measurements.
     */
    operator fun times(other: Measurement<*>): Measurement<Dimension> {
        return if (other.unit.javaClass == this.unit.javaClass) {
            if (other.unit == unit) {
                Measurement(value * other.value, unit)
            } else {
                val lhsValueInTermsOfBase = unit.converter.baseUnitValue(value)
                val otherValueInTermsOfBase = other.unit.converter.baseUnitValue(other.value)
                Measurement(lhsValueInTermsOfBase * otherValueInTermsOfBase, unit.baseUnit())
            }
        } else {
            throw Exception("Attempt to multiply measurements with non-equal units")
        }
    }

    operator fun times(other: Double): Measurement<Dimension> = Measurement(value * other, unit)
    operator fun times(other: Float): Measurement<Dimension> = Measurement(value * other, unit)
    operator fun times(other: Int): Measurement<Dimension> = Measurement(value * other, unit)
    operator fun times(other: Long): Measurement<Dimension> = Measurement(value * other, unit)
    operator fun times(other: Short): Measurement<Dimension> = Measurement(value * other, unit)

    val abs get() = Measurement(value.absoluteValue, unit)
    // </editor-fold>

    val description get() = "$value ${unit.symbol}"

    fun formattedDescription(pattern: String = "#.##"): String {
        val formatter = DecimalFormat(pattern)
        formatter.roundingMode = RoundingMode.HALF_EVEN
        val formattedValue = formatter.format(value)

        return "$formattedValue ${unit.symbol}"
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun format(measureFormat: MeasureFormat = MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.SHORT)): String {
        if (unit.measureUnit == null) {
            return formattedDescription()
        }

        val measure = Measure(value, unit.measureUnit)
        return measureFormat.format(measure)
    }

    override fun toString(): String {
        return "\"measurement\": {\"value\": \"$value\", \"symbol\": \"${unit.symbol}\"}"
    }
}

val Measurement<UnitEnergy>.valueAsFloat get() = value.toFloat()