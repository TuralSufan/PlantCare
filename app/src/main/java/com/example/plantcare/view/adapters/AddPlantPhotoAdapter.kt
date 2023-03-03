package com.example.plantcare.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plantcare.databinding.RvAddPlantPhotosItemBinding
import com.example.plantcare.model.models.FileModel

class AddPlantPhotoAdapter(
    private val fileList: MutableList<FileModel>
) : RecyclerView.Adapter<AddPlantPhotoAdapter.plantPhotoVH>() {

    inner class plantPhotoVH(val binding: RvAddPlantPhotosItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): plantPhotoVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = RvAddPlantPhotosItemBinding.inflate(inflater, parent, false)
        return plantPhotoVH(view)
    }

    override fun onBindViewHolder(holder: plantPhotoVH, position: Int) {
        holder.binding.apply {
            ivPlantPhoto.setImageURI(fileList[position].uri)
            tvimageName.text = fileList[position].name
        }
    }


    override fun getItemCount(): Int = fileList.size


}