package com.stein.spacelunch.data

import com.stein.spacelunch.data.local.database.UpcomingLocalDataSource
import com.stein.spacelunch.data.local.database.UpcomingModel
import com.stein.spacelunch.data.model.Upcoming
import com.stein.spacelunch.data.model.toUpcoming
import com.stein.spacelunch.data.network.UpcomingNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface UpcomingRepository {

    val upcomings: Flow<List<Upcoming>>

    suspend fun update()
}

class UpcomingRepositoryImpl @Inject constructor(
    private val upcomingNetworkDataSource: UpcomingNetworkDataSource,
    private val upcomingLocalDataSource: UpcomingLocalDataSource
) : UpcomingRepository {

    override val upcomings: Flow<List<Upcoming>> =
        upcomingLocalDataSource.getUpcomings().map { items -> items.map { it.toUpcoming() } }

    override suspend fun update() {
        upcomingNetworkDataSource.getUpcomings().collect { apiUpcomings ->
            upcomingLocalDataSource.updateUpcomings(apiUpcomings.results.map {
                UpcomingModel(
                    name = it.name,
                    statusName = it.status.name,
                    launchProvider = it.launchServiceProvider.name,
                    podLocation = it.pad.location?.name,
                    image = it.image,
                    windowEnd = it.windowEnd
                )
            })
        }
    }
}