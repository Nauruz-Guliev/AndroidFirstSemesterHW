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
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import ru.kpfu.itis.hw_android_2022.R
import ru.kpfu.itis.hw_android_2022.databinding.CameraFragmentBinding
import ru.kpfu.itis.hw_android_2022.util.PermissionsRequestHandler
import ru.kpfu.itis.hw_android_2022.util.checkUrl
import ru.kpfu.itis.hw_android_2022.util.showAlert
import ru.kpfu.itis.hw_android_2022.util.showSnackbar


class CameraFragment : Fragment(R.layout.camera_fragment) {
    private var _binding: CameraFragmentBinding? = null
    private val binding by lazy { _binding!! }

    private var qrCodeUrl: String? = null

    private val permissionsRequestHandler =
        PermissionsRequestHandler(fragment = this) { isGranted ->
            if (isGranted) barcodeLauncher.launch(scanOptions)
            else onClickRequestPermission(binding.root)
        }

    private val barcodeLauncher =
        registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
            if (result.contents != null) {
                qrCodeUrl = result.contents
                showAlert(
                    title = getString(R.string.url_alert_title),
                    message = getString(R.string.url_alert_message),
                    positiveAction = getString(R.string.url_alert_positive_choice) to ::openUrlInBrowser,
                    negativeAction = getString(R.string.url_alert_negative_choice) to ::openUrlInWebView
                )
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

    private fun onClickRequestPermission(view: View) {
        if (ContextCompat.checkSelfPermission(
                binding.root.context,
                CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            barcodeLauncher.launch(scanOptions)
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), CAMERA)) {
                view.showSnackbar(
                    view,
                    getString(R.string.permission_required),
                    Snackbar.LENGTH_SHORT,
                    getString(R.string.ok)
                ) {
                    permissionsRequestHandler.requestSinglePermission(CAMERA)
                }
            } else {
                showOpenSettingsAlert()
            }
        }
    }


    private fun showOpenSettingsAlert() {
        val settingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", requireContext().packageName, null)
        )
        showAlert(
            title = getString(R.string.permission_required_title, getString(R.string.camera)),
            message = getString(R.string.permission_required_message, getString(R.string.camera)),
            positiveAction = getString(R.string.action_message) to { startActivity(settingsIntent) },
            negativeAction = null
        )
    }

    private fun initScanOptions() {
        scanOptions = ScanOptions()
            .setCameraId(0)
            .setBeepEnabled(false)
            .setPrompt(getString(R.string.scan_prompt_title))
            .setBarcodeImageEnabled(true)
    }

    private fun openUrlInBrowser() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(qrCodeUrl?.checkUrl()))
        startActivity(browserIntent)
    }

    //интересно, а как же обрабатывать xss уязвимости на уровне андроида??
    private fun openUrlInWebView() {
        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            qrCodeUrl?.checkUrl()?.let { loadUrl(it) }
        }
    }

    companion object {
        const val TAG = "CAMERA_FRAGMENT"
        fun createInstance(arguments: Bundle?) = CameraFragment().apply {
            this.arguments = arguments
        }
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}