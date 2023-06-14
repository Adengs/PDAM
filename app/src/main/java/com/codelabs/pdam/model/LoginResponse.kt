package com.codelabs.pdam.model
import com.google.gson.annotations.SerializedName


data class LoginResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status_code")
    var statusCode: Int?
) {
    data class Data(
        @SerializedName("current_date")
        var currentDate: String?,
        @SerializedName("token")
        var token: String?,
        @SerializedName("user")
        var user: User?
    ) {
        data class User(
            @SerializedName("company")
            var company: Company?,
            @SerializedName("company_id")
            var companyId: Int?,
            @SerializedName("created_at")
            var createdAt: String?,
            @SerializedName("deleted_at")
            var deletedAt: Any?,
            @SerializedName("district")
            var district: District?,
            @SerializedName("district_id")
            var districtId: Int?,
            @SerializedName("email")
            var email: String?,
            @SerializedName("forgot_password_code")
            var forgotPasswordCode: String?,
            @SerializedName("profile")
            var profile: Profile?,
            @SerializedName("role")
            var role: Role?,
            @SerializedName("role_id")
            var roleId: Int?,
            @SerializedName("updated_at")
            var updatedAt: String?,
            @SerializedName("user_id")
            var userId: Int?,
            @SerializedName("username")
            var username: String?
        ) {
            data class Company(
                @SerializedName("about_id")
                var aboutId: Int?,
                @SerializedName("city")
                var city: City?,
                @SerializedName("city_id")
                var cityId: Int?,
                @SerializedName("code")
                var code: String?,
                @SerializedName("company_id")
                var companyId: Int?,
                @SerializedName("created_at")
                var createdAt: String?,
                @SerializedName("created_by")
                var createdBy: Int?,
                @SerializedName("deleted_at")
                var deletedAt: Any?,
                @SerializedName("district_id")
                var districtId: Any?,
                @SerializedName("name")
                var name: String?,
                @SerializedName("province")
                var province: Province?,
                @SerializedName("province_id")
                var provinceId: Int?,
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

            data class Profile(
                @SerializedName("created_at")
                var createdAt: String?,
                @SerializedName("deleted_at")
                var deletedAt: Any?,
                @SerializedName("name")
                var name: String?,
                @SerializedName("phone")
                var phone: String?,
                @SerializedName("photo")
                var photo: String?,
                @SerializedName("updated_at")
                var updatedAt: String?,
                @SerializedName("user_id")
                var userId: Int?,
                @SerializedName("user_profile_id")
                var userProfileId: Int?
            )

            data class Role(
                @SerializedName("created_at")
                var createdAt: String?,
                @SerializedName("created_by")
                var createdBy: String?,
                @SerializedName("deleted_at")
                var deletedAt: Any?,
                @SerializedName("description")
                var description: String?,
                @SerializedName("name")
                var name: String?,
                @SerializedName("role_id")
                var roleId: Int?,
                @SerializedName("updated_at")
                var updatedAt: String?,
                @SerializedName("updated_by")
                var updatedBy: String?
            )
        }
    }
}