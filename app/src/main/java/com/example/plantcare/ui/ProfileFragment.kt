package com.example.plantcare.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.plantcare.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    lateinit var auth: FirebaseAuth

    var _binding: FragmentProfileBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.etEmail.setText("${auth.currentUser!!.email}")



        binding.btnUpdateEmail.setOnClickListener {
            val user = auth.currentUser
            val email = binding.etEmail.text.toString()
            if (checkEmailField()) {
                user?.updateEmail(email)?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            requireContext(), "Email updated successfully.", Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(), it.exception.toString(), Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        binding.btnUpdatePassword.setOnClickListener {
            val user = auth.currentUser
            var password = binding.etPassword.text.toString()
            if (checkPasswordField()) {
                user?.updatePassword(password)?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            requireContext(), "Password updated successfully.", Toast.LENGTH_LONG
                        ).show()
                        binding.etPassword.text = null
                        binding.etConfirmPassword.text = null
                    } else {
                        Toast.makeText(
                            requireContext(), it.exception.toString(), Toast.LENGTH_LONG
                        ).show()
                    }

                }
            }
        }


    }


    private fun checkEmailField(): Boolean {
        val email = binding.etEmail.text.toString()
        if (email == "") {
            binding.textLayoutEmail.error = "This is required field"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.textLayoutEmail.error = "Check email format"
            return false
        }
        return true
    }


    private fun checkPasswordField(): Boolean {

        if (binding.etPassword.text.toString() == "") {
            binding.textLayoutPassword.error = "This is required field"
            binding.textLayoutPassword.errorIconDrawable = null
            return false
        }
        if (binding.etPassword.length() < 6) {
            binding.textLayoutPassword.error = "Password should at least 6 characters long"
            binding.textLayoutPassword.errorIconDrawable = null
            return false
        }
        if (binding.etPassword.text.toString() != binding.etConfirmPassword.text.toString()) {
            binding.textLayoutConfirmPassword.error = "Password not confirmed"
            binding.textLayoutPassword.errorIconDrawable = null
            return false
        }
        return true

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}