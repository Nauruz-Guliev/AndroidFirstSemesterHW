package ru.kpfu.itis.hw_android_2022

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kpfu.itis.hw_android_2022.databinding.CameraFragmentBinding

class CameraFragment: Fragment(R.layout.camera_fragment) {
    private var _binding: CameraFragmentBinding? = null
    private val binding by lazy { _binding!! }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CameraFragmentBinding.inflate(layoutInflater)
        return binding.root
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