package com.example.androidclass.viewpager

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.hw_android_2022.databinding.ItemPageBinding

class PagerViewHolder(
    private val binding: ItemPageBinding,
    private val onWork: ((String, ImageView, ProgressBar) -> Unit)? = null
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(url: String) {
        with(binding) {
            onWork?.invoke(url, ivImage, progressbar)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onWork: ((String, ImageView, ProgressBar) -> Unit)?
        ) = PagerViewHolder(
            ItemPageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onWork
        )
    }
}
