package ru.kpfu.itis.hw_android_2022.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kpfu.itis.hw_android_2022.R
import ru.kpfu.itis.hw_android_2022.adapters.CitiesListAdapter
import ru.kpfu.itis.hw_android_2022.dao.CitiesRepository
import ru.kpfu.itis.hw_android_2022.databinding.CitiesFragmentBinding
import ru.kpfu.itis.hw_android_2022.models.SortModel
import ru.kpfu.itis.hw_android_2022.util.getParcelable

class CitiesFragment : Fragment(R.layout.cities_fragment) {

    private var _binding: CitiesFragmentBinding? = null
    private val binding by lazy { _binding!! }

    private var adapter: CitiesListAdapter? = null

    private var selectedSort: SortModel? = null

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
        initButtonListener()
        initFragmentResultListener()
    }

    private fun initFragmentResultListener() {
        parentFragmentManager.setFragmentResultListener(
            BottomSheetFragment.REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            selectedSort = getParcelable(
                bundle,
                BottomSheetFragment.RESULT_EXTRA_KEY,
                SortModel::class.java
            ) as SortModel?
            CitiesRepository.setSelectedSort(
                selectedSort
            )
            adapter?.submitList(CitiesRepository.getSortedList())
        }
    }


    private fun initButtonListener() {
        binding.btnBottomSheet.setOnClickListener {
            BottomSheetFragment.createInstance(CitiesRepository.getSelectedSort()).show(
                parentFragmentManager,
                BottomSheetFragment.TAG
            )
        }
    }

    private fun initRecyclerView() {
        adapter = CitiesListAdapter()
        adapter?.submitList(CitiesRepository.getSortedList())
        binding.rvCities.adapter = adapter
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