package com.stein.spacelunch.ui.upcoming_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.stein.spacelunch.data.UpcomingRepository
import com.stein.spacelunch.data.model.Upcoming
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingListViewModel @Inject constructor(
    private val upcomingRepository: UpcomingRepository
) : ViewModel() {

    val items: Flow<PagingData<Upcoming>> =
        upcomingRepository
            .getUpcomingStream()
            .cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            upcomingRepository.update {
                it.printStackTrace()
            }
        }
    }

}