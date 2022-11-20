package ru.kpfu.itis.hw_android_2022.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.kpfu.itis.hw_android_2022.R
import ru.kpfu.itis.hw_android_2022.databinding.BottomSheetFragmentBinding
import ru.kpfu.itis.hw_android_2022.models.SortModel
import ru.kpfu.itis.hw_android_2022.util.getParcelable

class BottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: BottomSheetFragmentBinding? = null
    private val binding by lazy { _binding!! }

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    private var currentSort: SortModel? = null

    private var buttonChecked: Button? = null

    private var selectedSort: SortModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetFragmentBinding.inflate(layoutInflater)
        currentSort = getParcelable(arguments, ARGUMENT_KEY, SortModel::class.java) as SortModel?
        return binding.root
    }

    private fun checkButton(@IdRes buttonId: Int) {
        binding.toggleGroup.check(buttonId)
        buttonChecked = activity?.findViewById(buttonId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        when (currentSort) {
            SortModel.ID_ASC -> checkButton(R.id.btn_id_asc)
            SortModel.ID_DESC -> checkButton(R.id.btn_id_desc)
            SortModel.NAME_DESC -> checkButton(R.id.btn_name_desc)
            SortModel.NAME_ASC -> checkButton(R.id.btn_name_asc)
            else -> {}
        }
    }

    private fun initListeners() =
        with(binding) {
            toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
                if (isChecked) {
                    selectedSort = when (checkedId) {
                        R.id.btn_id_asc -> SortModel.ID_ASC
                        R.id.btn_id_desc -> SortModel.ID_DESC
                        R.id.btn_name_desc -> SortModel.NAME_DESC
                        R.id.btn_name_asc -> SortModel.NAME_ASC
                        else -> currentSort
                    }
                }
            }
            btnApply.setOnClickListener {
                selectedSort = selectedSort ?: currentSort
                parentFragmentManager.setFragmentResult(
                    REQUEST_KEY,
                    bundleOf(RESULT_EXTRA_KEY to selectedSort)
                )
                dismiss()
            }
        }

    companion object {
        const val TAG = "BOTTOM_SHEET_FRAGMENT"
        const val REQUEST_KEY = "SORT_KEY"
        const val RESULT_EXTRA_KEY = "RESULT_EXTRA_KEY"
        private const val ARGUMENT_KEY = "ARGS_KEY"
        fun createInstance(selectedSort: SortModel?) = BottomSheetFragment().apply {
            this.arguments = bundleOf(ARGUMENT_KEY to selectedSort)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}