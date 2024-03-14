package com.stein.spacelunch.data.network.di

import com.stein.spacelunch.data.network.RetrofitBuilder
import com.stein.spacelunch.data.network.UpcomingNetworkDataSource
import com.stein.spacelunch.data.network.UpcomingNetworkDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideApiHelper(): UpcomingNetworkDataSource {
        return UpcomingNetworkDataSourceImpl(RetrofitBuilder.apiService)
    }

}