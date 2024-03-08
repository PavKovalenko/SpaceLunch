package com.stein.spacelunch.data.local

import com.stein.spacelunch.data.local.database.UpcomingLocalDataSource
import com.stein.spacelunch.data.network.UpcomingNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface UpcomingRepository {

    val upcomings: Flow<List<String>>

    suspend fun update()
}

class DefaultUpcomingRepository @Inject constructor(
    private val upcomingNetworkDataSource: UpcomingNetworkDataSource,
    private val upcomingLocalDataSource: UpcomingLocalDataSource
) : UpcomingRepository {

    override val upcomings: Flow<List<String>> =
        upcomingLocalDataSource.getUpcomings().map { items -> items.map { it.name } }

    override suspend fun update() {
        upcomingNetworkDataSource.getUpcomings().collect { apiUpcomings ->
            upcomingLocalDataSource.updateUpcomings(apiUpcomings.results.map { it.name })
        }
    }
}