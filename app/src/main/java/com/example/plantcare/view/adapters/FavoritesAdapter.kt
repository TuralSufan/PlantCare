package com.example.plantcare.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantcare.R
import com.example.plantcare.databinding.RvFavoritesItemBinding
import com.example.plantcare.model.models.Plant

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.FavoritesVH>() {


    inner class FavoritesVH(val binding: RvFavoritesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Plant) {
            binding.apply {
                Glide.with(root.context).load(model.plantPhoto?.first()).into(ivFavoriteItem)
                tvFavoriteItemTitle.text = model.plantName
                tvFavoriteItemDesc.text = model.plantSoil
                ibDelete.setOnClickListener {
                    onItemClickListenerForDelete?.invoke(model)
                }
                ibNavigate.setOnClickListener {
                    onItemClickListenerMainItem?.invoke(model)
                }
                root.setOnClickListener {
                    onItemClickListenerMainItem?.invoke(model)
                }

            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Plant>() {
        override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem.plantPhoto == newItem.plantPhoto
        }

        override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = RvFavoritesItemBinding.inflate(inflater, parent, false)
        return FavoritesVH(view)
    }

    override fun onBindViewHolder(holder: FavoritesVH, position: Int) {
        holder.binding.root.setOnClickListener {
            it.findNavController().navigate(R.id.action_FromFavorite_ToDetail)
        }
        val model = differ.currentList[position]
        holder.bind(model)
    }

    override fun getItemCount(): Int = differ.currentList.size


    private var onItemClickListenerMainItem: ((Plant) -> Unit)? = null

    fun setOnItemClickListenerMain(listener: (Plant) -> Unit) {
        onItemClickListenerMainItem = listener
    }


    private var onItemClickListenerForDelete: ((Plant) -> Unit)? = null

    fun setOnItemClickListenerDelete(listener: (Plant) -> Unit) {
        onItemClickListenerForDelete = listener
    }


}