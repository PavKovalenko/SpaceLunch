package com.stein.spacelunch.data.local.di

import android.content.Context
import androidx.room.Room
import com.stein.spacelunch.data.local.database.AppDatabase
import com.stein.spacelunch.data.local.database.UpcomingLocalDataSource
import com.stein.spacelunch.data.local.database.UpcomingLocalDataSourceImpl
import com.stein.spacelunch.data.local.database.UpcomingModelDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideUpcomingDao(appDatabase: AppDatabase): UpcomingModelDao {
        return appDatabase.upcomingModelDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "Upcoming"
        ).build()
    }

    @Provides
    fun provideUpcomingLocalDataSource(upcomingModelDao: UpcomingModelDao): UpcomingLocalDataSource {
        return UpcomingLocalDataSourceImpl(upcomingModelDao)
    }
}