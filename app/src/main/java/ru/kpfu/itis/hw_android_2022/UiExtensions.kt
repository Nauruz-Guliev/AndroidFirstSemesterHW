package ru.kpfu.itis.hw_android_2022

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


fun <T> Fragment.showToast(message: T) =
    Toast.makeText(this.context, message.toString(), Toast.LENGTH_SHORT).show()

fun RequestManager.preloadImage(url: String?) {
    this.load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .preload()
}

fun ImageView.load(
    imageUrl: String?,
    glide: RequestManager?,
    progressBar: ProgressBar
) {
    glide
        ?.load(imageUrl)
        ?.diskCacheStrategy(DiskCacheStrategy.ALL)
        ?.listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable?>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable?>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                this@load.setImageDrawable(drawable)
                progressBar.isVisible = false
                return false
            }


        })?.into(this)
}

