package be.appwise.measurements

import be.appwise.measurements.units.Dimension
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.absoluteValue

// Based this complete thing on swift
// https://github.com/apple/swift-corelibs-foundation/blob/main/Sources/Foundation/Unit.swift
// https://github.com/apple/swift-corelibs-foundation/blob/main/Sources/Foundation/Measurement.swift

class Measurement<UnitType : Dimension>(var value: Double, unit: UnitType) {

    var unit: UnitType = unit
        private set

    /**
     * Returns a new measurement created by converting to the specified unit.
     *
     * @param otherUnit: A unit of the same '[Dimension]'
     * @return A converted measurement
     */
    fun converted(otherUnit: UnitType): Measurement<UnitType> {
        return if (unit == otherUnit) {
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

    /**
     * Add two measurements of the same Dimension.
     *
     * If the [unit] of this object and [other] are [equals], then this returns the result of adding the [value] of each [Measurement].
     * If they are not equal, then this will convert both to the base unit of the [Dimension] and return the result as a [Measurement] of that base unit.
     * @return The result of adding the two measurements.
     */
    operator fun plus(other: Measurement<Dimension>): Measurement<Dimension> {
        return if (other.unit == unit) {
            Measurement(value + other.value, unit)
        } else {
            val lhsValueInTermsOfBase = unit.converter.baseUnitValue(value)
            val otherValueInTermsOfBase = other.unit.converter.baseUnitValue(other.value)
            Measurement(lhsValueInTermsOfBase + otherValueInTermsOfBase, unit.baseUnit())
        }
    }

    /**
     *
     *  Subtract two measurements of the same Dimension.
     *
     *  If the [unit] of this object and [other] are [equals], then this returns the result of subtracting the [value] of each [Measurement].
     *  If they are not equal, then this will convert both to the base unit of the [Dimension] and return the result as a [Measurement] of that base unit.
     *  @return The result of adding the two measurements.
     */
    operator fun minus(other: Measurement<*>): Measurement<*> {
        return if (other.unit == unit) {
            Measurement(value + other.value, unit)
        } else {
            val lhsValueInTermsOfBase = unit.converter.baseUnitValue(value)
            val otherValueInTermsOfBase = other.unit.converter.baseUnitValue(other.value)
            Measurement(lhsValueInTermsOfBase - otherValueInTermsOfBase, unit.baseUnit())
        }
    }

    val abs get() = Measurement(value.absoluteValue, unit)

    val description get() = "$value ${unit.symbol}"

    //TODO: maybe take a look into the `MeasureFormatter` native to kotlin/android,
    //  maybe we can interlock them so the format can be done with that API

    fun formattedDescription(pattern: String = "#.#"): String {

        val formatter = DecimalFormat(pattern)
        formatter.roundingMode = RoundingMode.HALF_EVEN
        val someVal = formatter.format(value)

        return "$someVal ${unit.symbol}"
    }

    fun prettyFormatValue(pattern: String = "#.#"): String {
        val formatter = DecimalFormat(pattern)
        formatter.roundingMode = RoundingMode.HALF_EVEN

        return formatter.format(value)
    }

    override fun toString(): String {
        return "\"measurement\": {\"value\": \"$value\", \"symbol\": \"${unit.symbol}\"}"
    }
}

fun Iterable<Measurement<*>>.sum(): Measurement<*> {
    val values = this.map { it.value }
    return Measurement(values.sum(), this.first().unit)
}