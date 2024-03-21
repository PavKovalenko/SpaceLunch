package com.stein.spacelunch

import com.stein.spacelunch.data.local.database.UpcomingModel
import java.util.Date

val fakeUpcomings =
    listOf(
        UpcomingModel(
            id = "1",
            name = "One",
            statusName = "statusName",
            launchProvider = "launchProvider",
            padLocation = "podLocation",
            image = "image",
            windowEnd = Date(),
        ),
        UpcomingModel(
            id = "2",
            name = "Two",
            statusName = "statusName",
            launchProvider = "launchProvider",
            padLocation = "podLocation",
            image = "image",
            windowEnd = Date(),
        ),
        UpcomingModel(
            id = "3",
            name = "Three",
            statusName = "statusName",
            launchProvider = "launchProvider",
            padLocation = "podLocation",
            image = "image",
            windowEnd = Date(),
        )
    )