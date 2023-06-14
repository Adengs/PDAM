package com.codelabs.pdam.model
import com.google.gson.annotations.SerializedName


data class LoginFailledResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status_code")
    var statusCode: Int?
) {
    data class Data(
        @SerializedName("email")
        var email: List<String?>?
    )
}