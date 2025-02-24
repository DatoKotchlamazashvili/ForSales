package com.example.tbcexercises.presentation.home_screen.product_list_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.domain.model.HomeProduct

object ProductHomeDiffUtil : DiffUtil.ItemCallback<HomeProduct>() {
    override fun areItemsTheSame(oldItem: HomeProduct, newItem: HomeProduct): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(oldItem: HomeProduct, newItem: HomeProduct): Boolean {
        return oldItem == newItem
    }
}