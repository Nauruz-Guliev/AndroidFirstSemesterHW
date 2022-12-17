package ru.kpfu.itis.hw_android_2022.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.kpfu.itis.hw_android_2022.databinding.SettingsFragmentBinding

class SettingsFragment: Fragment() {
    private var _binding: SettingsFragmentBinding? = null
    private val binding by lazy { _binding!! }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val ARG_TEXT = "ARG_TEXT"
        fun createBundle(message: String) = bundleOf(
            ARG_TEXT to message
        )
    }
}