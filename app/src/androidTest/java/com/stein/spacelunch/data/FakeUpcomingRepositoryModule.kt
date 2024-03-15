package com.stein.spacelunch.data

import com.stein.spacelunch.data.di.DataModule
import com.stein.spacelunch.data.model.Upcoming
import com.stein.spacelunch.data.model.toUpcoming
import com.stein.spacelunch.fakeUpcomings
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
@Module
object FakeInventoryRepositoryModule {
    @Singleton
    @Provides
    fun provideFakeInventoryRepository() = object : UpcomingRepository {
        override val upcomings: Flow<List<Upcoming>>
            get() = flowOf(fakeUpcomings.map { it.toUpcoming() })

        override suspend fun update(onFailure: (Throwable) -> Unit) {
            // no-op
        }
    }
}