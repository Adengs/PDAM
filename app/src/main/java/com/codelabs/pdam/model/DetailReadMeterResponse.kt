package com.codelabs.pdam.model
import android.net.Uri
import com.google.gson.annotations.SerializedName
import java.io.File


data class DetailReadMeterResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status_code")
    var statusCode: Int?
) {
    data class Data(
        var imageFileL : File? = null,
        var imagePathL : Uri? = null,
        var imageFileM : File? = null,
        var imagePathM : Uri? = null,
        @SerializedName("billing_amount")
        var billingAmount: Int? = null,
        @SerializedName("created_at")
        var createdAt: String? = null,
        @SerializedName("created_by")
        var createdBy: Int? = null,
        @SerializedName("current_meter_number")
        var currentMeterNumber: Int? = null,
        @SerializedName("customer")
        var customer: Customer? = null,
        @SerializedName("customer_id")
        var customerId: Int? = null,
        @SerializedName("deleted_at")
        var deletedAt: Any? = null,
        @SerializedName("last_meter_number")
        var lastMeterNumber: Int? = null,
        @SerializedName("notes")
        var notes: String? = null,
        @SerializedName("payment_id")
        var paymentId: Int? = null,
        @SerializedName("photo_location")
        var photoLocation: List<String?>? = null,
        @SerializedName("photo_stand_meter")
        var photoStandMeter: List<String?>? = null,
        @SerializedName("read_meter_id")
        var readMeterId: Int? = null,
        @SerializedName("stand_meter_usage")
        var standMeterUsage: String? = null,
        @SerializedName("status")
        var status: Int? = null,
        @SerializedName("updated_at")
        var updatedAt: String? = null,
        @SerializedName("updated_by")
        var updatedBy: Int? = null,
        @SerializedName("user")
        var user: User? = null,
        @SerializedName("user_id")
        var userId: Int? = null
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