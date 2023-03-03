package com.example.plantcare.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.plantcare.databinding.RvAlrmItemBinding
import com.example.plantcare.model.models.AlarmData

class AlarmAdapter : RecyclerView.Adapter<AlarmAdapter.alarmVH>() {

    inner class alarmVH(val binding: RvAlrmItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: AlarmData) {

            binding.apply {
                if (model.repeatDay == null || model.repeatDay == 0) {
                    tvAlarmRepeat.visibility = View.GONE
                } else {
                    tvAlarmRepeat.text = "After every ${model.repeatDay} days"

                }
                if (tvAlarmRepeat.visibility == View.GONE) {
                    tvalarmCategory.text = model.category
                } else {
                    tvalarmCategory.text = " ${model.category},"

                }
                tvAlarmHour.text = model.hour.toString() + ":"
                tvAlarmMinute.text = model.minute.toString()
                switchAlarmItem.isChecked = model.isActive


                switchAlarmItem.setOnCheckedChangeListener { buttonView, isChecked ->
                    onCheckedChangeListener?.invoke(model)
                }


            }
        }
    }


    private val differCallback = object : DiffUtil.ItemCallback<AlarmData>() {
        override fun areItemsTheSame(oldItem: AlarmData, newItem: AlarmData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AlarmData, newItem: AlarmData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): alarmVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = RvAlrmItemBinding.inflate(inflater, parent, false)
        return alarmVH(view)
    }

    override fun onBindViewHolder(holder: alarmVH, position: Int) {
        val model = differ.currentList[position]
        holder.bind(model)
    }

    override fun getItemCount(): Int = differ.currentList.size


    private var onCheckedChangeListener: ((AlarmData) -> Unit)? = null

    fun setOnCheckedChangeListener(listener: (AlarmData) -> Unit) {
        onCheckedChangeListener = listener
    }


}