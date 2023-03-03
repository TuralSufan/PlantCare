package com.example.plantcare.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plantcare.R
import com.example.plantcare.databinding.RvCategoriesItemBinding

class DiscoverCategoriesAdapter(context: Context) :
    RecyclerView.Adapter<DiscoverCategoriesAdapter.categoriesVH>() {

    private val list: MutableList<String> = mutableListOf()

    init {
        val categories = context.resources.getStringArray(R.array.categories)
        list.addAll(categories)
    }


    inner class categoriesVH(val binding: RvCategoriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: String) {
            binding.tvCategoryItem.text = model
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(model)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): categoriesVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = RvCategoriesItemBinding.inflate(inflater, parent, false)
        return categoriesVH(view)
    }

    override fun onBindViewHolder(holder: categoriesVH, position: Int) {
        list.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = 10


    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }


}