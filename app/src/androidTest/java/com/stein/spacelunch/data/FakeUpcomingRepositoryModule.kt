package com.stein.spacelunch.data

import com.stein.spacelunch.data.di.DataModule
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
        override val upcomings: Flow<List<String>>
            get() = flowOf(fakeUpcomings)

        override suspend fun update() {
            // no-op
        }
    }
}

val fakeUpcomings = listOf("One", "Two", "Three")