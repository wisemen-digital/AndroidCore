package com.example.coredemo.ui.measurements.calculations

import android.os.Build
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import be.appwise.core.ui.base.BaseViewModel
import be.appwise.measurements.Measurement
import be.appwise.measurements.units.UnitLength
import com.example.coredemo.ui.measurements.conversion.list

class MeasureCalculationViewModel : BaseViewModel() {

    val firstLengthValue = MutableLiveData("0")
    val secondLengthValue = MutableLiveData("0")
    val firstLengthUnit = MutableLiveData(UnitLength.list.first())
    val secondLengthUnit = MutableLiveData(UnitLength.list.first())
    val convertUnit = MutableLiveData(true)

    val unitLengthTotal = MediatorLiveData<String>().also { mediator ->
        mediator.addSource(firstLengthValue) {
            calculateLength(it.toDoubleOrNull(), firstLengthUnit.value, secondLengthValue.value?.toDoubleOrNull(), secondLengthUnit.value, convertUnit.value)
        }

        mediator.addSource(firstLengthUnit) {
            calculateLength(firstLengthValue.value?.toDoubleOrNull(), it, secondLengthValue.value?.toDoubleOrNull(), secondLengthUnit.value, convertUnit.value)
        }

        mediator.addSource(secondLengthValue) {
            calculateLength(firstLengthValue.value?.toDoubleOrNull(), firstLengthUnit.value, it.toDoubleOrNull(), secondLengthUnit.value, convertUnit.value)
        }

        mediator.addSource(secondLengthUnit) {
            calculateLength(firstLengthValue.value?.toDoubleOrNull(), firstLengthUnit.value, secondLengthValue.value?.toDoubleOrNull(), it, convertUnit.value)
        }

        mediator.addSource(convertUnit) {
            calculateLength(firstLengthValue.value?.toDoubleOrNull(), firstLengthUnit.value, secondLengthValue.value?.toDoubleOrNull(), secondLengthUnit.value, it)
        }
    }

    private fun calculateLength(firstValue: Double?, firstUnitLength: UnitLength?, secondValue: Double?, secondUnitLength: UnitLength?, convertUnit: Boolean?) {
        if (firstValue != null && firstUnitLength != null && secondValue != null && secondUnitLength != null && convertUnit != null) {
            val firstMeasure = Measurement(firstValue, firstUnitLength)
            val secondMeasure = Measurement(secondValue, secondUnitLength)

            val addition = firstMeasure + secondMeasure
            val converted = if (convertUnit == true) {
                addition.converted(firstUnitLength)
            } else addition

            val desc = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                converted.format()
            } else {
                converted.formattedDescription()
            }
            unitLengthTotal.value = desc
        }
    }
}