package com.stein.spacelunch

import com.stein.spacelunch.data.local.database.UpcomingModel
import java.util.Date

val fakeUpcomings =
    listOf(
        UpcomingModel(
            name = "One",
            statusName = "statusName",
            launchProvider = "launchProvider",
            podLocation = "podLocation",
            image = "image",
            windowEnd = Date(),
        ),
        UpcomingModel(
            name = "Two",
            statusName = "statusName",
            launchProvider = "launchProvider",
            podLocation = "podLocation",
            image = "image",
            windowEnd = Date(),
        ),
        UpcomingModel(
            name = "Three",
            statusName = "statusName",
            launchProvider = "launchProvider",
            podLocation = "podLocation",
            image = "image",
            windowEnd = Date(),
        )
    )