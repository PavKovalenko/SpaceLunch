package com.stein.spacelunch.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.stein.spacelunch.data.local.database.AppDatabase
import com.stein.spacelunch.data.local.database.UpcomingLocalDataSource
import com.stein.spacelunch.data.local.database.UpcomingModel
import com.stein.spacelunch.data.local.database.UpcomingModelDao
import com.stein.spacelunch.data.network.UpcomingNetworkDataSource
import com.stein.spacelunch.data.network.model.ApiUpcoming
import com.stein.spacelunch.data.network.model.ApiUpcomings
import com.stein.spacelunch.data.network.model.LaunchServiceProvider
import com.stein.spacelunch.data.network.model.Pad
import com.stein.spacelunch.data.network.model.Status
import com.stein.spacelunch.fakeUpcomings
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.util.Date

class UpcomingRepositoryTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    lateinit var upcomingNetworkDataSource: UpcomingNetworkDataSource

    @RelaxedMockK
    lateinit var upcomingLocalDataSource: UpcomingLocalDataSource

    @RelaxedMockK
    lateinit var upcomingRemoteMediator: UpcomingRemoteMediator

    @RelaxedMockK
    lateinit var database: AppDatabase

    @RelaxedMockK
    lateinit var upcomingModelDao: UpcomingModelDao

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun whenCallUpcomings_thenDatabaseUpcomingModelDaoInvoked() = runTest {
        val repository = buildRepository()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            repository.getUpcomingStream().collect {}
        }

        verify { database.upcomingModelDao() }
    }

    @Test
    fun whenCallUpcomings_thenNetworkRepositoryNoInteraction() {
        val repository = buildRepository()

        repository.getUpcomingStream()

        verify { upcomingNetworkDataSource wasNot Called }
    }

    @Test
    fun whenUpdateInvoked_thenNetworkRepositoryInvoked() = runTest {
        val repository = buildRepository()

        repository.update()

        verify { upcomingNetworkDataSource.getUpcomings() }
    }

    @Test
    fun whenUpdateReturnResponse_thenSaveItToLocalDataSource() = runTest {
        val fakeUpcomings = ApiUpcomings(
            id = 1,
            next = "next",
            previous = "previous",
            results = listOf(
                ApiUpcoming(
                    name = "One",
                    status = Status(),
                    launchServiceProvider = LaunchServiceProvider(),
                    pad = Pad(),
                    windowEnd = Date(),
                ),
                ApiUpcoming(
                    name = "Two",
                    status = Status(),
                    launchServiceProvider = LaunchServiceProvider(),
                    pad = Pad(),
                    windowEnd = Date(),
                )
            ),
        )
        coEvery { upcomingNetworkDataSource.getUpcomings() } returns flowOf(fakeUpcomings)
        val repository = buildRepository()

        repository.update()

        coVerify {
            upcomingLocalDataSource.updateUpcomings(fakeUpcomings.results.map {
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

    @OptIn(ExperimentalPagingApi::class)
    private fun buildRepository(): UpcomingRepository {
        every { upcomingModelDao.getUpcomingsPagingSource() } returns FakeUpcomingPagingSource()
        every { database.upcomingModelDao() } returns upcomingModelDao
        coEvery {
            upcomingRemoteMediator.load(
                any(),
                any()
            )
        } returns RemoteMediator.MediatorResult.Success(true)

        return UpcomingRepositoryImpl(
            upcomingNetworkDataSource,
            upcomingLocalDataSource,
            upcomingRemoteMediator,
            database
        )
    }

    private class FakeUpcomingPagingSource : PagingSource<Int, UpcomingModel>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UpcomingModel> {
            return LoadResult.Page(
                data = fakeUpcomings,
                prevKey = null,
                nextKey = null
            )
        }

        override fun getRefreshKey(state: PagingState<Int, UpcomingModel>): Int? {
            TODO("Not yet implemented")
        }
    }
}