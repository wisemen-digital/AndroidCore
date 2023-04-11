package com.example.coredemo.ui.validation

import androidx.lifecycle.map
import be.appwise.core.ui.base.BaseViewModel
import be.appwise.core.validation.validators.CompositeValidator

class ValidationViewModel : BaseViewModel() {

    val validator = CompositeValidator()
    val resultLive = validator.validResultLive.map { it.passed }
}