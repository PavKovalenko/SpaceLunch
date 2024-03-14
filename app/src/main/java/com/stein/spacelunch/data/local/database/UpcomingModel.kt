package com.stein.spacelunch.data.local.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Entity
data class UpcomingModel(
    val name: String,
    val statusName: String,
    val launchProvider: String,
    val podLocation: String?,
    val image: String,
    val windowEnd: Date,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}

@Dao
interface UpcomingModelDao {
    @Query("SELECT * FROM upcomingmodel ORDER BY uid DESC LIMIT 10")
    fun getUpcomings(): Flow<List<UpcomingModel>>

    @Insert
    suspend fun insertUpcomings(item: List<UpcomingModel>)

    @Query("DELETE FROM upcomingmodel")
    suspend fun nukeTable()
}