package com.codelabs.pdam.model
import com.google.gson.annotations.SerializedName


data class About(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status_code")
    var statusCode: Int?
) {
    data class Data(
        @SerializedName("about_id")
        var aboutId: Int?,
        @SerializedName("created_at")
        var createdAt: String?,
        @SerializedName("created_by")
        var createdBy: Int?,
        @SerializedName("deleted_at")
        var deletedAt: Any?,
        @SerializedName("description")
        var description: String?,
        @SerializedName("price_per_meter")
        var pricePerMeter: Int?,
        @SerializedName("title")
        var title: String?,
        @SerializedName("updated_at")
        var updatedAt: String?,
        @SerializedName("updated_by")
        var updatedBy: Int?
    )
}