package com.stein.spacelunch.data.model

import com.stein.spacelunch.data.local.database.UpcomingModel
import java.util.Date

data class Upcoming(
    val name: String,
    val statusName: String,
    val launchProvider: String,
    val podLocation: String,
    val image: String,
    val windowEnd: Date,
)

fun Upcoming.toUpcomingModel(): UpcomingModel {
    return UpcomingModel(
        name = name,
        statusName = statusName,
        launchProvider = launchProvider,
        podLocation = podLocation,
        image = image,
        windowEnd = windowEnd
    )
}

fun UpcomingModel.toUpcoming(): Upcoming {
    return Upcoming(
        name = name,
        statusName = statusName,
        launchProvider = launchProvider,
        podLocation = podLocation ?: "-",
        image = image,
        windowEnd = windowEnd
    )
}