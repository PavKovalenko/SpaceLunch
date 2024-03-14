package com.stein.spacelunch.data.network

import com.stein.spacelunch.data.network.model.ApiUpcoming
import com.stein.spacelunch.data.network.model.ApiUpcomings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface UpcomingNetworkDataSource {
    fun getUpcomings(): Flow<ApiUpcomings>
    fun getUpcomingDetails(id: String): Flow<ApiUpcoming>
}

class UpcomingNetworkDataSourceImpl(private val apiService: ApiService) :
    UpcomingNetworkDataSource {
    override fun getUpcomings() = flow {
        emit(apiService.getUpcomings())
    }

    override fun getUpcomingDetails(id: String) = flow {
        emit(apiService.getUpcomingDetails(id))
    }
}