package com.codelabs.pdam.model
import com.google.gson.annotations.SerializedName


data class CustomerDetailResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status_code")
    var statusCode: Int?
) {
    data class Data(
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
        @SerializedName("installation")
        var installation: Installation?,
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
    ) {
        data class Installation(
            @SerializedName("address")
            var address: String?,
            @SerializedName("city")
            var city: City?,
            @SerializedName("city_id")
            var cityId: Int?,
            @SerializedName("created_at")
            var createdAt: String?,
            @SerializedName("created_by")
            var createdBy: Int?,
            @SerializedName("customer_id")
            var customerId: Int?,
            @SerializedName("deleted_at")
            var deletedAt: Any?,
            @SerializedName("district")
            var district: District?,
            @SerializedName("district_id")
            var districtId: Int?,
            @SerializedName("document")
            var document: Document?,
            @SerializedName("document_id")
            var documentId: Int?,
            @SerializedName("installation_approval_id")
            var installationApprovalId: Int?,
            @SerializedName("installation_closing_id")
            var installationClosingId: Int?,
            @SerializedName("installation_id")
            var installationId: Int?,
            @SerializedName("land")
            var land: Land?,
            @SerializedName("land_id")
            var landId: Int?,
            @SerializedName("postal_code")
            var postalCode: String?,
            @SerializedName("province")
            var province: Province?,
            @SerializedName("province_id")
            var provinceId: Int?,
            @SerializedName("status")
            var status: Int?,
            @SerializedName("updated_at")
            var updatedAt: String?,
            @SerializedName("updated_by")
            var updatedBy: Int?
        ) {
            data class City(
                @SerializedName("city_id")
                var cityId: Int?,
                @SerializedName("code")
                var code: String?,
                @SerializedName("created_at")
                var createdAt: String?,
                @SerializedName("deleted_at")
                var deletedAt: Any?,
                @SerializedName("name")
                var name: String?,
                @SerializedName("province_id")
                var provinceId: Int?,
                @SerializedName("updated_at")
                var updatedAt: String?
            )

            data class District(
                @SerializedName("city_id")
                var cityId: Int?,
                @SerializedName("code")
                var code: String?,
                @SerializedName("created_at")
                var createdAt: String?,
                @SerializedName("deleted_at")
                var deletedAt: Any?,
                @SerializedName("district_id")
                var districtId: Int?,
                @SerializedName("name")
                var name: String?,
                @SerializedName("updated_at")
                var updatedAt: String?
            )

            data class Document(
                @SerializedName("created_at")
                var createdAt: String?,
                @SerializedName("created_by")
                var createdBy: Int?,
                @SerializedName("deleted_at")
                var deletedAt: Any?,
                @SerializedName("document_id")
                var documentId: Int?,
                @SerializedName("electricity_account")
                var electricityAccount: String?,
                @SerializedName("ksk")
                var ksk: String?,
                @SerializedName("ktp")
                var ktp: String?,
                @SerializedName("land_certificate")
                var landCertificate: String?,
                @SerializedName("pbb")
                var pbb: String?,
                @SerializedName("photo_building")
                var photoBuilding: String?,
                @SerializedName("updated_at")
                var updatedAt: String?,
                @SerializedName("updated_by")
                var updatedBy: Int?
            )

            data class Land(
                @SerializedName("building_area")
                var buildingArea: Int?,
                @SerializedName("building_breadth")
                var buildingBreadth: Int?,
                @SerializedName("building_length")
                var buildingLength: Int?,
                @SerializedName("created_at")
                var createdAt: String?,
                @SerializedName("created_by")
                var createdBy: Int?,
                @SerializedName("deleted_at")
                var deletedAt: Any?,
                @SerializedName("land_area")
                var landArea: Int?,
                @SerializedName("land_breadth")
                var landBreadth: Int?,
                @SerializedName("land_id")
                var landId: Int?,
                @SerializedName("land_length")
                var landLength: Int?,
                @SerializedName("status")
                var status: Int?,
                @SerializedName("updated_at")
                var updatedAt: String?,
                @SerializedName("updated_by")
                var updatedBy: Int?
            )

            data class Province(
                @SerializedName("code")
                var code: String?,
                @SerializedName("created_at")
                var createdAt: String?,
                @SerializedName("deleted_at")
                var deletedAt: Any?,
                @SerializedName("name")
                var name: String?,
                @SerializedName("province_id")
                var provinceId: Int?,
                @SerializedName("updated_at")
                var updatedAt: String?
            )
        }
    }
}