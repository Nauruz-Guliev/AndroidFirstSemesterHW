package com.example.androidclass.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.androidclass.viewpager.ViewPagerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kpfu.itis.hw_android_2022.databinding.FragmentFirstBinding
import ru.kpfu.itis.hw_android_2022.load
import ru.kpfu.itis.hw_android_2022.preloadImage

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding: FragmentFirstBinding get() = _binding!!

    private val glide by lazy { Glide.with(this) }
    private val adapter = ViewPagerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        initAdapter()
        with(binding) {
            btnPreload.setOnClickListener {
                preload(adapter.imageUrl.toList())
            }
        }

        return binding.root
    }

    private fun initAdapter() {
        adapter.onWork = ::someWork
        binding.viewpager.adapter = adapter
    }

    private fun someWork(url: String, imageView: ImageView, pb: ProgressBar) {
        lifecycleScope.launch(Dispatchers.IO) {
            delay(INITIAL_DELAY)
            glide.preloadImage(url)
            withContext(Dispatchers.Main) {
                imageView.load(url, glide, pb)
            }
        }
    }

    private fun preload(list: List<String>) {
        lifecycleScope.launch(Dispatchers.IO) {
            list.chunked(list.size / CHUNK_SIZE).forEach { chunkedPart ->
                launch {
                    chunkedPart.forEach {
                        glide.preloadImage(it)
                    }
                }
            }
        }
    }

    companion object {
        private const val INITIAL_DELAY = 2000L
        private const val CHUNK_SIZE = 3
    }
}
