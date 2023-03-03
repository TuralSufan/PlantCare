package com.example.plantcare.view.fragments

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.plantcare.databinding.FragmentAddPlantBinding
import com.example.plantcare.model.models.FileModel
import com.example.plantcare.model.models.Plant
import com.example.plantcare.view.adapters.AddPlantPhotoAdapter
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class AddPlantFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    private var collectionRef = Firebase.firestore.collection("PlantInfo")
    private var storageRef = Firebase.storage.reference
    private var fileList = mutableListOf<FileModel>()
    private var imageUrlList = mutableListOf<String>()
    private var count: Int = 100

    private var _binding: FragmentAddPlantBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddPlantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding.ibAddPhoto.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                it.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                imagelauncher.launch(Intent.createChooser(it, "Select photo"))
            }
        }


        collectionRef.get().addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
            if (task.isSuccessful) {
                count = task.result.size()
            } else {
                count = System.currentTimeMillis().toInt()
            }


        })




        binding.btnSend.setOnClickListener {

            val plantName = binding.etPlantName.text.toString().trim()
            val plantCategory = binding.spinnerCategory.selectedItem.toString()
            val plantSoil = binding.etPlantSoil.text.toString()
            val plantWatering = binding.etPlantWatering.text.toString()
            val plantHumidity = binding.etPlantHumidity.text.toString()
            val plantTemperature = binding.etPlantTemperature.text.toString()
            val plantLighting = binding.etPlantLighting.text.toString()
            val plantPhoto = imageUrlList
            val plantID = count++
            val plant = Plant(
                plantTemperature = plantTemperature,
                plantCategory = plantCategory,
                plantHumidity = plantHumidity,
                plantLighting = plantLighting,
                plantName = plantName,
                plantSoil = plantSoil,
                plantWatering = plantWatering,
                plantPhoto = plantPhoto,
                id = plantID
            )

            if (plantName.isNotEmpty() && plantWatering.isNotEmpty() && plantSoil.isNotEmpty() && fileList.size > 0) {
                binding.progressBar.visibility = View.VISIBLE
                CoroutineScope(Dispatchers.IO).launch {
                    fileList.forEach {
                        it.uri?.let { uri ->
                            val child = storageRef.child("images/${it.name}")
                            child.putFile(uri).await()
                            val url = child.downloadUrl.await()
                            imageUrlList.add(url.toString())
                        }
                    }
                    plant.id = plant.id?.plus(1)
                    savePlantData(plant)
                    setFragmentResult("succes", bundleOf())
                }
            } else {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(
                    context,
                    "Please enter all required information",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

        }

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }


    }


    private fun savePlantData(plant: Plant) {
        collectionRef.add(plant).addOnSuccessListener {
            Toast.makeText(context, "Succesfully added", Toast.LENGTH_LONG).show()
            findNavController().popBackStack()
        }.addOnFailureListener {
            binding.progressBar.visibility = View.GONE
            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        }
    }


    private val imagelauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result?.data?.clipData?.let {
                    val clipDataCount = it.itemCount
                    for (clipPosition in 0 until clipDataCount) {
                        addItems(
                            getFileNameFromURI(requireContext(), it.getItemAt(clipPosition).uri),
                            it.getItemAt(clipPosition).uri
                        )
                    }
                    setupRecyclerView()
                } ?: run {
                    result.data?.data?.let {
                        addItems(getFileNameFromURI(requireContext(), it), it)
                        setupRecyclerView()
                    }
                }
            } else {
                Toast.makeText(requireContext(), " Canceled", Toast.LENGTH_LONG).show()
            }
        }


    private fun setupRecyclerView() {
        binding.rvPhotos.apply {
            adapter = AddPlantPhotoAdapter(fileList)
        }
    }


    private fun addItems(name: String?, uri: Uri?) {
        fileList.add(FileModel(name, uri))
    }


    private fun getFileNameFromURI(contex: Context, uri: Uri): String? {
        val fileName: String?
        val cursor = contex.contentResolver.query(uri, null, null, null, null)
        cursor?.moveToFirst()
        fileName = cursor?.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
        cursor?.close()
        return fileName
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}