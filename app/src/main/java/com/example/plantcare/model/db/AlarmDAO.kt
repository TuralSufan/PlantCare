package com.example.plantcare.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.plantcare.model.models.AlarmData

@Dao
interface AlarmDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAlarm(alarm: AlarmData): Long

    @Update(onConflict = OnConflictStrategy.REPLACE, entity = AlarmData::class)
    suspend fun updateAlarm(alarm: AlarmData): Int

    @Delete
    suspend fun deleteAlarm(alarm: AlarmData)

    @Query("SELECT * FROM alarms")
    fun getAlarms(): LiveData<List<AlarmData>>

    @Query("SELECT * FROM alarms WHERE id = :alarmID")
    fun getAlarmByID(alarmID: Int): AlarmData?
}