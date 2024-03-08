package com.stein.spacelunch.data.network

import com.stein.spacelunch.data.network.model.ApiUpcoming
import com.stein.spacelunch.data.network.model.ApiUpcomings
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("launch/upcoming")
    suspend fun getUpcomings(): ApiUpcomings

    @GET("/launch/upcoming/{id}")
    suspend fun getUpcomingDetails(@Path("id") id: String): ApiUpcoming
}