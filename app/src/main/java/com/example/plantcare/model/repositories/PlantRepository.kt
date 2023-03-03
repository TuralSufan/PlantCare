package com.example.plantcare.model.repositories

import com.example.plantcare.model.db.PlantDatabase
import com.example.plantcare.model.models.Plant

class PlantRepository(
    val db: PlantDatabase
) {

    suspend fun upsertPLant(plant: Plant) = db.getPlantDAO().upsert(plant)

    fun getSavedPlants() = db.getPlantDAO().getAllPlants()

    suspend fun deletePlant(plant: Plant) = db.getPlantDAO().deletePlant(plant)

}