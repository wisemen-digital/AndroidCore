package be.appwise.measurements.converters

open class UnitConverterReciprocal(reciprocal: Double) : UnitConverter() {

    var reciprocal: Double = reciprocal
        private set

    override fun baseUnitValue(value: Double): Double {
        return reciprocal / value
    }

    override fun value(baseUnitValue: Double): Double {
        return reciprocal / baseUnitValue
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UnitConverterReciprocal

        if (reciprocal != other.reciprocal) return false

        return true
    }

    override fun hashCode(): Int {
        return reciprocal.hashCode()
    }
}