package com.stein.spacelunch.data.local.database

import kotlinx.coroutines.flow.Flow

interface UpcomingLocalDataSource {
    fun getUpcomings(): Flow<List<UpcomingModel>>
    suspend fun updateUpcomings(upcomings: List<String>)
}

class UpcomingLocalDataSourceImpl(private val upcomingModelDao: UpcomingModelDao) :
    UpcomingLocalDataSource {
    override fun getUpcomings() = upcomingModelDao.getUpcomings()

    override suspend fun updateUpcomings(upcomings: List<String>) {
        upcomingModelDao.nukeTable()
        upcomingModelDao.insertUpcomings(upcomings.map { UpcomingModel(it) })
    }
}