package com.example.androidclass.viewpager

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView

internal class ViewPagerAdapter : RecyclerView.Adapter<PagerViewHolder>() {
    var onWork: ((String, ImageView, ProgressBar) -> Unit)? = null

    var imageUrl = arrayOf(
        "https://www.nasa.gov/sites/default/files/thumbnails/image/uranus.jpg",
        "https://www.nasa.gov/sites/default/files/images/729223main_728322main_messenger_orbit_image20130218_2_full_full_full.jpg",
        "https://www.nasa.gov/sites/default/files/thumbnails/image/imagesvenus20191211venus20191211-16.jpeg",
        "https://www.nasa.gov/sites/default/files/thumbnails/image/stsci-h-p1936a-m-1999x2000.png"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
        PagerViewHolder.create(parent, onWork)

    override fun getItemCount(): Int = imageUrl.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(imageUrl[position])
    }
}


