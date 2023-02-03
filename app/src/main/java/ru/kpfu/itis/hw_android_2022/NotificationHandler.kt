package ru.kpfu.itis.hw_android_2022

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat


class NotificationHandler(private val context: Context) {

    private var notificationManager: NotificationManager? = null

    fun createNotification(headerText: String?, messageBodyText: String?, messageBodyTextDetailed: String?, intent: PendingIntent) {
        createNotificationChannel()
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_outlet_24)
            .setContentTitle(headerText)
            .setContentText(messageBodyText)
            .setSubText(messageBodyTextDetailed)
            .setContentIntent(intent)
            .setCategory(Notification.CATEGORY_MESSAGE)
            .build()
        notificationManager?.notify(NOTIFICATION_ID, notificationBuilder)
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager?.getNotificationChannel(CHANNEL_ID) == null) {
                val name: CharSequence = context.getString(R.string.channel_name)
                val description = context.getString(R.string.channel_description)
                val importance = NotificationManager.IMPORTANCE_HIGH
                val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
                mChannel.description = description
                notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager?.createNotificationChannel(mChannel)
            }

        }
    }

    companion object {
        private const val CHANNEL_ID = "NOTIFICATION_CHANNEL_ID"
        // если захотим отобразить несколько уведомлений, то придётся использовать другой ID
        private const val NOTIFICATION_ID = 123
    }
}