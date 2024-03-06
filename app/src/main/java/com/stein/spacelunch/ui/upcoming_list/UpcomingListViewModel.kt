package com.stein.spacelunch.ui.upcoming_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stein.spacelunch.data.local.UpcomingRepository
import com.stein.spacelunch.ui.upcoming_list.UpcomingListUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingListViewModel @Inject constructor(
    private val upcomingRepository: UpcomingRepository
) : ViewModel() {

    val uiState: StateFlow<UpcomingListUiState> =
        upcomingRepository
            .upcomings.map<List<String>, UpcomingListUiState>(::Success)
            .catch { emit(UpcomingListUiState.Error(it)) }
            .stateIn(
                viewModelScope, SharingStarted.WhileSubscribed(5000),
                UpcomingListUiState.Loading
            )

    fun addItems() {
        viewModelScope.launch {
            upcomingRepository.addUpcoming("Test upcoming")
        }
    }

}

sealed interface UpcomingListUiState {
    data object Loading : UpcomingListUiState
    data class Error(val throwable: Throwable) : UpcomingListUiState
    data class Success(val data: List<String>) : UpcomingListUiState
}