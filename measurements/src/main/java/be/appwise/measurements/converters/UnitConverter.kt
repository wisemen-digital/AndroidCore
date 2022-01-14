package be.appwise.measurements.converters

/**
 * UnitConverter describes how to convert a unit to and from the base unit of its dimension.
 * Use the UnitConverter protocol to implement new ways of converting a unit.
 */
open class UnitConverter {

    /**
     * The following methods perform conversions to and from the base unit of a unit class's dimension.
     * Each unit is defined against the base unit for the dimension to which the unit belongs.
     *
     * These methods are implemented differently depending on the type of conversion.
     * The default implementation in NSUnitConverter simply returns the value.
     *
     * These methods exist for the sole purpose of creating custom conversions for units in order to
     * support converting a value from one kind of unit to another in the same dimension.
     * NSUnitConverter is an abstract class that is meant to be subclassed.
     * There is no need to call these methods directly to do a conversion -- the correct way to convert
     * a measurement is to use [NSMeasurement measurementByConvertingToUnit:].
     * measurementByConvertingToUnit: uses the following 2 methods internally to perform the conversion.
     *
     * When creating a custom unit converter, you must override these two methods to implement the conversion to and from a
     * value in terms of a unit and the corresponding value in terms of the base unit of that unit's dimension in order for conversion to work correctly.
     */

    /**
     * This method takes a value in terms of a unit and returns the corresponding value in terms of the base unit of the original unit's dimension.
     * @param value Value in terms of the unit class
     * @return Value in terms of the base unit
     */
    open fun baseUnitValue(value: Double) = value

    /**
     * This method takes in a value in terms of the base unit of a unit's dimension and returns the equivalent value in terms of the unit.
     * @param baseUnitValue Value in terms of the base unit
     * @return Value in terms of the unit class
     */
    open fun value(baseUnitValue: Double) = baseUnitValue
}
