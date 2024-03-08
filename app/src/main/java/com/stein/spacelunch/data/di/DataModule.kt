package com.stein.spacelunch.data.di

import com.stein.spacelunch.data.local.DefaultUpcomingRepository
import com.stein.spacelunch.data.local.UpcomingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsUpcomingRepository(
        upcomingRepository: DefaultUpcomingRepository
    ): UpcomingRepository
}

class FakeUpcomingRepository @Inject constructor() : UpcomingRepository {
    override val upcomings: Flow<List<String>> = flowOf(fakeUpcomings)

    override suspend fun update() {
        // no-op
    }
}

val fakeUpcomings = listOf("One", "Two", "Three")