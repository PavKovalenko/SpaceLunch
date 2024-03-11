package com.stein.spacelunch.data.network.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ApiUpcoming(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("image")
    val image: String = "",
    @SerializedName("status")
    val status: Status,
    @SerializedName("launch_service_provider")
    val launchServiceProvider: LaunchServiceProvider,
    @SerializedName("pad")
    val pad: Pad,
    @SerializedName("window_end")
    val windowEnd: Date,
)

data class Status(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String = ""
)

data class LaunchServiceProvider(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String = ""
)

data class Pad(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("location")
    val location: Location?,
)

data class Location(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String = ""
)