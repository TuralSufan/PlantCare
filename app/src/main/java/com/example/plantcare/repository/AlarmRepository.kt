package com.example.plantcare.repository

import com.example.plantcare.db.PlantDatabase
import com.example.plantcare.models.AlarmData

class AlarmRepository(
    val db: PlantDatabase
) {

    suspend fun upsertAlarm(alarm: AlarmData): Long = db.getAlarmDAO().upsertAlarm(alarm)

    fun getAlarms() = db.getAlarmDAO().getAlarms()

    suspend fun deleteAlarm(alarm: AlarmData) = db.getAlarmDAO().deleteAlarm(alarm)

    suspend fun updateAlarm(alarm: AlarmData) = db.getAlarmDAO().updateAlarm(alarm)
}