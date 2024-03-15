package com.stein.spacelunch

import com.stein.spacelunch.data.local.database.UpcomingModel
import java.util.Date

val fakeUpcomings =
    listOf(
        UpcomingModel(
            name = "One",
            statusName = "statusName_1",
            launchProvider = "launchProvider_1",
            podLocation = "podLocation_1",
            image = "image",
            windowEnd = Date(),
        ),
        UpcomingModel(
            name = "Two",
            statusName = "statusName_2",
            launchProvider = "launchProvider_2",
            podLocation = "podLocation_2",
            image = "image",
            windowEnd = Date(),
        ),
        UpcomingModel(
            name = "Three",
            statusName = "statusName_3",
            launchProvider = "launchProvider_3",
            podLocation = "podLocation_3",
            image = "image",
            windowEnd = Date(),
        )
    )