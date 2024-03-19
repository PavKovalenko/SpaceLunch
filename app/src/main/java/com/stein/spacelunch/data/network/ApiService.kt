package com.stein.spacelunch.data.network

import com.stein.spacelunch.data.network.model.ApiUpcoming
import com.stein.spacelunch.data.network.model.ApiUpcomings
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("launch/upcoming")
    suspend fun getUpcomings(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 10
    ): ApiUpcomings

    @GET("/launch/upcoming/{id}")
    suspend fun getUpcomingDetails(@Path("id") id: String): ApiUpcoming
}