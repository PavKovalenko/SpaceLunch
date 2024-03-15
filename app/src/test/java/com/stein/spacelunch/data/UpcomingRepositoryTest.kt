package com.stein.spacelunch.data

import com.stein.spacelunch.data.local.database.UpcomingLocalDataSource
import com.stein.spacelunch.data.local.database.UpcomingModel
import com.stein.spacelunch.data.model.toUpcoming
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
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
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

    @Test
    fun whenCallUpcomings_thenLocalRepositoryInvoked() {
        val repository = buildRepository()

        repository.upcomings

        verify { upcomingLocalDataSource.getUpcomings() }
    }

    @Test
    fun whenCallUpcomings_thenNetworkRepositoryNoInteraction() {
        val repository = buildRepository()

        repository.upcomings

        verify { upcomingNetworkDataSource wasNot Called }
    }

    @Test
    fun whenUpcomingsReturnList_thenMapAndReturnItCorrectly() = runTest {
        coEvery { upcomingLocalDataSource.getUpcomings() } returns flowOf(fakeUpcomings)
        val repository = buildRepository()

        repository.upcomings.collect { upcomings ->
            assertEquals(fakeUpcomings.map { it.toUpcoming() }, upcomings)
        }
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

    private fun buildRepository(): UpcomingRepository {
        return UpcomingRepositoryImpl(upcomingNetworkDataSource, upcomingLocalDataSource)
    }
}