package ru.kpfu.itis.hw_android_2022.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Build.VERSION
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class CustomContract :
    ActivityResultContract<ScanOptions, Pair<ScanIntentResult, Bitmap?>?>() {

    override fun createIntent(context: Context, input: ScanOptions): Intent {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val scanIntent = input.createScanIntent(context)
        val chooserIntent = Intent.createChooser(
            scanIntent, "Choose"
        )
        chooserIntent?.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
        return chooserIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Pair<ScanIntentResult, Bitmap?>? =
        if (resultCode == Activity.RESULT_OK) {
            ScanIntentResult.parseActivityResult(
                resultCode,
                intent
            ) to if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent?.getParcelableExtra("data", Bitmap::class.java)
            } else {
                intent?.getParcelableExtra("data") as Bitmap?
            }
        } else null
}