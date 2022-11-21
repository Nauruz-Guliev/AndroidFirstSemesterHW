package ru.kpfu.itis.hw_android_2022.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.kpfu.itis.hw_android_2022.R
import ru.kpfu.itis.hw_android_2022.adapters.CitiesListAdapter
import ru.kpfu.itis.hw_android_2022.dao.CitiesRepository
import ru.kpfu.itis.hw_android_2022.databinding.CitiesFragmentBinding
import ru.kpfu.itis.hw_android_2022.models.SortModel
import ru.kpfu.itis.hw_android_2022.util.getParcelable
import ru.kpfu.itis.hw_android_2022.util.showToast

class CitiesFragment : Fragment(R.layout.cities_fragment) {

    private var _binding: CitiesFragmentBinding? = null
    private val binding by lazy { _binding!! }

    private var adapter: CitiesListAdapter? = null

    private var selectedSort: SortModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = CitiesFragmentBinding.bind(view)
        initRecyclerView()
        initButtonListener()
        initFragmentResultListener()
    }

    private fun initFragmentResultListener() {
        childFragmentManager.setFragmentResultListener(
            BottomSheetFragment.REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            selectedSort =
                getParcelable(bundle, BottomSheetFragment.RESULT_EXTRA_KEY, SortModel::class.java)
            CitiesRepository.setSelectedSort(selectedSort)
            adapter?.submitList(CitiesRepository.cities)
        }
    }


    private fun initButtonListener() {
        showToast("asdasdasd")
        binding.btnBottomSheet.setOnClickListener {
            //создаём bottomsheet и передаём текущую сортировку из репозитория
            //чтобы выбранной кнопкой была та, которая соответсвует сортировке
            BottomSheetFragment.createInstance(CitiesRepository.getSelectedSort()).show(
                childFragmentManager,
                BottomSheetFragment.TAG
            )
        }
    }

    private fun initRecyclerView() {
        adapter = CitiesListAdapter()
        adapter?.submitList(CitiesRepository.cities)
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