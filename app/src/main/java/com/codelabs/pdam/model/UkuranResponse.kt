package com.codelabs.pdam.model
import com.google.gson.annotations.SerializedName


data class UkuranResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status_code")
    var statusCode: Int?
) {
    data class Data(
        @SerializedName("current_page")
        var currentPage: Int?,
        @SerializedName("data")
        var `data`: List<Data?>?,
        @SerializedName("first_page_url")
        var firstPageUrl: String?,
        @SerializedName("from")
        var from: Int?,
        @SerializedName("last_page")
        var lastPage: Int?,
        @SerializedName("last_page_url")
        var lastPageUrl: String?,
        @SerializedName("links")
        var links: List<Link?>?,
        @SerializedName("next_page_url")
        var nextPageUrl: Any?,
        @SerializedName("path")
        var path: String?,
        @SerializedName("per_page")
        var perPage: String?,
        @SerializedName("prev_page_url")
        var prevPageUrl: Any?,
        @SerializedName("to")
        var to: Int?,
        @SerializedName("total")
        var total: Int?
    ) {
        data class Data(
            @SerializedName("company_id")
            var companyId: Int?,
            @SerializedName("created_at")
            var createdAt: String?,
            @SerializedName("created_by")
            var createdBy: Int?,
            @SerializedName("deleted_at")
            var deletedAt: Any?,
            @SerializedName("size")
            var size: Int?,
            @SerializedName("tariff")
            var tariff: String?,
            @SerializedName("ukuran_meter_id")
            var ukuranMeterId: Int?,
            @SerializedName("unit")
            var unit: String?,
            @SerializedName("updated_at")
            var updatedAt: String?,
            @SerializedName("updated_by")
            var updatedBy: Int?
        )

        data class Link(
            @SerializedName("active")
            var active: Boolean?,
            @SerializedName("label")
            var label: String?,
            @SerializedName("url")
            var url: Any?
        )
    }
}