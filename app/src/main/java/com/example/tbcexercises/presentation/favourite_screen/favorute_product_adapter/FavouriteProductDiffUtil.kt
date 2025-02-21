package com.example.tbcexercises.presentation.favourite_screen.favorute_product_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.domain.model.ProductFavourite

object FavouriteProductDiffUtil : DiffUtil.ItemCallback<ProductFavourite>() {
    override fun areItemsTheSame(oldItem: ProductFavourite, newItem: ProductFavourite): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(oldItem: ProductFavourite, newItem: ProductFavourite): Boolean {
        return oldItem == newItem
    }
}