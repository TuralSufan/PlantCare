package com.example.plantcare.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.plantcare.R
import com.example.plantcare.databinding.FragmentDetailBinding
import com.example.plantcare.adapters.DetailViewpagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: PlantViewModel
    val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val plant = args.plant
        viewModel = (activity as MainActivity).viewModel

        val list: List<String>? = plant.plantPhoto
        val mAdapter = DetailViewpagerAdapter(list)
        binding.viewpagerDetail.adapter = mAdapter

        mAdapter.differ.submitList(list)

        binding.ibAddFavorite.setOnClickListener {
            viewModel.savePlant(plant)
            binding.ibAddFavorite.setImageResource(R.drawable.green_favorite_24)
            Toast.makeText(requireContext(), "Plant saved successfully", Toast.LENGTH_SHORT).show()
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvDetailTitle.text = plant.plantName
        binding.tvCategoryOfPlant.text = plant.plantCategory.toString()
        binding.tvHumidityInfo.text = plant.plantHumidity.toString().trim()
        binding.tvWateringInfo.text = plant.plantWatering
        binding.tvSoilInfo.text = plant.plantSoil
        binding.tvTemperatureInfo.text = plant.plantTemperature
        binding.tvTitle.text = plant.plantName
        binding.tvLightingInfo.text = plant.plantLighting

        TabLayoutMediator(
            binding.tablayoutDeatilViewpager,
            binding.viewpagerDetail,
        ) { tab, position ->
        }.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}