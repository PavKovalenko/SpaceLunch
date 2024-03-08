package com.stein.spacelunch.data.local.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity
data class UpcomingModel(
    val name: String
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