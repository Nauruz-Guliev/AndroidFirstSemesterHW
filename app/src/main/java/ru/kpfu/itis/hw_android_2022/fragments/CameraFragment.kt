package ru.kpfu.itis.hw_android_2022.fragments


import android.Manifest.permission.CAMERA
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import ru.kpfu.itis.hw_android_2022.CaptureActivity
import ru.kpfu.itis.hw_android_2022.R
import ru.kpfu.itis.hw_android_2022.databinding.CameraFragmentBinding
import ru.kpfu.itis.hw_android_2022.util.*


class CameraFragment : Fragment(R.layout.camera_fragment) {
    private var _binding: CameraFragmentBinding? = null
    private val binding by lazy { _binding!! }

    private var qrCodeUrl: String? = null

    private val permissionsRequestHandler =
        PermissionsRequestHandler(fragment = this) { isGranted ->
            if (isGranted) customLauncher.launch(scanOptions)
            else onClickRequestPermission()
        }


    private val customLauncher = registerForActivityResult(CustomContract()) {
        if (it?.first?.contents != null) {
            showToast(it.first)
            qrCodeUrl = it.first.contents
            showAlert(
                title = getString(R.string.url_alert_title),
                message = getString(R.string.url_alert_message),
                positiveAction = getString(R.string.url_alert_positive_choice) to ::openUrlInBrowser,
                negativeAction = getString(R.string.url_alert_negative_choice) to ::openUrlInWebView
            )
        } else if (it?.second != null) {
            val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)
            parentFragmentManager.setFragmentResult(
                PHOTO_REQUEST_KEY,
                bundleOf(PHOTO_RESULT_KEY to it.second)
            )
            viewPager?.post {
                viewPager.currentItem = 2
            }
        }
    }

    private var scanOptions: ScanOptions? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initScanOptions()
        _binding = CameraFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonListener()
    }

    private fun initButtonListener() {
        with(binding) {
            btnCamera.setOnClickListener {
                permissionsRequestHandler.requestSinglePermission(CAMERA)
            }
        }
    }

    private fun onClickRequestPermission() {
        //если разрешение есть
        if (ContextCompat.checkSelfPermission(
                binding.root.context,
                CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //barcodeLauncher.launch(scanOptions)
            customLauncher.launch(
                scanOptions
            )
        } else {
            // если запретили один раз
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), CAMERA)) {
                view?.showSnackbar(
                    getString(R.string.permission_required),
                    Snackbar.LENGTH_SHORT,
                    getString(R.string.ok)
                ) {
                    permissionsRequestHandler.requestSinglePermission(CAMERA)
                }
                //после 2-ух запретов
            } else {
                showAlert(
                    title = getString(R.string.permission_required_title, getString(R.string.camera)),
                    message = getString(R.string.permission_required_message, getString(R.string.camera)),
                    positiveAction = getString(R.string.action_message) to ::openSettings,
                    negativeAction = null
                )
            }
        }
    }

    private fun openSettings(){
        val settingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", requireContext().packageName, null)
        )
        startActivity(settingsIntent)
    }

    private fun initScanOptions() {
        scanOptions = ScanOptions()
            .setCameraId(0)
            .setBeepEnabled(false)
            .setOrientationLocked(true)
            .setPrompt(getString(R.string.scan_prompt_title))
            .setBarcodeImageEnabled(true)
            .setCaptureActivity(CaptureActivity::class.java)

    }

    private fun openUrlInBrowser() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(qrCodeUrl?.checkUrl()))
        startActivity(browserIntent)
    }

    //интересно, а как же обрабатывать xss уязвимости на уровне андроида??
    //я всё-таки хочу жаваскрипт в у себя в webview
    private fun openUrlInWebView() {
        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            qrCodeUrl?.checkUrl()?.let { loadUrl(it) }
        }
    }

    companion object {
        const val TAG = "CAMERA_FRAGMENT"
        const val PHOTO_REQUEST_KEY = "PHOTO_REQUEST_KEY"
        const val PHOTO_RESULT_KEY = "PHOTO_RESULT_KEY"
        fun createInstance(arguments: Bundle?) = CameraFragment().apply {
            this.arguments = arguments
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}