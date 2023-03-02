package com.example.plantcare.repository

import com.example.plantcare.db.PlantDatabase
import com.example.plantcare.models.AlarmData
import com.example.plantcare.models.Plant

class PlantRepository(
    val db: PlantDatabase
) {

    suspend fun upsertPLant(plant: Plant) = db.getPlantDAO().upsert(plant)

    fun getSavedPlants() = db.getPlantDAO().getAllPlants()

    suspend fun deletePlant(plant: Plant) = db.getPlantDAO().deletePlant(plant)


    suspend fun upsertAlarm(alarm: AlarmData): Long = db.getAlarmDAO().upsertAlarm(alarm)

    fun getAlarms() = db.getAlarmDAO().getAlarms()

    suspend fun deleteAlarm(alarm: AlarmData) = db.getAlarmDAO().deleteAlarm(alarm)

    suspend fun updateAlarm(alarm: AlarmData) = db.getAlarmDAO().updateAlarm(alarm)

}