package com.codelabs.pdam.model
import com.google.gson.annotations.SerializedName


data class UpdateResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status_code")
    var statusCode: Int?
) {
    class Data
}