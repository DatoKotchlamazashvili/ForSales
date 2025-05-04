package com.example.tbcexercises.presentation.detail_screen.company_prices_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.domain.model.detail.DetailProduct

object CompanyPricesDiffUtil : DiffUtil.ItemCallback<DetailProduct>() {
    override fun areItemsTheSame(oldItem: DetailProduct, newItem: DetailProduct): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(oldItem: DetailProduct, newItem: DetailProduct): Boolean {
        return oldItem == newItem
    }
}