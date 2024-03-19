package com.stein.spacelunch.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Entity
data class UpcomingModel(
    @PrimaryKey
    val id: String,
    val name: String,
    val statusName: String,
    val launchProvider: String,
    val padLocation: String?,
    val image: String,
    val windowEnd: Date,
)

@Dao
interface UpcomingModelDao {
    @Query("SELECT * FROM upcomingmodel ORDER BY windowEnd DESC LIMIT 10")
    fun getUpcomings(): Flow<List<UpcomingModel>>

    @Query("SELECT * FROM upcomingmodel ORDER BY windowEnd ASC")
    fun getUpcomingsPagingSource(): PagingSource<Int, UpcomingModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcomings(item: List<UpcomingModel>)

    @Query("DELETE FROM upcomingmodel")
    suspend fun clearUpcomings()
}