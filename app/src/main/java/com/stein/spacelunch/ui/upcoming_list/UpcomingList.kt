package com.stein.spacelunch.ui.upcoming_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
    when (items) {
        is UpcomingListUiState.Success -> {
            UpcomingListScreen(
                items = (items as UpcomingListUiState.Success).data,
                modifier = modifier,
                onAddItem = { viewModel.addItems() },
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
    modifier: Modifier = Modifier,
    items: List<String>,
    onAddItem: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
    ) {
        for (item in items) {
            Text(item)
        }
        Button(onClick = onAddItem) {
            Text(text = "Add")
        }
    }
}