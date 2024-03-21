package com.stein.spacelunch.ui.upcoming_details

import androidx.lifecycle.ViewModel
import com.stein.spacelunch.data.UpcomingRepository
import com.stein.spacelunch.data.model.Upcoming
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

@HiltViewModel(assistedFactory = UpcomingDetailsViewModel.UpcomingDetailsViewModelFactory::class)
class UpcomingDetailsViewModel @AssistedInject constructor(
    upcomingRepository: UpcomingRepository,
    @Assisted private val upcomingId: String
) : ViewModel() {


    val upcomingDetailsState: Flow<UpcomingDetailsState> = upcomingRepository
        .upcomingDetails(upcomingId = upcomingId)
        .onStart { UpcomingDetailsState.Loading }
        .catch { UpcomingDetailsState.Failed }
        .map { UpcomingDetailsState.Result(it) }


    @AssistedFactory
    interface UpcomingDetailsViewModelFactory {
        fun create(id: String): UpcomingDetailsViewModel
    }
}


sealed class UpcomingDetailsState {
    data object Loading : UpcomingDetailsState()
    data class Result(val upcoming: Upcoming) : UpcomingDetailsState()
    data object Failed : UpcomingDetailsState()
}