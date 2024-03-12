package com.stein.spacelunch.ui.upcoming_list.ui

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stein.spacelunch.data.model.Upcoming
import com.stein.spacelunch.ui.upcoming_list.UpcomingListUiState
import com.stein.spacelunch.ui.upcoming_list.UpcomingListViewModel
import com.stein.spacelunch.ui.widget.Progress

@Composable
fun UpcomingListScreen(
    viewModel: UpcomingListViewModel = hiltViewModel()
) {
    val itemsState by viewModel.uiState.collectAsStateWithLifecycle()
    when (itemsState) {
        is UpcomingListUiState.Success -> {
            UpcomingListScreen(
                items = (itemsState as UpcomingListUiState.Success).data,
            )
        }

        is UpcomingListUiState.Loading -> {
            Progress()
        }

        is UpcomingListUiState.Error -> {
            (itemsState as UpcomingListUiState.Error).data?.let {
                UpcomingListScreen(
                    items = it,
                )
            }
            Toast.makeText(
                LocalContext.current,
                (itemsState as UpcomingListUiState.Error).throwable.message,
                Toast.LENGTH_SHORT,
            ).show()

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