package com.stein.spacelunch.data.network

import com.google.gson.GsonBuilder
import com.stein.spacelunch.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                        .create()
                )
            )
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}