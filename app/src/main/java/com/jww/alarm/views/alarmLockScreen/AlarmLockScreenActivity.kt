package com.jww.alarm.views.alarmLockScreen

import android.app.KeyguardManager
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.jww.alarm.App
import com.jww.alarm.R
import com.jww.alarm.bases.BaseActivity
import com.jww.alarm.databinding.FragmentAlarmPlayBinding

class AlarmLockScreenActivity : BaseActivity() {

    private var _binding: FragmentAlarmPlayBinding? = null
    private val binding
        get() = _binding!!

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLockScreen()
        _binding = FragmentAlarmPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sound = intent?.getBooleanExtra("sound", true)
        val vibration = intent?.getBooleanExtra("vibration", true)
        startSound(sound!!)
        if (vibration!!) {
            (applicationContext as App).startVibrator()
        }
        binds()
    }

    private fun binds() {
        binding.btnCloseSound.setOnClickListener {
            mediaPlayer?.stop()
            this.finish()
        }
    }

    private fun initLockScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            keyguardManager.requestDismissKeyguard(this, null)
        } else {
            this.window.addFlags(
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun startSound(isSound: Boolean) {
        if (isSound) {
            mediaPlayer = MediaPlayer.create(this, R.raw.sound01_45)
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        Log.d("Won", "backPressed")
    }
}