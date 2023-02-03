package ru.kpfu.itis.hw_android_2022

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.kpfu.itis.hw_android_2022.models.ActionsEnum.START
import ru.kpfu.itis.hw_android_2022.models.ActionsEnum.STOP
import ru.kpfu.itis.hw_android_2022.util.helpers.NotificationHelper

class LocationService : Service(), LocationListener {

    private var notificationHelper: NotificationHelper? = null
    private var locationManager: LocationManager? = null

    override fun onCreate() {
        super.onCreate()
        notificationHelper = NotificationHelper(baseContext)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            START.name -> startLocationService()
            STOP.name -> stopLocationService()
        }
        return START_NOT_STICKY
    }

    private fun startLocationService() {
        val notification = notificationHelper?.getNotificationBuilder(
            service = this,
            title = getString(R.string.notification_title),
            content = getString(R.string.notification_content)
        )?.build()
        startForeground(NotificationHelper.NOTIFICATION_ID, notification)
        getLocationCoordinates()
    }

    //произвожу тут ту же самую проверку, но студия отказывается её видеть
    @SuppressLint("MissingPermission")
    private fun getLocationCoordinates() {
        if (!baseContext.isLocationPermissionGranted()) startBroadCast(
            createIntent(
                message = getString(R.string.permission_error),
                location = null
            )
        ) else {
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1f, this)
        }
    }

    private fun stopLocationService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(STOP_FOREGROUND_REMOVE)
        } else {
            stopForeground(true)
        }
        locationManager?.removeUpdates(this)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        notificationHelper = null
        locationManager = null
    }

    private fun startBroadCast(intent: Intent){
        LocalBroadcastManager.getInstance(baseContext).sendBroadcast(intent)
    }

    private fun createIntent(message: String?, location: Location?) = Intent(LOCATION_INTENT_KEY).apply {
        putExtra(LOCATION_KEY, location)
        putExtra(MESSAGE_KEY, message)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onLocationChanged(location: Location) {
        startBroadCast(createIntent(null, location))
        notificationHelper?.run {
            updateNotificationTitle(
                getString(R.string.service_status)
            )
            updateNotificationContent(
                getString(
                    R.string.location,
                    location.latitude.toString(),
                    location.longitude.toString()
                )
            )
        }
    }

    companion object {
        const val LOCATION_INTENT_KEY = "LOCATION_INTENT_KEY"
        const val LOCATION_KEY = "LOCATION_KEY"
        const val MESSAGE_KEY = "MESSAGE_KEY"
    }
}