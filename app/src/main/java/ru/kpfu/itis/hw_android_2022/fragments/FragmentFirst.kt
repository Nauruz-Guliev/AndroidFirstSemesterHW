package ru.kpfu.itis.hw_android_2022.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import ru.kpfu.itis.hw_android_2022.R
import ru.kpfu.itis.hw_android_2022.Repository
import ru.kpfu.itis.hw_android_2022.comparator.ModelsComparator
import ru.kpfu.itis.hw_android_2022.data.RandomItemsGenerator
import ru.kpfu.itis.hw_android_2022.databinding.FragmentFirstBinding
import ru.kpfu.itis.hw_android_2022.models.FirstItem
import ru.kpfu.itis.hw_android_2022.models.SecondItem
import ru.kpfu.itis.hw_android_2022.models.ThirdItem
import ru.kpfu.itis.hw_android_2022.models.UIModel
import ru.kpfu.itis.hw_android_2022.recyclerviewComponents.MusicAdapter
import ru.kpfu.itis.hw_android_2022.recyclerviewComponents.SwipeListener


class FragmentFirst : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding by lazy { _binding!! }

    private var fbAddItem: FloatingActionButton? = null
    private var adapter: MusicAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(layoutInflater)
        fbAddItem = requireActivity().findViewById(R.id.fb_add_random_item)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initFloatingAddButton()
    }

    private fun initRecyclerView(){
        with(binding) {
            adapter = MusicAdapter(context = root.context,
                musicArrayList = Repository.getDataList(root.context).toMutableList(),
                glide = Glide.with(root.context),
                onDeleteClick = ::onDeleteClicked,
                onFirstItemClicked = ::onFirstItemClicked
                )
            rvFirstFragment.adapter = adapter

            SwipeListener(requireView()).attachToRecyclerView(rvFirstFragment)
        }
    }
    private fun initFloatingAddButton(){
        fbAddItem?.setOnClickListener {
            val position = Repository.addRandomItem()
            adapter?.setData(Repository.getDataList(binding.root.context).toMutableList())
            Log.d("values", "size: " + Repository.getDataList(binding.root.context).size.toString() +"position: " + position)
        }
    }

    private fun onDeleteClicked(viewHolder: ViewHolder, ) {
        val deletedItem: UIModel =
            Repository.getDataList(binding.root.context)[viewHolder.adapterPosition]

        val position = viewHolder.adapterPosition

        Repository.deleteItem(position)
      //  adapter?.notifyItemRemoved(viewHolder.adapterPosition)
        adapter?.setData(Repository.getDataList(binding.root.context).toMutableList())
        Snackbar.make(binding.root, "Item has been deleted", Snackbar.LENGTH_LONG)
            .setAction(
                "Undo"
            ) {
                Repository.addItem(deletedItem, position)
                adapter?.setData(Repository.getDataList(binding.root.context).toMutableList())
               // adapter?.notifyItemInserted(position)
            }.show()

        viewHolder.itemView.scrollTo(0,0)
    }

    private fun onFirstItemClicked(position: Int) {
        val arrayURLS = resources.getTextArray(R.array.random_image_urls)
        val item = (Repository.getDataList(binding.root.context)[position] as? UIModel.FirstTypeModel)
            ?.firstItem
        item?.imageUrl = arrayURLS[(arrayURLS.indices).random()] as String
        //adapter?.setData(Repository.getDataList(binding.root.context).toMutableList())

        adapter?.notifyItemChanged(position)
        Log.d("value", (Repository.getDataList(binding.root.context)[position] as? UIModel.FirstTypeModel)
            ?.firstItem?.imageUrl.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle) =
            FragmentFirst().apply {
                arguments = Bundle().apply {
                    putAll(bundle)
                }
            }
    }
}