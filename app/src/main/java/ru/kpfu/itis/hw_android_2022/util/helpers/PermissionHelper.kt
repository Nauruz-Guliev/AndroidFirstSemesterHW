package ru.kpfu.itis.hw_android_2022.util.helpers

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.kpfu.itis.hw_android_2022.R
import ru.kpfu.itis.hw_android_2022.showAlert
import ru.kpfu.itis.hw_android_2022.showSnackbar

class PermissionHelper(
    private val fragment: Fragment,
    private val permissionGrantedCallback: ((Boolean) -> Unit )
    ) {

    val permissionsRequestHandler = fragment.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionGrantedCallback.invoke(isGranted)
    }

    fun onClickRequestPermission(permission: String) {
        //если разрешение есть
        if (ContextCompat.checkSelfPermission(
                fragment.requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            permissionGrantedCallback.invoke(true)
        } else {
            // если запретили один раз
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    fragment.requireActivity(),
                    permission
                )
            ) {
                fragment.view?.showSnackbar(
                    fragment.getString(R.string.permission_required),
                    Snackbar.LENGTH_SHORT,
                    fragment.getString(R.string.ok)
                ) {
                    permissionsRequestHandler.launch(
                        permission
                    )
                }
                //после 2-ух запретов
            } else {
                fragment.showAlert(
                    title = fragment.getString(
                        R.string.permission_required_title,
                        permission
                    ),
                    message = fragment.getString(
                        R.string.permission_required_message,
                        permission
                    ),
                    positiveAction = fragment.getString(R.string.settings_action_message) to ::openSettings,
                    negativeAction = null
                )
            }
        }
    }

    private fun openSettings() {
        val settingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", fragment.requireContext().packageName, null)
        )
        fragment.startActivity(settingsIntent)
    }
}