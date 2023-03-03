package com.example.plantcare.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.plantcare.R
import com.example.plantcare.databinding.FragmentCheckUserBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CheckUserFragment : Fragment() {

    lateinit var auth: FirebaseAuth

    private var _binding: FragmentCheckUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckUserBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        checkUserForStartApp()

    }

    private fun checkUserForStartApp() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(500L)
            if (auth.currentUser == null) {
                findNavController().navigate(R.id.action_checkUserFragment_to_introFragment)

            } else {
                findNavController().navigate(R.id.action_checkUserFragment_to_discoverFragment)
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}