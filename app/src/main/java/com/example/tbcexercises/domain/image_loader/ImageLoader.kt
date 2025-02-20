package com.example.tbcexercises.domain.image_loader

import android.widget.ImageView
import com.example.tbcexercises.R

interface ImageLoader {
    fun loadImage(
        imageView: ImageView,
        url: String?,
        placeholder: Int = R.drawable.loading,
        error: Int = R.drawable.ic_error
    )
}