package com.example.coredemo.ui.measurements

import be.appwise.measurements.units.UnitEnergy
import be.appwise.measurements.units.UnitLength
import be.appwise.measurements.units.UnitPower
import be.appwise.measurements.units.UnitTemperature

val UnitPower.Companion.list
    get() = listOf(
        terawatts,
        gigawatts,
        megawatts,
        kilowatts,
        watts,
        milliwatts,
        microwatts,
        nanowatts,
        picowatts,
        femtowatts,
        horsepower
    )

val UnitEnergy.Companion.list
    get() = listOf(
        kilojoules,
        joules,
        kilocalories,
        calories,
        kilowattHours
    )

val UnitTemperature.Companion.list
    get() = listOf(
        kelvin,
        celsius,
        fahrenheit
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