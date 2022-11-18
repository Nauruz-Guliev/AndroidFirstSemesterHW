package ru.kpfu.itis.hw_android_2022.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import ru.kpfu.itis.hw_android_2022.CitiesViewHolder
import ru.kpfu.itis.hw_android_2022.dao.model.City

class CitiesListAdapter : androidx.recyclerview.widget.ListAdapter<City, CitiesViewHolder>(
    object : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean =
            oldItem.id == newItem.id
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder =
        CitiesViewHolder.create(parent)

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }
}