package com.stein.spacelunch.data.model

import com.stein.spacelunch.data.local.database.UpcomingModel
import com.stein.spacelunch.data.network.model.ApiUpcoming
import java.util.Date

data class Upcoming(
    val id: String,
    val name: String,
    val statusName: String,
    val launchProvider: String,
    val padLocation: String,
    val image: String,
    val windowEnd: Date,
)

fun Upcoming.toUpcomingModel(): UpcomingModel {
    return UpcomingModel(
        id = id,
        name = name,
        statusName = statusName,
        launchProvider = launchProvider,
        padLocation = padLocation,
        image = image,
        windowEnd = windowEnd
    )
}

fun UpcomingModel.toUpcoming(): Upcoming {
    return Upcoming(
        id = id,
        name = name,
        statusName = statusName,
        launchProvider = launchProvider,
        padLocation = padLocation ?: "-",
        image = image,
        windowEnd = windowEnd
    )
}

fun ApiUpcoming.toUpcoming(): Upcoming {
    return Upcoming(
        id = id,
        name = name,
        statusName = status.name,
        launchProvider = launchServiceProvider.name,
        padLocation = pad.location?.name ?: "-",
        image = image,
        windowEnd = windowEnd
    )
}


fun ApiUpcoming.toUpcomingModel(): UpcomingModel {
    return UpcomingModel(
        id = id,
        name = name,
        statusName = status.name,
        launchProvider = launchServiceProvider.name,
        padLocation = pad.location?.name ?: "-",
        image = image,
        windowEnd = windowEnd
    )
}