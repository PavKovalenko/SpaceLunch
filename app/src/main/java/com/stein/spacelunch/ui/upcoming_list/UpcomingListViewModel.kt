package com.stein.spacelunch.ui.upcoming_list

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UpcomingListViewModel @Inject constructor(
    //todo put repository here
) : ViewModel() {
    val uiState: StateFlow<UpcomingListUiState> =
        MutableStateFlow<UpcomingListUiState>(UpcomingListUiState.Success(1))
}

sealed interface UpcomingListUiState {
    data object Loading : UpcomingListUiState
    data class Error(val throwable: Throwable) : UpcomingListUiState
    data class Success(val data: Int) : UpcomingListUiState
}