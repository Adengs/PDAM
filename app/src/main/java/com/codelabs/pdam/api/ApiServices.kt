package com.codelabs.pdam.api

import com.codelabs.pdam.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @Multipart
    @POST("login")
    fun login(
        @PartMap body: Map<String, @JvmSuppressWildcards RequestBody>
    ): Call<LoginResponse>

    @GET("profile")
    fun getProfile(): Call<ProfileResponse>

    @Multipart
    @POST("profile")
    fun updateProfile(
        @PartMap body: Map<String, @JvmSuppressWildcards RequestBody?>
    ):Call<UpdateProfileResponse>

    @Multipart
    @POST("profile")
    fun updatePhoto(
        @Part file: MultipartBody.Part?
    ):Call<UpdateProfileResponse>

    @Multipart
    @POST("change-password")
    fun updatePassword(
        @PartMap body: Map<String, @JvmSuppressWildcards RequestBody>
    ): Call<UpdateResponse>

    @GET("read_meter")
    fun getList(
        @QueryMap param: Map<String, String>
    ): Call<ReadMeterResponse>

    @GET("read_meter/{id}")
    fun getDetailRM(
        @Path("id") id: String?
    ): Call<DetailReadMeterResponse>

    @Multipart
    @POST("read_meter/update")
    fun updateRM(
        @PartMap body: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part file : List<MultipartBody.Part?>?
    ): Call<UpdateDataResponse>

    @GET("about")
    fun getAbout(): Call<About>

    @GET("customer")
    fun getListCust(
        @QueryMap param: Map<String, String>
    ): Call<CustomerResponse>

    @GET("customer/{id}")
    fun getDetailCust(
        @Path("id") id: String?
    ): Call<CustomerDetailResponse>

    @POST("forgot-password")
    fun email(
        @Body request: EmailRequest
    ): Call<EmailVerificationResponse>

    @Multipart
    @POST("customer/complaint")
    fun complain(
        @PartMap body: Map<String, @JvmSuppressWildcards RequestBody>
    ): Call<ComplainResponse>

    @GET("province")
    fun getListProvince(
        @QueryMap param: Map<String, String>
    ): Call<ProvinceResponse>

    @GET("city")
    fun getListKabupaten(
        @QueryMap param: Map<String, String>
    ): Call<KabupatenResponse>

    @GET("district")
    fun getListKecamatan(
        @QueryMap param: Map<String, String>
    ): Call<KecamatanResponse>

    @GET("golongan")
    fun getListGolongan(
        @QueryMap param: Map<String, String>
    ): Call<GolonganResponse>

    @GET("ukuran")
    fun getListUkuran(
        @QueryMap param: Map<String, String>
    ): Call<UkuranResponse>

    @Multipart
    @POST("customer/installation")
    fun newInstallation(
        @PartMap body: Map<String, @JvmSuppressWildcards RequestBody>,
//        @Part file : List<MultipartBody.Part?>?
    ): Call<NewInstallationResponse>

    @GET("customer/history/{id}")
    fun getHistoryDetail(
        @Path("id") id: String?
    ): Call<HistoryDetailResponse>

    @Multipart
    @POST("read_meter/calculate")
    fun calculate(
        @PartMap body: Map<String, @JvmSuppressWildcards RequestBody>
    ): Call<CalculateResponse>
}