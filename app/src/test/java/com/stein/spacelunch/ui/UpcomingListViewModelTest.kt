package com.stein.spacelunch.ui

import com.stein.spacelunch.MainDispatcherRule
import com.stein.spacelunch.data.UpcomingRepository
import com.stein.spacelunch.ui.upcoming_list.UpcomingListViewModel
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
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

    @Test
    fun whenLaunch_repositoryCallUpdate() = runTest {
        buildViewModel()

        coVerify { upcomingRepository.update(any()) }
    }

    private fun buildViewModel(): UpcomingListViewModel {
        return UpcomingListViewModel(upcomingRepository)
    }

}