package ru.kpfu.itis.hw_android_2022.fragments

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ru.kpfu.itis.hw_android_2022.LocationService
import ru.kpfu.itis.hw_android_2022.databinding.MainFragmentBinding
import ru.kpfu.itis.hw_android_2022.isServiceRunning
import ru.kpfu.itis.hw_android_2022.models.ActionsEnum
import ru.kpfu.itis.hw_android_2022.models.ActionsEnum.START
import ru.kpfu.itis.hw_android_2022.models.ActionsEnum.STOP
import ru.kpfu.itis.hw_android_2022.showToast
import ru.kpfu.itis.hw_android_2022.util.helpers.PermissionHelper


class MainFragment : Fragment(), OnMapReadyCallback {

    private var _binding: MainFragmentBinding? = null
    private val binding by lazy { _binding!! }

    private var locationServiceIntent: Intent? = null

    private var permissionHelper: PermissionHelper? = null
    private var mMapView: MapView? = null

    private var googleMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(layoutInflater)
        initMap(savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = MainFragmentBinding.bind(view)
        initClickListeners()
        registerReceiver()
        initLocationVariables()
    }

    private fun initLocationVariables() {
        locationServiceIntent = Intent(
            binding.root.context,
            LocationService::class.java
        )
        permissionHelper = PermissionHelper(
            this
        ) { isGranted ->
            if (isGranted) manageLocationService(START)
            else permissionHelper?.onClickRequestPermission(ACCESS_FINE_LOCATION)
        }
    }

    private fun initMap(savedInstanceState: Bundle?) {
        MapsInitializer.initialize(requireActivity())
        mMapView = binding.mapView
        mMapView?.onCreate(savedInstanceState)
        mMapView?.getMapAsync(this@MainFragment)
    }

    private fun initClickListeners() {
        with(binding) {
            btnStartLocationService.setOnClickListener {
                val isRunning = root.context.isServiceRunning(LocationService::class.java)
                if (!isRunning) {
                    permissionHelper?.permissionsRequestHandler?.launch(
                        ACCESS_FINE_LOCATION
                    )
                } else root.context.showToast("Service is already running")
            }
            btnStopLocationService.setOnClickListener {
                val isRunning = root.context.isServiceRunning(LocationService::class.java)
                if (isRunning) {
                    manageLocationService(STOP)
                } else root.context.showToast("You need to start your service first!")
            }
        }
    }

    private fun manageLocationService(action: ActionsEnum?) {
        locationServiceIntent?.action = action?.name
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity?.startForegroundService(locationServiceIntent)
        } else {
            activity?.startService(locationServiceIntent)
        }
    }

    private val locationServiceReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val location = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent?.getParcelableExtra(LocationService.LOCATION_KEY, Location::class.java)
            } else {
                intent?.getParcelableExtra(LocationService.LOCATION_KEY) as? Location
            }
            val message = intent?.getStringExtra(LocationService.MESSAGE_KEY)
            when {
                location != null -> setMapLocation(location)
                message != null -> binding.root.context.showToast(message)
            }
        }
    }

    private fun setMapLocation(location: Location) {
        val latlng = LatLng(
            location.latitude,
            location.longitude
        )
        googleMap?.addMarker(
            MarkerOptions()
                .position(latlng).title("I'm here =)")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                .draggable(false).visible(true)
        )
        googleMap?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                latlng,
                20F
            )
        )
    }

    private fun registerReceiver() {
        LocalBroadcastManager.getInstance(binding.root.context).registerReceiver(
            locationServiceReceiver, IntentFilter(
                LocationService.LOCATION_INTENT_KEY
            )
        )
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        map.uiSettings.isMyLocationButtonEnabled = true
        map.moveCamera(
            CameraUpdateFactory.newLatLng(
                LatLng(
                    50.0,
                    50.0
                )
            )
        )
    }

    override fun onStart() {
        super.onStart()
        mMapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMapView?.onStop()
    }

    override fun onResume() {
        super.onResume()
        mMapView?.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mMapView?.onDestroy()
        activity?.unregisterReceiver(locationServiceReceiver)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMapView?.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView!!.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        permissionHelper = null
    }

    companion object {
        const val FRAGMENT_NAME = "MAIN_FRAGMENT"
    }
}