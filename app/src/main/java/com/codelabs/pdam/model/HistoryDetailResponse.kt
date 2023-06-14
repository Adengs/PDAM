package com.codelabs.pdam.model
import com.google.gson.annotations.SerializedName


data class HistoryDetailResponse(
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
        var perPage: Int?,
        @SerializedName("prev_page_url")
        var prevPageUrl: Any?,
        @SerializedName("to")
        var to: Int?,
        @SerializedName("total")
        var total: Int?
    ) {
        data class Data(
            @SerializedName("billing_amount")
            var billingAmount: Int?,
            @SerializedName("created_at")
            var createdAt: String?,
            @SerializedName("created_by")
            var createdBy: Int?,
            @SerializedName("current_meter_number")
            var currentMeterNumber: Int?,
            @SerializedName("customer")
            var customer: Customer?,
            @SerializedName("customer_id")
            var customerId: Int?,
            @SerializedName("deleted_at")
            var deletedAt: Any?,
            @SerializedName("last_meter_number")
            var lastMeterNumber: Int?,
            @SerializedName("notes")
            var notes: String?,
            @SerializedName("payment")
            var payment: Payment?,
            @SerializedName("payment_id")
            var paymentId: Int?,
            @SerializedName("photo_location")
            var photoLocation: String?,
            @SerializedName("photo_stand_meter")
            var photoStandMeter: String?,
            @SerializedName("read_meter_id")
            var readMeterId: Int?,
            @SerializedName("stand_meter_usage")
            var standMeterUsage: String?,
            @SerializedName("status")
            var status: Int?,
            @SerializedName("updated_at")
            var updatedAt: String?,
            @SerializedName("updated_by")
            var updatedBy: Int?,
            @SerializedName("user_id")
            var userId: Int?
        ) {
            data class Customer(
                @SerializedName("address")
                var address: String?,
                @SerializedName("bank_account_name")
                var bankAccountName: String?,
                @SerializedName("bank_account_number")
                var bankAccountNumber: String?,
                @SerializedName("created_at")
                var createdAt: String?,
                @SerializedName("created_by")
                var createdBy: Int?,
                @SerializedName("customer_id")
                var customerId: Int?,
                @SerializedName("deleted_at")
                var deletedAt: Any?,
                @SerializedName("name")
                var name: String?,
                @SerializedName("nik")
                var nik: String?,
                @SerializedName("phone")
                var phone: String?,
                @SerializedName("updated_at")
                var updatedAt: String?,
                @SerializedName("updated_by")
                var updatedBy: Int?
            )

            data class Payment(
                @SerializedName("created_at")
                var createdAt: String?,
                @SerializedName("created_by")
                var createdBy: Int?,
                @SerializedName("deleted_at")
                var deletedAt: Any?,
                @SerializedName("payment_id")
                var paymentId: Int?,
                @SerializedName("photo")
                var photo: String?,
                @SerializedName("status")
                var status: Int?,
                @SerializedName("updated_at")
                var updatedAt: String?,
                @SerializedName("updated_by")
                var updatedBy: Int?
            )
        }

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