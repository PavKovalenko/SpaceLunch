package com.stein.spacelunch.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.stein.spacelunch.data.local.database.AppDatabase
import com.stein.spacelunch.data.local.database.RemoteKeys
import com.stein.spacelunch.data.local.database.UpcomingModel
import com.stein.spacelunch.data.model.toUpcomingModel
import com.stein.spacelunch.data.network.ApiService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class UpcomingRemoteMediator(
    private val service: ApiService,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, UpcomingModel>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UpcomingModel>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_KEY
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with endOfPaginationReached = false because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val apiResponse = service.getUpcomings(offset = page, limit = state.config.pageSize)

            val upcomingsResult = apiResponse.results
            val endOfPaginationReached = upcomingsResult.isEmpty()
            appDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    appDatabase.remoteKeysDao().clearRemoteKeys()
                    appDatabase.upcomingModelDao().clearUpcomings()
                }
                val prevKey = if (page == STARTING_KEY) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = upcomingsResult.map {
                    RemoteKeys(upcomingId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                appDatabase.remoteKeysDao().insertAll(keys)
                val upcomingModels = upcomingsResult.map { it.toUpcomingModel() }
                appDatabase.upcomingModelDao().insertUpcomings(upcomingModels)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, UpcomingModel>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                appDatabase.remoteKeysDao().remoteKeysUpcomingId(it.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, UpcomingModel>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                appDatabase.remoteKeysDao().remoteKeysUpcomingId(it.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, UpcomingModel>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let {
                appDatabase.remoteKeysDao().remoteKeysUpcomingId(it)
            }
        }
    }

    companion object {
        private const val STARTING_KEY = 0
    }
}