package ru.kpfu.itis.hw_android_2022.recyclerviewComponents

import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import ru.kpfu.itis.hw_android_2022.models.UIModel

class RVDiffUtil(private val oldList: List<UIModel>, private val newList: List<UIModel>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = newList[newItemPosition]
        val oldItem = oldList[oldItemPosition]
        if (oldItem is UIModel.FirstTypeModel && newItem is UIModel.FirstTypeModel) {
            Log.d("valuessss", oldItem.firstItem.imageUrl  +" : "+ newItem.firstItem.imageUrl)
            return oldItem.firstItem.imageUrl == newItem.firstItem.imageUrl
        }
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}