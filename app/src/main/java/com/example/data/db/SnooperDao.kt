package com.example.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.SnooperLog
import kotlinx.coroutines.flow.Flow

@Dao
interface SnooperDao {
    @Query("SELECT * FROM snooper_logs ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<SnooperLog>>

    @Query("SELECT * FROM snooper_logs WHERE id = :id LIMIT 1")
    suspend fun getLogById(id: Long): SnooperLog?

    @Query("SELECT COUNT(*) FROM snooper_logs")
    fun getLogCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: SnooperLog): Long

    @Delete
    suspend fun deleteLog(log: SnooperLog)

    @Query("DELETE FROM snooper_logs WHERE id = :id")
    suspend fun deleteLogById(id: Long)

    @Query("DELETE FROM snooper_logs")
    suspend fun clearAllLogs()
}
