package com.example.tbcexercises.utils.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.tbcexercises.R


fun ImageView.loadImg(url: String?) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.loading)
        .error(R.drawable.ic_error)
        .into(this)
}