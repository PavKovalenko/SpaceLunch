package com.stein.spacelunch.ui.upcoming_list.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.stein.spacelunch.data.model.Upcoming
import com.stein.spacelunch.ui.upcoming_list.UpcomingListViewModel
import com.stein.spacelunch.ui.widget.Progress
import kotlinx.coroutines.flow.Flow

@Composable
fun UpcomingListScreen(
    viewModel: UpcomingListViewModel = hiltViewModel()
) {
    UpcomingListScreen(viewModel.items)
}

@Composable
internal fun UpcomingListScreen(
    items: Flow<PagingData<Upcoming>>,
) {
    val pager = remember { items }
    val lazyPagingItems = pager.collectAsLazyPagingItems()
    LazyColumn {
        if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
            item {
                Progress()
            }
        }

        items(count = lazyPagingItems.itemCount) { index ->
            val item = lazyPagingItems[index]
            item?.let {
                UpcomingItem(upcoming = it)
            }
        }

        if (lazyPagingItems.loadState.append == LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }
}