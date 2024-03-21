package com.stein.spacelunch

import com.stein.spacelunch.data.local.database.UpcomingModel
import java.util.Date

val fakeUpcomings =
    listOf(
        UpcomingModel(
            id = "1",
            name = "One",
            statusName = "statusName_1",
            launchProvider = "launchProvider_1",
            padLocation = "padLocation_1",
            image = "image",
            windowEnd = Date(),
        ),
        UpcomingModel(
            id = "2",
            name = "Two",
            statusName = "statusName_2",
            launchProvider = "launchProvider_2",
            padLocation = "padLocation_2",
            image = "image",
            windowEnd = Date(),
        ),
        UpcomingModel(
            id = "3",
            name = "Three",
            statusName = "statusName_3",
            launchProvider = "launchProvider_3",
            padLocation = "padLocation_3",
            image = "image",
            windowEnd = Date(),
        ),
    )