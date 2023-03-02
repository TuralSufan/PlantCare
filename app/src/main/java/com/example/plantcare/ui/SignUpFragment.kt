package com.example.plantcare.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.plantcare.R
import com.example.plantcare.databinding.FragmentSignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    lateinit var auth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.webclient_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        binding.tvSignUptoSignIn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnSignUpWithGoogle.setOnClickListener {
            signUpWithGoogle()
        }

        binding.btnSignUpSignUp.setOnClickListener {
            registerUser()
        }


    }


    private fun registerUser() {
        val email = binding.etSignUpEmail.text.toString().trim()
        val cpassword = binding.etSignUpCPassword.text.toString().trim()
        val password = binding.etSignUpPassword.text.toString().trim()
        val terms = binding.cbSignUp
        binding.textInputLayoutEmail.isErrorEnabled = false
        binding.textInputLayoutPassword.isErrorEnabled = false
        binding.textInputLayoutConfirmPassword.isErrorEnabled = false

        if (email.isNotEmpty() && password.isNotEmpty() && cpassword.isNotEmpty() && terms.isChecked && cpassword == password) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {
                        checkLoggedInSTate()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else if (email == "") {
            binding.textInputLayoutEmail.error = "Please enter email address"
        } else if (password == "") {
            binding.textInputLayoutPassword.error = "Please enter password"
        } else if (password != cpassword) {
            binding.textInputLayoutConfirmPassword.error = "Please confirm password"
        } else if (!terms.isChecked) {
            Toast.makeText(
                context,
                "Please read and accept Terms and Conditions",
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(context, "Please enter all information", Toast.LENGTH_LONG).show()
        }

    }


    private fun signUpWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                result(task)
            }
        }

    private fun result(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount = task.result
            if (account != null) {
                updateSomeUI(account)

            }
        }
    }

    private fun updateSomeUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                findNavController().navigate(R.id.action_signUpFragment_to_discoverFragment)
            }
        }

    }


    private fun checkLoggedInSTate() {
        if (auth.currentUser == null) {
            Toast.makeText(context, "Something went wrong, Please try Sign in", Toast.LENGTH_SHORT)
                .show()
        } else {
            findNavController().navigate(R.id.action_signUpFragment_to_discoverFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
