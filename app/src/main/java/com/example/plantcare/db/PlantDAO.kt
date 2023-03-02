package com.example.plantcare.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.plantcare.models.Plant

@Dao
interface PlantDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(plant: Plant): Long

    @Query("SELECT * FROM plants")
    fun getAllPlants(): LiveData<List<Plant>>

    @Delete
    suspend fun deletePlant(plant: Plant)


}