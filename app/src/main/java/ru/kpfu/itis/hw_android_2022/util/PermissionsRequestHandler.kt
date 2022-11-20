package ru.kpfu.itis.hw_android_2022.util

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class PermissionsRequestHandler(
    fragment: Fragment,
    var singlePermissionCallback: ((Boolean) -> Unit)
) {
    private val requestSinglePermissionLauncher = fragment.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        isGranted ->
        singlePermissionCallback.invoke(isGranted)
    }
    fun requestSinglePermission(permission: String) {
        requestSinglePermissionLauncher.launch(permission)
    }
}