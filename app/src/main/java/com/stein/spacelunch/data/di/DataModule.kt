package com.stein.spacelunch.data.di

import com.stein.spacelunch.data.UpcomingRepository
import com.stein.spacelunch.data.UpcomingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsUpcomingRepository(
        upcomingRepository: UpcomingRepositoryImpl
    ): UpcomingRepository
}