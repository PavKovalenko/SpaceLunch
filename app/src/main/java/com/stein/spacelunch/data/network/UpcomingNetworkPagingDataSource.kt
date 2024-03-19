package com.stein.spacelunch.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.stein.spacelunch.data.network.model.ApiUpcoming
import kotlin.math.max

class UpcomingNetworkPagingDataSource(private val apiService: ApiService) :
    PagingSource<Int, ApiUpcoming>() {

    private val upcomingList = mutableListOf<ApiUpcoming>()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiUpcoming> {
        return try {
            val nextPageNumber = params.key ?: 0
            val response =
                apiService.getUpcomings(offset = nextPageNumber * PAGE_SIZE, limit = PAGE_SIZE)
            upcomingList.replaceIndexed(nextPageNumber * PAGE_SIZE, response.results)
            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPageNumber == 0) null else nextPageNumber - 1,
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ApiUpcoming>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val item = state.closestItemToPosition(anchorPosition) ?: return null
        val itemIndex = upcomingList.indexOf(item)
        return ensureValidKey(key = itemIndex - (state.config.pageSize / 2))
    }

    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)

    private fun MutableList<ApiUpcoming>.replaceIndexed(
        startIndex: Int,
        newItems: List<ApiUpcoming>
    ) {
        val end = startIndex + newItems.size
        if (end > size) {
            addAll(newItems)
        } else {
            for (i in startIndex until end) {
                set(i, newItems[i - startIndex])
            }
        }
    }

    companion object {
        private const val STARTING_KEY = 0
        private const val PAGE_SIZE = 10
    }
}