package com.example.tbcexercises.presentation.search_screen.product_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.domain.model.SearchProduct

object SearchProductDiffUtil : DiffUtil.ItemCallback<SearchProduct>() {
    override fun areItemsTheSame(oldItem: SearchProduct, newItem: SearchProduct): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(oldItem: SearchProduct, newItem: SearchProduct): Boolean {
        return oldItem == newItem
    }
}