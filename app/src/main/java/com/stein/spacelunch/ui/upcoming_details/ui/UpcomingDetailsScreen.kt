package com.stein.spacelunch.ui.upcoming_details.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stein.spacelunch.ui.upcoming_details.UpcomingDetailsState
import com.stein.spacelunch.ui.upcoming_details.UpcomingDetailsViewModel
import com.stein.spacelunch.ui.upcoming_list.ui.UpcomingItem
import com.stein.spacelunch.ui.widget.Progress

@Composable
fun UpcomingDetailsScreen(
    upcomingId: String,
    viewModel: UpcomingDetailsViewModel = hiltViewModel<UpcomingDetailsViewModel, UpcomingDetailsViewModel.UpcomingDetailsViewModelFactory> { factory ->
        factory.create(upcomingId)
    }
) {
    val uiState by viewModel.upcomingDetailsState.collectAsStateWithLifecycle(
        initialValue = UpcomingDetailsState.Loading,
        lifecycle = LocalLifecycleOwner.current.lifecycle
    )
    when (uiState) {
        is UpcomingDetailsState.Loading -> {
            Progress()
        }

        is UpcomingDetailsState.Result -> {
            UpcomingItem(upcoming = (uiState as UpcomingDetailsState.Result).upcoming)
        }

        is UpcomingDetailsState.Failed -> {
            Text("Unexpected error occurred")
        }
    }
}
