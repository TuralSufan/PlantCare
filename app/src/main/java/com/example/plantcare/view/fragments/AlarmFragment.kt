package com.example.plantcare.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.plantcare.R
import com.example.plantcare.databinding.FragmentAlarmBinding
import com.example.plantcare.util.SaveAlarmData
import com.example.plantcare.view.MainActivity
import com.example.plantcare.view.adapters.AlarmAdapter
import com.example.plantcare.viewmodel.AlarmViewModel
import com.google.android.material.snackbar.Snackbar

class AlarmFragment : Fragment() {

    private var _binding: FragmentAlarmBinding? = null
    private val binding get() = _binding!!

    lateinit var alarmVM: AlarmViewModel
    private val mAdapter = AlarmAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlarmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alarmVM = (activity as MainActivity).alarmVM
        val saveAlarmData = SaveAlarmData(requireContext())


        binding.rvAlarm.adapter = mAdapter

        alarmVM.getAlarms().observe(viewLifecycleOwner, Observer {
            mAdapter.differ.submitList(it)
        })

        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val alarm = mAdapter.differ.currentList[position]
                saveAlarmData.cancelAlarm(alarm.id!!)
                alarmVM.deleteAlarm(alarm)
                val snackbar =
                    Snackbar.make(requireView(), "Successfully deleted ", Snackbar.LENGTH_LONG)
                snackbar.apply {
                    anchorView = binding.viewAlarm
                    setAction("Undo") {
                        alarm.isActive = false
                        alarmVM.saveAlarm(alarm)

                    }
                    show()
                }
            }

        }

        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.rvAlarm)
        }

        mAdapter.setOnCheckedChangeListener {
            when (it.isActive) {
                true -> {
                    it.isActive = false
                    alarmVM.updateAlarm(it)
                    saveAlarmData.setAlarm(it.id!!)
                }
                else -> {
                    it.isActive = true
                    alarmVM.updateAlarm(it)
                    saveAlarmData.setAlarm(it.id!!)
                }
            }
        }


        binding.layoutAddAlarm.setOnClickListener {
            findNavController().navigate(R.id.action_alarmFragment_to_addAlarmDialogFragment)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
