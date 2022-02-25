package com.example.coredemo.ui.measurements.conversion

import be.appwise.measurements.units.Dimension
import be.appwise.measurements.units.UnitEnergy
import be.appwise.measurements.units.UnitLength
import be.appwise.measurements.units.UnitTemperature

val UnitEnergy.Companion.list
    get() = listOf(
        kilojoules,
        joules,
        kilocalories,
        calories,
        kilowattHours
    )

val Dimension.Companion.list
    get() = listOf(
        UnitLength.meters,
        UnitTemperature.kelvin,
        UnitLength.megameters
    )

val UnitLength.Companion.list
    get() = listOf(
        megameters,
        kilometers,
        hectometers,
        decameters,
        meters,
        decimeters,
        centimeters,
        millimeters,
        micrometers,
        nanometers,
        picometers,
        inches,
        feet,
        yards,
        miles,
        scandinavianMiles,
        lightyears,
        nauticalMiles,
        fathoms,
        furlongs,
        astronomicalUnits,
        parsecs
    )