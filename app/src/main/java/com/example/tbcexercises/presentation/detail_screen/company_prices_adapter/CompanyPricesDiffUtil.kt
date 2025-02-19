package com.example.tbcexercises.presentation.detail_screen.company_prices_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.domain.model.ProductDetail

object CompanyPricesDiffUtil : DiffUtil.ItemCallback<ProductDetail>() {
    override fun areItemsTheSame(oldItem: ProductDetail, newItem: ProductDetail): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(oldItem: ProductDetail, newItem: ProductDetail): Boolean {
        return oldItem == newItem
    }
}