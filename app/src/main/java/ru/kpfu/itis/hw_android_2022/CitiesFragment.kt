package ru.kpfu.itis.hw_android_2022

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.kpfu.itis.hw_android_2022.adapters.CitiesListAdapter
import ru.kpfu.itis.hw_android_2022.dao.CitiesRepository
import ru.kpfu.itis.hw_android_2022.databinding.CitiesFragmentBinding

class CitiesFragment : Fragment(R.layout.cities_fragment) {

    private var _binding: CitiesFragmentBinding?= null
    private val binding by lazy { _binding!! }

    private var adapter: CitiesListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = CitiesFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = CitiesListAdapter()
        binding.rvCities.adapter = adapter
        Toast.makeText(context, "sadasd", Toast.LENGTH_SHORT).show()
        adapter?.submitList(CitiesRepository.cities)
    }

    companion object {
        const val TAG = "CITIES_FRAGMENT"
        fun createInstance(arguments: Bundle?) = CitiesFragment().apply {
            this.arguments = arguments
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}