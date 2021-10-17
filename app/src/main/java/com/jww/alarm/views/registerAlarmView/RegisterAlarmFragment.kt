package com.jww.alarm.views.registerAlarmView

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jww.alarm.MainActivity
import com.jww.alarm.bases.BaseFragment
import com.jww.alarm.databinding.FragmentRegisterAlarmBinding
import com.jww.alarm.receiver.AlarmReceiver
import com.jww.alarm.services.AlarmBackgroundService.Companion.NOTIFICATION_ID

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
    private lateinit var currentAct: MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        currentAct = (activity as MainActivity)
    }

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
            vm.completeRegister(currentAct)
            alarmInit()
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

    private fun alarmInit() {
        val alarmManager =
            currentAct.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val intent = Intent(currentAct, AlarmReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            currentAct,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        Log.d("Won","Alarm Intent")
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + (10 * 1000),
            pendingIntent
        )
    }
}