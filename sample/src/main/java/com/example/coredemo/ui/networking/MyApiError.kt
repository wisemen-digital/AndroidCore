package com.example.coredemo.ui.networking

import be.appwise.networking.model.ApiError
import com.google.gson.annotations.SerializedName

class MyApiError(
    @SerializedName("message2", alternate = ["message"])
    override val message: String,
    val exception: String
) : ApiError(message) {

    override fun parseErrorMessage(responseCode: Int?): String {
        return super.parseErrorMessage(responseCode) + " --- $exception"
    }
}