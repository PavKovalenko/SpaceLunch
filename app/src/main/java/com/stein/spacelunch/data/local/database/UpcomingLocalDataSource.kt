package com.stein.spacelunch.data.local.database

import kotlinx.coroutines.flow.Flow

interface UpcomingLocalDataSource {
    fun getUpcomings(): Flow<List<UpcomingModel>>
    suspend fun updateUpcomings(upcomings: List<UpcomingModel>)
}

class UpcomingLocalDataSourceImpl(private val upcomingModelDao: UpcomingModelDao) :
    UpcomingLocalDataSource {
    override fun getUpcomings() = upcomingModelDao.getUpcomings()

    override suspend fun updateUpcomings(upcomings: List<UpcomingModel>) {
        upcomingModelDao.clearUpcomings()
        upcomingModelDao.insertUpcomings(upcomings)
    }
}