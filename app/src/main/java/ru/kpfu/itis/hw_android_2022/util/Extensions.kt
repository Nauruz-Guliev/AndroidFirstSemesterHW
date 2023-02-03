package ru.kpfu.itis.hw_android_2022

import android.Manifest
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun<T: Any> Context.showToast(message: T) =
    Toast.makeText(this, message.toString(), Toast.LENGTH_SHORT).show()

fun View.showSnackbar(
    msg: String,
    length: Int,
    actionMessage: CharSequence?,
    action: (View) -> Unit
) {
    val snackbar = Snackbar.make(this, msg, length)
    if (actionMessage != null) {
        snackbar.setAction(actionMessage) {
            action(this)
        }.show()
    } else {
        snackbar.show()
    }
}

fun <T> Context.isServiceRunning(service: Class<T>): Boolean {
    return (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
        .getRunningServices(Integer.MAX_VALUE)
        .any { it -> it.service.className == service.name }
}


fun Fragment.showAlert(
    title: String,
    message: String,
    positiveAction: Pair<String, (() -> Unit)?>,
    negativeAction: Pair<String, (() -> Unit)?>?
) = AlertDialog.Builder(this.requireContext())
    .setTitle(title)
    .setMessage(message)
    .setPositiveButton(positiveAction.first) { _, _ -> positiveAction.second?.invoke() }
    .setNegativeButton(negativeAction?.first) { _, _ -> negativeAction?.second?.invoke() }
    .create()
    .show()

fun Context.isLocationPermissionGranted() =
    !(ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED)
