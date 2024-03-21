package com.stein.spacelunch.data.local.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val upcomingId: String,
    val prevKey: Int?,
    val nextKey: Int?
)

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE upcomingId = :upcomingId")
    suspend fun remoteKeysUpcomingId(upcomingId: String): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}