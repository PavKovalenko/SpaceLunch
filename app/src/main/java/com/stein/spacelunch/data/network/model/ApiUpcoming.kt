package com.stein.spacelunch.data.network.model

import com.google.gson.annotations.SerializedName

data class ApiUpcoming(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = ""
)