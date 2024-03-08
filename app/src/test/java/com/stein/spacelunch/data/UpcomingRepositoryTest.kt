package com.stein.spacelunch.data

import com.stein.spacelunch.data.local.UpcomingRepository
import com.stein.spacelunch.data.local.UpcomingRepositoryImpl
import com.stein.spacelunch.data.local.database.UpcomingLocalDataSource
import com.stein.spacelunch.data.local.database.UpcomingModel
import com.stein.spacelunch.data.network.UpcomingNetworkDataSource
import com.stein.spacelunch.data.network.model.ApiUpcoming
import com.stein.spacelunch.data.network.model.ApiUpcomings
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
        val fakeUpcomings =
            listOf(UpcomingModel("One"), UpcomingModel("Two"), UpcomingModel("Three"))
        coEvery { upcomingLocalDataSource.getUpcomings() } returns flowOf(fakeUpcomings)
        val repository = buildRepository()

        repository.upcomings.collect {
            assertEquals(fakeUpcomings.map { it.name }, it)
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
            results = listOf(ApiUpcoming("One"), ApiUpcoming("Two")),
        )
        coEvery { upcomingNetworkDataSource.getUpcomings() } returns flowOf(fakeUpcomings)
        val repository = buildRepository()

        repository.update()

        coVerify { upcomingLocalDataSource.updateUpcomings(fakeUpcomings.results.map { it.name }) }
    }

    private fun buildRepository(): UpcomingRepository {
        return UpcomingRepositoryImpl(upcomingNetworkDataSource, upcomingLocalDataSource)
    }
}