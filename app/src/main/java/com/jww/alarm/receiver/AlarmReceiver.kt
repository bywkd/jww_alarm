package com.jww.alarm.receiver

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import com.jww.alarm.R

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID = 0
        const val CHANNEL_ID = "notification_channel"

    }

    lateinit var builder: Notification.Builder
    lateinit var notificationManager: NotificationManager
    private lateinit var vib: Vibrator


    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            notificationManager =
                it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            startVibrator(it)
//            createNotification(intent!!, it)
        }

        Log.d("Won", "================================")
    }

    private fun startVibrator(context: Context) {

        vib = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val audioAttributes = AudioAttributes.Builder().build()
            vib.vibrate(
                VibrationEffect.createOneShot(10000, 100)
            )
        } else {
            vib.vibrate(200000)
        }
    }

    @SuppressLint("WrongConstant")
    private fun createNotification(intent: Intent, context: Context) {
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(CHANNEL_ID, "description", NotificationManager.IMPORTANCE_HIGH)

            notificationChannel.run {
//                vibrationPattern = longArrayOf(100L,2000L,100L,2000L,100L,2000L,100L,2000L)//대기 시간, 진동시간
                enableVibration(true)
                enableLights(false)
                lightColor = Color.GREEN
            }


            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("알람 테스트")
                .setContentText("알람 시간")
                .setContentIntent(pendingIntent)
        } else {

            builder = Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("알람 테스트")
                .setContentText("알람 시간")
                .setVibrate(longArrayOf(0L))
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

}