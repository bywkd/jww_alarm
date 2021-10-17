package com.jww.alarm.services

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.core.app.NotificationCompat
import com.jww.alarm.R
import com.jww.alarm.receiver.AlarmReceiver

//https://medium.com/mj-studio/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%96%B4%EB%94%94%EA%B9%8C%EC%A7%80-%EC%95%84%EC%84%B8%EC%9A%94-2-1-service-foreground-service-e19cf74df390
class AlarmBackgroundService : Service() {

    companion object {
        const val NOTIFICATION_ID = 300
        const val CHANNEL_ID = "notification_channel"
    }

    lateinit var builder: NotificationCompat.Builder
    lateinit var notificationManager: NotificationManager
    private lateinit var vib: Vibrator

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        vib = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        Log.d("Won", "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("Won", "BackgroundService.onStartCommand")
        createNotification(intent!!, this)
        startForeground(NOTIFICATION_ID, builder.build())
        stopForeground(true)
        stopSelf()

        notificationManager.notify(NOTIFICATION_ID, builder.build())
        startVibrator(this)
        return START_STICKY
    }


    @SuppressLint("WrongConstant")
    private fun createNotification(intent: Intent, context: Context) {

        when (intent.action) {
            Intent.ACTION_SCREEN_OFF -> {
                Log.d("Won", "ScreenOff")
            }
            Intent.ACTION_SCREEN_ON -> {
                Log.d("Won", "ScreenOn")
            }
        }
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(
                    CHANNEL_ID,
                    "jwwAlarm",
                    NotificationManager.IMPORTANCE_HIGH
                )

            notificationChannel.run {
//                vibrationPattern = longArrayOf(
//                    1000L,
//                    59000L,
//                    1000L,
//                    59000L,
//                    1000L,
//                    59000L,
//                    1000L,
//                    59000L,
//                    1000L,
//                    59000L
//                )//대기 시간, 진동시간
                enableVibration(true)
                enableLights(false)
                lightColor = Color.GREEN
                lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            }

            notificationManager.createNotificationChannel(notificationChannel)

            builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("알람 테스트")
                .setContentText("알람 시간")
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)

        } else {

            builder = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("알람 테스트")
                .setContentText("알람 시간")
                .setVibrate(longArrayOf(0L))
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
        }
        builder.build().flags = Notification.FLAG_INSISTENT
    }

    private fun startVibrator(context: Context) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val audioAttributes = AudioAttributes.Builder().build()
            vib.vibrate(
                VibrationEffect.createOneShot(600000, 100)
            )
        } else {
            vib.vibrate(200000)
        }
    }
}