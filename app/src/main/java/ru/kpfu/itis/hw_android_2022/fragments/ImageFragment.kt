package ru.kpfu.itis.hw_android_2022.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import kotlinx.coroutines.*
import ru.kpfu.itis.hw_android_2022.databinding.ImageFragmentBinding
import ru.kpfu.itis.hw_android_2022.load
import ru.kpfu.itis.hw_android_2022.preloadImage

class ImageFragment : Fragment() {

    private var _binding: ImageFragmentBinding? = null
    private val binding by lazy { _binding!! }
    private var imageUrl: String? = null
    private var glide: RequestManager? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ImageFragmentBinding.inflate(layoutInflater)
        imageUrl = arguments?.getString(imageUrl)
        arguments?.let {
            imageUrl = it.getString(IMAGE_URL_KEY)
        }
        glide = Glide.with(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            preloadImage()
            withContext(Dispatchers.Main) {
                displayImage()
            }
        }
    }

    private suspend fun preloadImage() =
        withContext(lifecycleScope.coroutineContext) {
            delay(DELAY_TIME)
            glide?.preloadImage(imageUrl)
        }

    private fun displayImage() {
        with(binding) {
            ivCat.load(
                imageUrl = imageUrl,
                glide = glide,
                progressBar = progressbar
            )
        }
    }

    companion object {
        private const val DELAY_TIME = 3000L
        private const val IMAGE_URL_KEY = "IMAGE_URL_KEY"
        fun createInstance(imageUrl: String) =
            ImageFragment().apply {
                this.arguments = bundleOf(
                    IMAGE_URL_KEY to imageUrl,
                )
            }
    }
}