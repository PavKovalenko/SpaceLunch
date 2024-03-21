package com.stein.spacelunch.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.stein.spacelunch.data.local.database.UpcomingLocalDataSource
import com.stein.spacelunch.data.local.database.UpcomingModel
import com.stein.spacelunch.data.model.Upcoming
import com.stein.spacelunch.data.model.toUpcoming
import com.stein.spacelunch.data.model.toUpcomingModel
import com.stein.spacelunch.data.network.UpcomingNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

interface UpcomingRepository {

    fun getUpcomingStream(): Flow<PagingData<Upcoming>>

    fun upcomingDetails(upcomingId: String): Flow<Upcoming>

    suspend fun update(onFailure: (Throwable) -> Unit = {})
}

class UpcomingRepositoryImpl @Inject constructor(
    private val upcomingNetworkDataSource: UpcomingNetworkDataSource,
    private val upcomingLocalDataSource: UpcomingLocalDataSource,
    private val upcomingRemoteMediator: UpcomingRemoteMediator,
) : UpcomingRepository {

    override fun getUpcomingStream(): Flow<PagingData<Upcoming>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = upcomingRemoteMediator
        ) {
            upcomingLocalDataSource.getUpcomingsPagingSource()
        }.flow
            .map { pagingData -> pagingData.map { it.toUpcoming() } }
    }

    override fun upcomingDetails(upcomingId: String): Flow<Upcoming> {
        upcomingNetworkDataSource.getUpcomingDetails(upcomingId)
            .catch {
                it.printStackTrace()
            }.onEach {
                upcomingLocalDataSource.updateUpcoming(it.toUpcomingModel())
            }

        return upcomingLocalDataSource.getUpcoming(upcomingId)
            .map { it.toUpcoming() }
    }



    override suspend fun update(onFailure: (Throwable) -> Unit) {
        upcomingNetworkDataSource.getUpcomings()
            .catch {
                onFailure.invoke(it)
            }
            .collect { apiUpcomings ->
                upcomingLocalDataSource.updateUpcomings(apiUpcomings.results.map {
                    UpcomingModel(
                        id = it.id,
                        name = it.name,
                        statusName = it.status.name,
                        launchProvider = it.launchServiceProvider.name,
                        padLocation = it.pad.location?.name,
                        image = it.image,
                        windowEnd = it.windowEnd
                    )
                })
            }
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}