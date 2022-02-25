package com.example.coredemo.ui.measurements.calculations

import android.os.Build
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import be.appwise.core.ui.base.BaseViewModel
import be.appwise.measurements.Measurement
import be.appwise.measurements.units.Dimension
import be.appwise.measurements.units.UnitLength
import com.example.coredemo.ui.measurements.conversion.list

class MeasureCalculationViewModel : BaseViewModel() {

    // <editor-fold desc="length">
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
    // </editor-fold>

    // <editor-fold desc="length">
    val firstDivideValue = MutableLiveData("0")
    val secondDivideValue = MutableLiveData("0")
    val firstDivideUnit = MutableLiveData(UnitLength.list.first())
    val secondDivideUnit = MutableLiveData(Dimension.list.first())
    val convertDivideUnit = MutableLiveData(true)

    val divideUnitTotal = MediatorLiveData<String>().also { mediator ->
        mediator.addSource(firstDivideValue) {
            calculateDivide(it.toDoubleOrNull(), firstDivideUnit.value, secondDivideValue.value?.toDoubleOrNull(), secondDivideUnit.value, convertDivideUnit.value)
        }

        mediator.addSource(firstDivideUnit) {
            calculateDivide(firstDivideValue.value?.toDoubleOrNull(), it, secondDivideValue.value?.toDoubleOrNull(), secondDivideUnit.value, convertDivideUnit.value)
        }

        mediator.addSource(secondDivideValue) {
            calculateDivide(firstDivideValue.value?.toDoubleOrNull(), firstDivideUnit.value, it.toDoubleOrNull(), secondDivideUnit.value, convertDivideUnit.value)
        }

        mediator.addSource(secondDivideUnit) {
            calculateDivide(firstDivideValue.value?.toDoubleOrNull(), firstDivideUnit.value, secondDivideValue.value?.toDoubleOrNull(), it, convertDivideUnit.value)
        }

        mediator.addSource(convertDivideUnit) {
            calculateDivide(firstDivideValue.value?.toDoubleOrNull(), firstDivideUnit.value, secondDivideValue.value?.toDoubleOrNull(), secondDivideUnit.value, it)
        }
    }

    private fun calculateDivide(firstValue: Double?, firstUnitLength: UnitLength?, secondValue: Double?, secondUnitLength: Dimension?, convertDivideUnit: Boolean?) {
        if (firstValue != null && firstUnitLength != null && secondValue != null && secondUnitLength != null && convertDivideUnit != null) {
            val firstMeasure = Measurement(firstValue, firstUnitLength)
            val secondMeasure = Measurement(secondValue, secondUnitLength)

            // try to divide 2 units
            val addition = try {
                // in case the units are of the same type than the division will succeed
                firstMeasure / secondMeasure
            } catch (e: Exception) {
                // in case the units are Not of the same type, an exception will be thrown and shown to the user
                divideUnitTotal.value = e.message
                return
            }

            val converted = if (convertDivideUnit == true) {
                addition.converted(firstUnitLength)
            } else addition

            val desc = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                converted.format()
            } else {
                converted.formattedDescription()
            }
            divideUnitTotal.value = desc
        }
    }
    // </editor-fold>

}