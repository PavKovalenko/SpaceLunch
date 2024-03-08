package com.stein.spacelunch.data.network.model

import com.google.gson.annotations.SerializedName

data class ApiUpcomings(
    @SerializedName("count")
    val id: Int = 0,
    @SerializedName("next")
    val next: String = "",
    @SerializedName("previous")
    val previous: String = "",
    @SerializedName("results")
    val results: List<ApiUpcoming> = listOf()
)