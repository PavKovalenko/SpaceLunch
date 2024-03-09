package com.stein.spacelunch.ui

import com.stein.spacelunch.MainDispatcherRule
import com.stein.spacelunch.data.UpcomingRepository
import com.stein.spacelunch.ui.upcoming_list.UpcomingListUiState
import com.stein.spacelunch.ui.upcoming_list.UpcomingListViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class UpcomingListViewModelTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @RelaxedMockK
    lateinit var upcomingRepository: UpcomingRepository

    private val fakeUpcomings = listOf("One", "Two", "Three")

    @Test
    fun whenLaunch_repositoryCallUpdate() = runTest {
        buildViewModel()

        coVerify { upcomingRepository.update() }
    }

    @Test
    fun onStartShowLoading() = runTest {
        val viewModel = buildViewModel()

        assertEquals(UpcomingListUiState.Loading, viewModel.uiState.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun whenRepoReturnResponse_showIt() = runTest {
        val viewModel = buildViewModel()

        // Create an empty collector for the StateFlow
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect {}
        }

        assertEquals(UpcomingListUiState.Success(fakeUpcomings), viewModel.uiState.value)
    }

    private fun buildViewModel(): UpcomingListViewModel {
        coEvery { upcomingRepository.upcomings } returns flowOf(fakeUpcomings)
        return UpcomingListViewModel(upcomingRepository)
    }

}