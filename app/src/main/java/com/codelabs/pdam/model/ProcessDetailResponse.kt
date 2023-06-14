package com.codelabs.pdam.model
import com.google.gson.annotations.SerializedName


data class ProcessDetailResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status_code")
    var statusCode: Int?
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
        @SerializedName("payment_id")
        var paymentId: Int?,
        @SerializedName("photo_location")
        var photoLocation: List<Any?>?,
        @SerializedName("photo_stand_meter")
        var photoStandMeter: List<Any?>?,
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
        @SerializedName("user")
        var user: User?,
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
            @SerializedName("phone")
            var phone: String?,
            @SerializedName("updated_at")
            var updatedAt: String?,
            @SerializedName("updated_by")
            var updatedBy: Int?
        )

        data class User(
            @SerializedName("created_at")
            var createdAt: String?,
            @SerializedName("deleted_at")
            var deletedAt: Any?,
            @SerializedName("email")
            var email: String?,
            @SerializedName("forgot_password_code")
            var forgotPasswordCode: String?,
            @SerializedName("role_id")
            var roleId: Int?,
            @SerializedName("updated_at")
            var updatedAt: String?,
            @SerializedName("user_id")
            var userId: Int?,
            @SerializedName("username")
            var username: String?
        )
    }
}