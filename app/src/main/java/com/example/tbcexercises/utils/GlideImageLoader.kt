package com.example.tbcexercises.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.tbcexercises.domain.image_loader.ImageLoader

object GlideImageLoader : ImageLoader {
    override fun loadImage(
        imageView: ImageView,
        url: String?,
        placeholder: Int,
        error: Int
    ) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(placeholder)
            .error(error)
            .into(imageView)
    }
}