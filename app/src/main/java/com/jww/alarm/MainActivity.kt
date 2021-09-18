package com.jww.alarm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jww.alarm.databinding.ActivityMainBinding
import com.jww.alarm.eumes.VIEW_TYPE
import com.jww.alarm.views.alarmListView.AlarmListFragment
import com.jww.alarm.views.registerAlarmView.RegisterAlarmFragment
import com.jww.alarm.views.settingView.SettingFragment

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNaviMenu()
    }

    private fun initBottomNaviMenu() {
        _binding?.run {
            bottomNV.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.navi_list -> {
                        loadFragment(VIEW_TYPE.ALARM_LIST)
                    }
                    R.id.navi_register -> {
                        loadFragment(VIEW_TYPE.REGISTER_ALARM)
                    }
                    R.id.navi_setting -> {
                        loadFragment(VIEW_TYPE.SETTING)
                    }
                }
                true
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        loadFragment(VIEW_TYPE.ALARM_LIST)
        _binding?.bottomNV?.selectedItemId = R.id.navi_list
    }

    private fun loadFragment(viewType: VIEW_TYPE) {
        when (viewType) {
            VIEW_TYPE.ALARM_LIST -> {
//                알람 목록 화면
                val f = AlarmListFragment.newInstance()
                val ft = supportFragmentManager.beginTransaction()
                ft.replace(binding.flRoot.id, f)
                ft.commit()
            }
            VIEW_TYPE.REGISTER_ALARM -> {
//                알람 등록 화면
                val f = RegisterAlarmFragment.newInstance()
                val ft = supportFragmentManager.beginTransaction()
                ft.replace(binding.flRoot.id, f)
                ft.commit()
            }
            VIEW_TYPE.SETTING -> {
//               설정화면
//                supportFragmentManager.popBackStack(
//                    VIEW_TYPE.SETTING.toString(),
//                    FragmentManager.POP_BACK_STACK_INCLUSIVE
//                )
                val f = SettingFragment.newInstance()
                val ft = supportFragmentManager.beginTransaction()
                ft.replace(binding.flRoot.id, f)
//                ft.addToBackStack(VIEW_TYPE.SETTING.toString())
                ft.commit()
            }
        }
    }
}