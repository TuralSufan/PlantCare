package com.example.plantcare.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plantcare.repository.AlarmRepository

class AlarmViewModelFactory(val repository: AlarmRepository): ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AlarmViewModel(repository) as T
    }

}