package com.example.tbcexercises.presentation.favourite_screen.favorute_product_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.domain.model.favourite.FavouriteProduct

object FavouriteProductDiffUtil : DiffUtil.ItemCallback<FavouriteProduct>() {
    override fun areItemsTheSame(oldItem: FavouriteProduct, newItem: FavouriteProduct): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(oldItem: FavouriteProduct, newItem: FavouriteProduct): Boolean {
        return oldItem == newItem
    }
}