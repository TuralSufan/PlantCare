package com.example.plantcare.model.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(
    tableName = "alarms"
)
data class AlarmData(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val hour: Int? = null,
    val minute: Int? = null,
    val repeatDay: Int? = null,
    val category: String? = null,
    @ColumnInfo(defaultValue = "1")
    var isActive: Boolean = true
) : Serializable
