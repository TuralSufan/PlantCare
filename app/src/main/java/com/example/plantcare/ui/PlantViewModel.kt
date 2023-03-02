package com.example.plantcare.ui

import SingleLiveEvent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.models.AlarmData
import com.example.plantcare.models.Plant
import com.example.plantcare.repository.PlantRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class PlantViewModel(
    val plantRepository: PlantRepository
) : ViewModel() {


    var listLiveData = MutableLiveData<List<Plant>>()
    private var listPlant: MutableList<Plant> = mutableListOf()

    var savedAlarmId: SingleLiveEvent<Long> = SingleLiveEvent()

    init {
        getPlantsFromFirestore()
    }

    private fun getPlantsFromFirestore() =
        FirebaseFirestore.getInstance().collection("PlantInfo").get()
            .addOnSuccessListener { snapshot ->
                val list = snapshot.toObjects(Plant::class.java)
                listPlant.addAll(list)
                listLiveData.value = listPlant
            }

    fun searchQuery(query: String) {
        if (query == "") {
            listLiveData.value = listPlant
        } else {
            listLiveData.value = listPlant.filter {
                it.plantName!!.contains(query, true) || it.plantCategory == query
            }
        }
    }


    fun savePlant(plant: Plant) = viewModelScope.launch {
        plantRepository.upsertPLant(plant)
    }

    fun deletePlant(plant: Plant) = viewModelScope.launch {
        plantRepository.deletePlant(plant)
    }

    fun getSavedPlants() = plantRepository.getSavedPlants()


    fun saveAlarm(alarm: AlarmData) = viewModelScope.launch {
        val alarmId = plantRepository.upsertAlarm(alarm)
        savedAlarmId.postValue(alarmId)
    }

    fun deleteAlarm(alarm: AlarmData) = viewModelScope.launch {
        plantRepository.deleteAlarm(alarm)
    }

    fun getAlarms() = plantRepository.getAlarms()

    fun updateAlarm(alarm: AlarmData) = viewModelScope.launch {
        plantRepository.updateAlarm(alarm)
    }

}