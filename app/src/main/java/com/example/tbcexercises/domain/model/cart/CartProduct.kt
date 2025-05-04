package com.example.tbcexercises.domain.model.cart


data class CartProduct(
    val productId: Int,
    val productName: String,
    val productImgUrl: String,
    val company: String,
    val productPrice: Double? = null,
    val quantity: Int
) {

    val totalPrice: Double = productPrice?.let { it * quantity } ?: 0.0

}