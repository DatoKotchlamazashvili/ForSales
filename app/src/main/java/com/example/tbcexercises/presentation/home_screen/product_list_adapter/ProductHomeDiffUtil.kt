package com.example.tbcexercises.presentation.home_screen.product_list_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.domain.model.ProductHome

object ProductHomeDiffUtil : DiffUtil.ItemCallback<ProductHome>() {
    override fun areItemsTheSame(oldItem: ProductHome, newItem: ProductHome): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(oldItem: ProductHome, newItem: ProductHome): Boolean {
        return oldItem == newItem
    }
}