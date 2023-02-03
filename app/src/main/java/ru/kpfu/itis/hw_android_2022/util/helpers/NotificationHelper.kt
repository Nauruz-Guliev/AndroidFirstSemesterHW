package ru.kpfu.itis.hw_android_2022.util.helpers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import androidx.core.app.NotificationCompat
import ru.kpfu.itis.hw_android_2022.LocationService
import ru.kpfu.itis.hw_android_2022.MainActivity
import ru.kpfu.itis.hw_android_2022.R


class NotificationHelper(private val context: Context) {

    private var notificationBuilder:NotificationCompat.Builder? = null
    private var notificationManager: NotificationManager? = null

    init {
        notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    fun getNotificationBuilder(service: LocationService, title: String, content: String): NotificationCompat.Builder? {
        val notificationPendingIntent = PendingIntent.getActivity(
            service, NOTIFICATION_REQUEST_CODE, Intent(context, MainActivity::class.java), PENDING_INTENT_FLAG
        )
        createChannel()
        notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_baseline_location_on_24)
            .setContentIntent(notificationPendingIntent)
        return notificationBuilder
    }

    fun updateNotificationTitle(title: String) {
        notificationBuilder?.setContentTitle(title)
        notificationManager?.notify(NOTIFICATION_ID, notificationBuilder?.build())
    }

    fun updateNotificationContent(content: String) {
        notificationBuilder?.setContentText(content)
        notificationManager?.notify(NOTIFICATION_ID, notificationBuilder?.build())
    }

    private fun createChannel () {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL"
        const val NOTIFICATION_REQUEST_CODE = 123
        const val NOTIFICATION_ID = 321
        val PENDING_INTENT_FLAG = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
    }
}