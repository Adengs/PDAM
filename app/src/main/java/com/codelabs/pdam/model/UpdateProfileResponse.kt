package com.codelabs.pdam.model
import com.google.gson.annotations.SerializedName


data class UpdateProfileResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status_code")
    var statusCode: Int?
) {
    data class Data(
        @SerializedName("created_at")
        var createdAt: String?,
        @SerializedName("deleted_at")
        var deletedAt: Any?,
        @SerializedName("email")
        var email: String?,
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