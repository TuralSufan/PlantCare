package com.example.plantcare.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.plantcare.R
import com.example.plantcare.databinding.ActivityMainBinding
import com.example.plantcare.model.db.PlantDatabase
import com.example.plantcare.model.repositories.AlarmRepository
import com.example.plantcare.model.repositories.PlantRepository
import com.example.plantcare.viewmodel.AlarmViewModel
import com.example.plantcare.viewmodel.AlarmViewModelFactory
import com.example.plantcare.viewmodel.PlantViewModel
import com.example.plantcare.viewmodel.PlantViewModelFactory


class MainActivity : AppCompatActivity() {

    lateinit var plantVM: PlantViewModel
    lateinit var alarmVM: AlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        val navController = Navigation.findNavController(this, R.id.fragmentContainerView)
        binding.bottomNavView.setupWithNavController(navController)
        binding.bottomNavView.background = null
        binding.bottomNavView.menu.getItem(2).isEnabled = false
        binding.fab.setOnClickListener {
            Navigation.findNavController(this, R.id.fragmentContainerView)
                .navigate(R.id.action_to_add_plant)
        }


        val plantRepository = PlantRepository(PlantDatabase(applicationContext))
        val plantVMProviderFactory = PlantViewModelFactory(plantRepository)
        plantVM = ViewModelProvider(this, plantVMProviderFactory).get(PlantViewModel::class.java)


        val alarmRepository = AlarmRepository(PlantDatabase(applicationContext))
        val alarmVMProviderFactory = AlarmViewModelFactory(alarmRepository)
        alarmVM = ViewModelProvider(this, alarmVMProviderFactory).get(AlarmViewModel::class.java)


        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            if (nd.id == R.id.signInFragment || nd.id == R.id.signUpFragment || nd.id == R.id.introFragment || nd.id == R.id.checkUserFragment || nd.id == R.id.profileFragment || nd.id == R.id.detailFragment || nd.id == R.id.addPlantDialogFragment) {
                binding.bottomAppBar.visibility = View.GONE
                binding.fab.visibility = View.GONE

            } else {
                binding.bottomAppBar.visibility = View.VISIBLE
                binding.fab.visibility = View.VISIBLE
            }
        }


    }

}