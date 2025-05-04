package com.example.tbcexercises.presentation.cart_screen.cart_product_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.domain.model.cart.CartProduct

object CartProductDIffUtil:DiffUtil.ItemCallback<CartProduct>() {
    override fun areItemsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
        return oldItem == newItem
    }
}