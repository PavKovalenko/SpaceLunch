package com.stein.spacelunch.data.local.database

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

interface UpcomingLocalDataSource {
    fun getUpcomings(): Flow<List<UpcomingModel>>
    suspend fun updateUpcomings(upcomings: List<UpcomingModel>)
    suspend fun updateUpcoming(upcoming: UpcomingModel)
    fun getUpcoming(upcomingId: String): Flow<UpcomingModel>
    fun getUpcomingsPagingSource(): PagingSource<Int, UpcomingModel>
}

class UpcomingLocalDataSourceImpl(private val upcomingModelDao: UpcomingModelDao) :
    UpcomingLocalDataSource {
    override fun getUpcomings() = upcomingModelDao.getUpcomings()

    override suspend fun updateUpcomings(upcomings: List<UpcomingModel>) {
        upcomingModelDao.clearUpcomings()
        upcomingModelDao.insertUpcomings(upcomings)
    }

    override suspend fun updateUpcoming(upcoming: UpcomingModel) {
        upcomingModelDao.insertUpcomings(listOf(upcoming))
    }

    override fun getUpcomingsPagingSource() = upcomingModelDao.getUpcomingsPagingSource()

    override fun getUpcoming(upcomingId: String) = upcomingModelDao.getUpcoming(upcomingId)

}