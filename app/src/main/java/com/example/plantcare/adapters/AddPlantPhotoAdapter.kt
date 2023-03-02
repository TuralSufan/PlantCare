package com.example.plantcare.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plantcare.databinding.RvAddPlantPhotosItemBinding
import com.example.plantcare.models.FileModel

class AddPlantPhotoAdapter(
    private val fileList: MutableList<FileModel>
) : RecyclerView.Adapter<AddPlantPhotoAdapter.plantPhotoVH>() {

    inner class plantPhotoVH(val binding: RvAddPlantPhotosItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddPlantPhotoAdapter.plantPhotoVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = RvAddPlantPhotosItemBinding.inflate(inflater, parent, false)
        return plantPhotoVH(view)
    }

    override fun onBindViewHolder(holder: AddPlantPhotoAdapter.plantPhotoVH, position: Int) {
        holder.binding.apply {
            ivPlantPhoto.setImageURI(fileList[position].uri)
            tvimageName.setText(fileList[position].name)
        }
    }


    override fun getItemCount(): Int = fileList.size


}