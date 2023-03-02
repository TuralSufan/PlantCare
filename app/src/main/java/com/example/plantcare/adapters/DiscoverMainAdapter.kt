package com.example.plantcare.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantcare.R
import com.example.plantcare.databinding.RvDiscoverMainItemBinding
import com.example.plantcare.models.Plant


class DiscoverMainAdapter() : RecyclerView.Adapter<DiscoverMainAdapter.DiscoverMainVH>() {


    inner class DiscoverMainVH(val binding: RvDiscoverMainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Plant) {
            binding.apply {
                Glide.with(root.context).load(model.plantPhoto?.first()).into(ivMainItem)
                tvItemTitle.text = model.plantName
                tvMainItemDesc.text = model.plantSoil
                ibAddFavorite.setOnClickListener {
                    onItemClickListenerForSave?.invoke(model)
                    ibAddFavorite.setImageResource(R.drawable.green_favorite_24)
                }
                btnMainItemNavigate.setOnClickListener {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverMainVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = RvDiscoverMainItemBinding.inflate(inflater, parent, false)
        return DiscoverMainVH(view)

    }

    override fun onBindViewHolder(holder: DiscoverMainVH, position: Int) {
        val model = differ.currentList[position]
        holder.bind(model)

        /*
            val item = itemList[position]
            val isSaved = savedList[position]
            holder.bind(item, isSaved)
        }*/

    }

    override fun getItemCount(): Int = differ.currentList.size


    private var onItemClickListenerMainItem: ((Plant) -> Unit)? = null

    fun setOnItemClickListenerMain(listener: (Plant) -> Unit) {
        onItemClickListenerMainItem = listener
    }


    private var onItemClickListenerForSave: ((Plant) -> Unit)? = null

    fun setOnItemClickListenerSave(listener: (Plant) -> Unit) {
        onItemClickListenerForSave = listener
    }


}
