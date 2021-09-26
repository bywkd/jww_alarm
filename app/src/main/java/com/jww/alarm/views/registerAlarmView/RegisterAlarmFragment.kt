package com.jww.alarm.views.registerAlarmView

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.jww.alarm.bases.BaseFragment
import com.jww.alarm.databinding.FragmentRegisterAlarmBinding

class RegisterAlarmFragment : BaseFragment() {

    companion object {
        fun newInstance(): RegisterAlarmFragment {
            return RegisterAlarmFragment().apply {
                val arguments = Bundle().apply {
                    putString("", "")
                }
                this.arguments = arguments
            }
        }
    }

    private var _binding: FragmentRegisterAlarmBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var vm: RegisterAlarmVM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterAlarmBinding.inflate(inflater, container, false)
        vm = ViewModelProvider(this, RegisterAlarmVM.VMF()).get(RegisterAlarmVM::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        uiDataInit()
        bind()
        observer()
    }

    private fun uiDataInit() {
        vm.uiInitSound(false)
            .uiInitVibration(true)
            .uiInitTime(binding.timePicker, 6, 30)
            .uiInitCalendar(binding.calendar, 2021, 3, 20)
    }

    private fun bind() {
        binding.llSound.setOnClickListener {
            vm.setSound()
        }

        binding.llVibration.setOnClickListener {
            vm.setVibration()
        }

        binding.ivRegister.setOnClickListener {
            vm.completeRegister()
        }

        binding.timePicker.setOnTimeChangedListener(vm.timerListener)
        binding.calendar.setOnDateChangeListener(vm.calendarListener)
    }

    private fun observer() {
        vm.isSound.observe(viewLifecycleOwner, {
            if (it) {
//                온 이미지
            } else {
//                오프 이미지
            }
            Log.d("Won", "isSound = $it")
        })

        vm.isVibration.observe(viewLifecycleOwner, {
            if (it) {
//                온 이미지
            } else {
//                오프 이미지
            }
            Log.d("Won", "isVibration = $it")
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}