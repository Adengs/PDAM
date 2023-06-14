package com.codelabs.pdam.model
import com.google.gson.annotations.SerializedName


data class EmailVerificationResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status_code")
    var statusCode: Int?
) {
    data class Data(
        @SerializedName("code")
        var code: String?
    )
}