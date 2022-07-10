package com.example.coredemo.ui.networking

import be.appwise.networking.model.BaseApiError
import com.google.gson.annotations.SerializedName

data class MyApiError(
    @SerializedName("message", alternate = ["message2"])
    override var message: String = "",
    override var responseCode: Int?,
    val exception: String = ""
) : BaseApiError {
    override fun toString() = asString
}