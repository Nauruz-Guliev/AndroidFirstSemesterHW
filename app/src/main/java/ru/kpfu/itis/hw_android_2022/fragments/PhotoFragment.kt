package ru.kpfu.itis.hw_android_2022.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.kpfu.itis.hw_android_2022.R
import ru.kpfu.itis.hw_android_2022.databinding.PhotoFragmentBinding
import ru.kpfu.itis.hw_android_2022.util.getParcelable


class PhotoFragment : Fragment(R.layout.photo_fragment) {
    private var _binding: PhotoFragmentBinding? = null
    private val binding by lazy { _binding!! }

    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bitmap = getParcelable(arguments, BITMAP_ARGS, Bitmap::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = PhotoFragmentBinding.bind(view)
        initFragmentResultListener()
    }

    private fun initFragmentResultListener() =
        parentFragmentManager.setFragmentResultListener(
            CameraFragment.PHOTO_REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            bitmap = getParcelable(bundle, CameraFragment.PHOTO_RESULT_KEY, Bitmap::class.java)
            bitmap = bitmap?.let { Bitmap.createScaledBitmap(it, 900, 1280, false) }
            binding.ivCameraPhoto.setImageBitmap(bitmap)
        }


    companion object {
        const val TAG = "PHOTO_FRAGMENT"
        const val BITMAP_ARGS = "BITMAP_ARGS"
        fun createInstance(arguments: Bundle?) = PhotoFragment().apply {
            arguments?.putParcelable(BITMAP_ARGS, bitmap)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}