package com.codelabs.pdam.api

import android.content.Context
import com.codelabs.pdam.helper.SharedPreference
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    const val BASE_URL = "http://178.128.90.130/pdam/cms/api/"

    fun getClient(context: Context): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val sph = SharedPreference(context)
        var header : Interceptor = LoginInterceptor(sph)

        val client : OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(header)
            .addInterceptor(interceptor)
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

    }

    fun instanceRetrofit (context: Context): ApiServices {
        return getClient(context).create(ApiServices::class.java)
    }

    class LoginInterceptor (val sph : SharedPreference) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response = chain.run {
            proceed(
                request()
                    .newBuilder()
//                    .addHeader("Content-Type", "application/json")
//                    .addHeader("AppToken", "0ZB3PmBPxJrXxkzgdGTQxTj413jx6hHRbMK6qpdO")
                    .addHeader("Authorization", "" + sph.fetchAuthToken())
                    .build()
            )
        }
    }
}