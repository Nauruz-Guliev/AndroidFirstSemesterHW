package ru.kpfu.itis.hw_android_2022.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import ru.kpfu.itis.hw_android_2022.models.City

class CitiesListAdapter : androidx.recyclerview.widget.ListAdapter<City, CitiesViewHolder>(
    object : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: City, newItem: City) =
            oldItem.id == newItem.id
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CitiesViewHolder.create(parent)

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) =
        holder.onBind(currentList[position])
}