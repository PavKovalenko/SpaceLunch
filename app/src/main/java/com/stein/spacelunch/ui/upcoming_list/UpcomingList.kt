package com.stein.spacelunch.ui.upcoming_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun UpcomingListScreen(
    modifier: Modifier = Modifier,
    viewModel: UpcomingListViewModel = hiltViewModel()
) {
    val items by viewModel.uiState.collectAsStateWithLifecycle()
    if (items is UpcomingListUiState.Success) {
        UpcomingListScreen(
            item = (items as UpcomingListUiState.Success).data,
            modifier = modifier
        )
    }
    when (items) {
        is UpcomingListUiState.Success -> {
            UpcomingListScreen(
                item = (items as UpcomingListUiState.Success).data,
                modifier = modifier
            )
        }

        else -> {
            //todo add loading and error states
        }
    }
}

@Composable
internal fun UpcomingListScreen(
    modifier: Modifier = Modifier,
    item: Int,
) {
    Column(modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(item.toString())
        }
    }
}