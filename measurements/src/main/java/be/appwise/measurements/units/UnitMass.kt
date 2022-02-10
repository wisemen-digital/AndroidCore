package be.appwise.measurements.units

import android.icu.util.MeasureUnit
import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear
import be.appwise.measurements.isAtLeastO

class UnitMass(symbol: String, converter: UnitConverter, measureUnit: MeasureUnit? = null) : Dimension(symbol, converter, measureUnit) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

    private constructor(symbol: String, coefficient: Double, measureUnit: MeasureUnit?) : this(symbol, UnitConverterLinear(coefficient), measureUnit)

    private object Symbol {
        const val kilograms = "kg"
        const val grams = "g"
        const val decigrams = "dg"
        const val centigrams = "cg"
        const val milligrams = "mg"
        const val micrograms = "µg"
        const val nanograms = "ng"
        const val picograms = "pg"
        const val ounces = "oz"
        const val pounds = "lb"
        const val stones = "st"
        const val metricTons = "t"
        const val shortTons = "ton"
        const val carats = "ct"
        const val ouncesTroy = "oz t"
        const val slugs = "slug"
    }

    private object Coefficient {
        const val kilograms = 1.0
        const val grams = 1e-3
        const val decigrams = 1e-4
        const val centigrams = 1e-5
        const val milligrams = 1e-6
        const val micrograms = 1e-9
        const val nanograms = 1e-12
        const val picograms = 1e-15
        const val ounces = 0.0283495
        const val pounds = 0.453592
        const val stones = 0.157473
        const val metricTons = 1000.0
        const val shortTons = 907.185
        const val carats = 0.0002
        const val ouncesTroy = 0.03110348
        const val slugs = 14.5939
    }

    private object Unit {
        val kilograms = if (isAtLeastO) MeasureUnit.KILOGRAM else null
        val grams = if (isAtLeastO) MeasureUnit.GRAM else null
        val decigrams = null
        val centigrams = null
        val milligrams = if (isAtLeastO) MeasureUnit.MILLIGRAM else null
        val micrograms = if (isAtLeastO) MeasureUnit.MICROGRAM else null
        val nanograms = null
        val picograms = null
        val ounces = if (isAtLeastO) MeasureUnit.OUNCE else null
        val pounds = if (isAtLeastO) MeasureUnit.POUND else null
        val stones = if (isAtLeastO) MeasureUnit.STONE else null
        val metricTons = if (isAtLeastO) MeasureUnit.METRIC_TON else null
        val shortTons = null
        val carats = if (isAtLeastO) MeasureUnit.CARAT else null
        val ouncesTroy = if (isAtLeastO) MeasureUnit.OUNCE_TROY else null
        val slugs = null
    }

    companion object {
        val kilograms = UnitMass(Symbol.kilograms, Coefficient.kilograms, Unit.kilograms)
        val grams = UnitMass(Symbol.grams, Coefficient.grams, Unit.grams)
        val decigrams = UnitMass(Symbol.decigrams, Coefficient.decigrams, Unit.decigrams)
        val centigrams = UnitMass(Symbol.centigrams, Coefficient.centigrams, Unit.centigrams)
        val milligrams = UnitMass(Symbol.milligrams, Coefficient.milligrams, Unit.milligrams)
        val micrograms = UnitMass(Symbol.micrograms, Coefficient.micrograms, Unit.micrograms)
        val nanograms = UnitMass(Symbol.nanograms, Coefficient.nanograms, Unit.nanograms)
        val picograms = UnitMass(Symbol.picograms, Coefficient.picograms, Unit.picograms)
        val ounces = UnitMass(Symbol.ounces, Coefficient.ounces, Unit.ounces)
        val pounds = UnitMass(Symbol.pounds, Coefficient.pounds, Unit.pounds)
        val stones = UnitMass(Symbol.stones, Coefficient.stones, Unit.stones)
        val metricTons = UnitMass(Symbol.metricTons, Coefficient.metricTons, Unit.metricTons)
        val shortTons = UnitMass(Symbol.shortTons, Coefficient.shortTons, Unit.shortTons)
        val carats = UnitMass(Symbol.carats, Coefficient.carats, Unit.carats)
        val ouncesTroy = UnitMass(Symbol.ouncesTroy, Coefficient.ouncesTroy, Unit.ouncesTroy)
        val slugsval = UnitMass(Symbol.slugs, Coefficient.slugs, Unit.slugs)
    }

    override fun baseUnit() = kilograms
}