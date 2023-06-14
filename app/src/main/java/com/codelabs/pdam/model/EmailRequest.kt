package com.codelabs.pdam.model
import com.google.gson.annotations.SerializedName


data class EmailRequest(
    @SerializedName("email")
    var email: String?
)