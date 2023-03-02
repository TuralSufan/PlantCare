package com.example.plantcare.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plantcare.repository.PlantRepository

class PlantViewModelFactory(val repository: PlantRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlantViewModel(repository) as T
    }
}