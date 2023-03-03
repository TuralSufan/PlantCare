package com.example.plantcare.ui

import SingleLiveEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.models.AlarmData
import com.example.plantcare.repository.AlarmRepository
import kotlinx.coroutines.launch

class AlarmViewModel(val alarmRepository: AlarmRepository) : ViewModel() {


    var savedAlarmId: SingleLiveEvent<Long> = SingleLiveEvent()


    fun saveAlarm(alarm: AlarmData) = viewModelScope.launch {
        val alarmId = alarmRepository.upsertAlarm(alarm)
        savedAlarmId.postValue(alarmId)
    }

    fun deleteAlarm(alarm: AlarmData) = viewModelScope.launch {
        alarmRepository.deleteAlarm(alarm)
    }

    fun getAlarms() = alarmRepository.getAlarms()

    fun updateAlarm(alarm: AlarmData) = viewModelScope.launch {
        alarmRepository.updateAlarm(alarm)
    }


}