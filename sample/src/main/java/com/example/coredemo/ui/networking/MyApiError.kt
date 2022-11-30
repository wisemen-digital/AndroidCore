package com.example.coredemo.ui.networking

import be.appwise.networking.model.BaseApiError
import be.appwise.networking.model.BaseErrorBody

data class MyApiError(
    override var status: Int?,
    override val error: String?,
    override val error_description: String?,
    override val message: String?,
    override val errors: List<BaseErrorBody>?
) : BaseApiError {
    override fun toString() = asString
}