package com.stein.spacelunch.ui.upcoming_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.stein.spacelunch.data.UpcomingRepository
import com.stein.spacelunch.data.model.Upcoming
import com.stein.spacelunch.data.model.toUpcoming
import com.stein.spacelunch.ui.upcoming_list.UpcomingListUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingListViewModel @Inject constructor(
    private val upcomingRepository: UpcomingRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UpcomingListUiState> =
        MutableStateFlow(UpcomingListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    val items: Flow<PagingData<Upcoming>> = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        pagingSourceFactory = { upcomingRepository.upcomingPagingSource() }
    )
        .flow
        .map { pagingData -> pagingData.map { it.toUpcoming() } }
        .cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            upcomingRepository.upcomings.catch {
                _uiState.value = UpcomingListUiState.Error(it)
            }.collect {
                _uiState.value = Success(it)
            }
        }

        viewModelScope.launch {
            upcomingRepository.update {
                _uiState.value = UpcomingListUiState.Error(it)
            }
        }

    }

}

sealed interface UpcomingListUiState {
    data object Loading : UpcomingListUiState
    data class Error(val throwable: Throwable, val data: List<Upcoming>? = null) :
        UpcomingListUiState
    data class Success(val data: List<Upcoming>) : UpcomingListUiState
}