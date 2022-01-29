package be.appwise.measurements.units

import be.appwise.measurements.converters.UnitConverter
import be.appwise.measurements.converters.UnitConverterLinear

class UnitMass(symbol: String, converter: UnitConverter) : Dimension(symbol, converter) {

    private constructor(symbol: String, coefficient: Double) : this(symbol, UnitConverterLinear(coefficient))

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

    companion object {
        val kilograms = UnitMass(Symbol.kilograms, Coefficient.kilograms)
        val grams = UnitMass(Symbol.grams, Coefficient.grams)
        val decigrams = UnitMass(Symbol.decigrams, Coefficient.decigrams)
        val centigrams = UnitMass(Symbol.centigrams, Coefficient.centigrams)
        val milligrams = UnitMass(Symbol.milligrams, Coefficient.milligrams)
        val micrograms = UnitMass(Symbol.micrograms, Coefficient.micrograms)
        val nanograms = UnitMass(Symbol.nanograms, Coefficient.nanograms)
        val picograms = UnitMass(Symbol.picograms, Coefficient.picograms)
        val ounces = UnitMass(Symbol.ounces, Coefficient.ounces)
        val pounds = UnitMass(Symbol.pounds, Coefficient.pounds)
        val stones = UnitMass(Symbol.stones, Coefficient.stones)
        val metricTons = UnitMass(Symbol.metricTons, Coefficient.metricTons)
        val shortTons = UnitMass(Symbol.shortTons, Coefficient.shortTons)
        val carats = UnitMass(Symbol.carats, Coefficient.carats)
        val ouncesTroy = UnitMass(Symbol.ouncesTroy, Coefficient.ouncesTroy)
        val slugsval = UnitMass(Symbol.slugs, Coefficient.slugs)
    }

    override fun baseUnit() = kilograms
}