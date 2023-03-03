package com.example.plantcare.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.plantcare.R
import com.example.plantcare.databinding.FragmentFavoritesBinding
import com.example.plantcare.adapters.FavoritesAdapter

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: PlantViewModel

    private val mAdapter = FavoritesAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).plantVM
        binding.rvFavorites.adapter = mAdapter

        viewModel.getSavedPlants().observe(viewLifecycleOwner, Observer {
            mAdapter.differ.submitList(it)
        })

        mAdapter.setOnItemClickListenerMain {
            val bundle = Bundle().apply {
                putSerializable("plant", it)
            }
            findNavController().navigate(
                R.id.action_FromFavorite_ToDetail,
                bundle
            )
        }

        mAdapter.setOnItemClickListenerDelete {
            viewModel.deletePlant(it)
            Toast.makeText(requireContext(), "Plant deleted from Favorites", Toast.LENGTH_SHORT)
                .show()
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}