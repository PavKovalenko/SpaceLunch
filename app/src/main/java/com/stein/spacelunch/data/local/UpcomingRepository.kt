package com.stein.spacelunch.data.local

import com.stein.spacelunch.data.local.database.UpcomingModel
import com.stein.spacelunch.data.local.database.UpcomingModelDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface UpcomingRepository {

    val upcomings: Flow<List<String>>

    suspend fun addUpcoming(name: String)
}

class DefaultUpcomingRepository @Inject constructor(
    private val upcomingModelDao: UpcomingModelDao
) : UpcomingRepository {

    override val upcomings: Flow<List<String>> =
        upcomingModelDao.getUpcomings().map { items -> items.map { it.name } }

    override suspend fun addUpcoming(name: String) {
        upcomingModelDao.insertUpcoming(UpcomingModel(name))
    }
}