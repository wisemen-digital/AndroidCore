package be.appwise.measurements.units

import be.appwise.measurements.converters.UnitConverter

open class Dimension(var symbol: String, var converter: UnitConverter) : Unit(symbol) {

    open fun baseUnit(): Dimension {
        throw Exception("*** You must override baseUnit in your class to define its base unit.")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Dimension) return false
        if (!super.equals(other)) return false

        if (converter != other.converter) return false
        if (symbol != other.symbol) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + converter.hashCode()
        result = 31 * result + symbol.hashCode()
        return result
    }
}
