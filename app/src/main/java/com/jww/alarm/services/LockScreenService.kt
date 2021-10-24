package com.jww.alarm.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.jww.alarm.R
import com.jww.alarm.views.alarmPlayView.AlarmLockScreenView

class LockScreenService : Service() {

    companion object {
        val NOTIFICATION_ID = 8888
        val CHANNEL_ID = "LockScreenService"
    }

    lateinit var builder: NotificationCompat.Builder
    lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()
        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            createNotification(it, this)
        }
        startLockScreenActivity()
        startForeground(NOTIFICATION_ID, builder.build())
        stopForeground(true)
        stopSelf()

//        notificationManager.notify(NOTIFICATION_ID, builder.build())

        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun createNotification(intent: Intent, context: Context) {

        when (intent.action) {
            Intent.ACTION_SCREEN_OFF -> {
                Log.d("Won", "ScreenOff")
            }
            Intent.ACTION_SCREEN_ON -> {
                Log.d("Won", "ScreenOn")
            }
        }

        val pullIntent = Intent(this, AlarmLockScreenView::class.java)

        val pendingIntent =
            PendingIntent.getActivity(context, 0, pullIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(
                    CHANNEL_ID,
                    "jwwAlarmLcok",
                    NotificationManager.IMPORTANCE_HIGH
                )


            notificationManager.createNotificationChannel(notificationChannel)

            builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("락스크린 테스트")
                .setContentText("락스크린 시간")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        } else {

            builder = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("락스크린 테스트")
                .setContentText("락스크린 시간")
                .setVibrate(longArrayOf(0L))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        }
        builder.build().flags = Notification.FLAG_INSISTENT
    }

    private fun startLockScreenActivity() {
        Log.d("Won","startLockScreenActivity")
        val intent = Intent(applicationContext, AlarmLockScreenView::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        applicationContext.startActivity(intent)

    }
}