package com.codelabs.pdam.model
import com.google.gson.annotations.SerializedName


data class ComplainResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status_code")
    var statusCode: Int?
) {
    data class Data(
        @SerializedName("complaint_id")
        var complaintId: Int?,
        @SerializedName("created_at")
        var createdAt: String?,
        @SerializedName("created_by")
        var createdBy: Int?,
        @SerializedName("customer")
        var customer: Customer?,
        @SerializedName("customer_id")
        var customerId: Int?,
        @SerializedName("deleted_at")
        var deletedAt: Any?,
        @SerializedName("description")
        var description: String?,
        @SerializedName("subject")
        var subject: String?,
        @SerializedName("updated_at")
        var updatedAt: String?,
        @SerializedName("updated_by")
        var updatedBy: Int?
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
    }
}