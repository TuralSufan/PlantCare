package com.example.plantcare.model.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "plants"
)
data class Plant(

    @PrimaryKey(autoGenerate = false)
    var id: Int? = null,
    val plantName: String? = null,
    val plantCategory: String? = null,
    val plantSoil: String? = null,
    val plantWatering: String? = null,
    val plantLighting: String? = null,
    val plantTemperature: String? = null,
    val plantHumidity: String? = null,
    val plantPhoto: List<String>? = null
) : Serializable