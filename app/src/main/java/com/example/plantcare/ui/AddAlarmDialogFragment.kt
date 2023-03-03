package com.example.plantcare.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.plantcare.databinding.DialogFragmentAddAlarmBinding
import com.example.plantcare.models.AlarmData
import com.example.plantcare.util.SaveAlarmData

class AddAlarmDialogFragment : DialogFragment() {

    private var _binding: DialogFragmentAddAlarmBinding? = null
    private val binding get() = _binding!!

    private lateinit var alarmVM: AlarmViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = DialogFragmentAddAlarmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alarmVM = (activity as MainActivity).alarmVM
        binding.etRepeat.isEnabled = false

        binding.switchAlarmItem.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.etRepeat.isEnabled = true
            } else if (binding.etRepeat.text == null) {
                binding.etRepeat.isEnabled = false
            } else {
                binding.etRepeat.isEnabled = false
                binding.etRepeat.text = null
            }
        }

        binding.btnSave.setOnClickListener {
            if (binding.switchAlarmItem.isChecked && binding.etRepeat.length() <= 0) {
                binding.etRepeat.requestFocus()
                binding.etRepeat.setError("Enter data")
            } else {
                val isActive = true
                val hour: Int = binding.timePickerAlarm.hour
                val minute: Int = binding.timePickerAlarm.minute
                val repeatDay: Int? = binding.etRepeat.text.toString().toIntOrNull()
                val category: String = binding.spinnerAlarm.selectedItem.toString()
                val alarm =
                    AlarmData(
                        hour = hour,
                        minute = minute,
                        repeatDay = repeatDay,
                        category = category,
                        isActive = isActive
                    )

                binding.etRepeat.isEnabled = false
                alarmVM.saveAlarm(alarm)
            }
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        alarmVM.savedAlarmId.observe(viewLifecycleOwner) {
            it?.toInt()?.let {
                SaveAlarmData(activity?.applicationContext!!).setAlarm(it)
                dismiss()
            }
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

