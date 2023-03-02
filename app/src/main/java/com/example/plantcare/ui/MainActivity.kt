package com.example.plantcare.ui

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
import com.example.plantcare.db.PlantDatabase
import com.example.plantcare.repository.PlantRepository


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: PlantViewModel

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


        val newsRepository = PlantRepository(PlantDatabase(applicationContext))
        val viewModelProviderFactory = PlantViewModelFactory(newsRepository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(PlantViewModel::class.java)


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