package com.stein.spacelunch.ui.upcoming_list.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stein.spacelunch.data.model.Upcoming
import com.stein.spacelunch.ui.upcoming_list.UpcomingListUiState
import com.stein.spacelunch.ui.upcoming_list.UpcomingListViewModel

@Composable
fun UpcomingListScreen(
    viewModel: UpcomingListViewModel = hiltViewModel()
) {
    val items by viewModel.uiState.collectAsStateWithLifecycle()
    when (items) {
        is UpcomingListUiState.Success -> {
            UpcomingListScreen(
                items = (items as UpcomingListUiState.Success).data,
            )
        }

        is UpcomingListUiState.Loading -> {
            //todo add loading screen
        }

        is UpcomingListUiState.Error -> {
            //todo add error screen
        }
    }
}

@Composable
internal fun UpcomingListScreen(
    items: List<Upcoming>,
) {
    LazyColumn {
        items(items) { item ->
            UpcomingItem(item)
        }
    }
}