package ru.kpfu.itis.hw_android_2022.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import ru.kpfu.itis.hw_android_2022.R
import ru.kpfu.itis.hw_android_2022.databinding.ContainerFragmentBinding
import ru.kpfu.itis.hw_android_2022.preloadImage
import ru.kpfu.itis.hw_android_2022.showToast
import ru.kpfu.itis.hw_android_2022.viewpager.ViewPagerAdapter

class ContainerFragment : Fragment() {
    private var _binding: ContainerFragmentBinding? = null
    private val binding by lazy { _binding!! }

    private var viewPagerAdapter: ViewPagerAdapter? = null
    private var catsArray: List<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ContainerFragmentBinding.inflate(layoutInflater)
        catsArray = resources.getStringArray(R.array.cats_array).toList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initButtonClickListener()
    }

    private fun initButtonClickListener() {
        binding.btnPreloadAll.setOnClickListener {
            loadImages(
                chunkSize = CHUNK_SIZE,
                onSuccess = { showToast(getString(R.string.success)) },
                onError = { showToast(getString(R.string.error)) })
        }
    }

    // возможно, я неправильно понял
    // 3 списка, все 3 выполняются асинхронно и внутри 3 списков ссылки тоже загружаются асинхронно
    private fun loadImages(chunkSize: Int, onSuccess: () -> Unit, onError: () -> Unit) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val jobs = mutableListOf<Job>()
                val chunkedUrlsList = catsArray?.chunked(catsArray?.size?.div(chunkSize) ?: 1)
                //группами ставим асинхронно на закачку
                chunkedUrlsList?.forEach { chunkedList ->
                    jobs.add(async { loadImages(chunkedList) })
                }
                //но при этом ждём, пока все группы закончат и выводим сообщение
                jobs.joinAll()
                withContext(Dispatchers.Main) {
                    onSuccess.invoke()
                }
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    onError.invoke()
                }
            }
        }
    }

    private fun loadImages(images: List<String>) {
        images.forEach { imageUrl ->
            lifecycleScope.launch(Dispatchers.IO) {
                Glide.with(this@ContainerFragment).preloadImage(imageUrl)
            }
        }
    }


    private fun initViewPager() {
        val fragmentsList = mutableListOf<Fragment>()
        catsArray?.forEach {
            fragmentsList.add(ImageFragment.createInstance(it))
        }
        viewPagerAdapter = ViewPagerAdapter(
            activity = requireActivity(),
            fragments = fragmentsList
        )
        binding.viewpager.adapter = viewPagerAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val CHUNK_SIZE = 3;
    }
}