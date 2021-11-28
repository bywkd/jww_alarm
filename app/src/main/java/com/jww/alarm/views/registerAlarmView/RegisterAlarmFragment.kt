package com.jww.alarm.views.registerAlarmView

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jww.alarm.MainActivity
import com.jww.alarm.bases.BaseFragment
import com.jww.alarm.databinding.FragmentRegisterAlarmBinding
import com.jww.alarm.receiver.AlarmReceiver
import java.util.*

class RegisterAlarmFragment : BaseFragment() {


    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (!Settings.canDrawOverlays(currentAct)) {
                Toast.makeText(currentAct, "알림을 사용하기 위해 필요한 권한입니다.", Toast.LENGTH_SHORT).show()
            } else {

            }
        }

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
        checkLockScreenPermission()
    }

    private fun uiDataInit() {
        val calendar = Calendar.getInstance()
        vm.uiInitSound(false)
            .uiInitVibration(true)
            .uiInitTime(
                binding.timePicker,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)
            )
            .uiInitCalendar(
                binding.calendarView,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
            )
    }

    private fun checkLockScreenPermission() {
        if (!Settings.canDrawOverlays(currentAct)) {
            val uri = Uri.fromParts("package", currentAct.packageName, null)
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, uri)
            permissionLauncher.launch(intent)
        } else {

        }
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
        }

        binding.timePicker.setOnTimeChangedListener(vm.timerListener)
        binding.calendarView.setOnDateChangeListener(vm.calendarListener)
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

        vm.registerUid.observe(viewLifecycleOwner, {
            registerAlarm(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun registerAlarm(uid: Int) {
        val alarmManager =
            currentAct.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val intent = Intent(currentAct, AlarmReceiver::class.java)
        intent.putExtra("uid", uid)
        val pendingIntent = PendingIntent.getBroadcast(
            currentAct,
            AlarmReceiver.ALARM_RECEIVER_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            vm.calendar.timeInMillis,
            pendingIntent
        )
    }
}