package com.example.plantcare.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.plantcare.R
import com.example.plantcare.databinding.FragmentAccountBinding
import com.google.firebase.auth.FirebaseAuth

class AccountFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        checkUserLogIn()

        binding.layoutSignOut.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_accountFragment_to_SignInFragment)
        }

        binding.btnSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_SignInFragment)
        }

        binding.layoutProfile.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_profileFragment)
        }


    }


    private fun checkUserLogIn() {
        binding.progressBar.visibility = View.GONE
        if (auth.currentUser == null) {
            binding.layoutForUser.visibility = View.GONE
        } else {
            binding.tvUserEmail.text = "${auth.currentUser!!.email}"
            binding.layoutForGuest.visibility = View.GONE
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}