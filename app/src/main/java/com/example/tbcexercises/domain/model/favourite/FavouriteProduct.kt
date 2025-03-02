package com.example.tbcexercises.domain.model.favourite



data class FavouriteProduct(
    val productId: Int,
    val productName: String,
    val productImgUrl: String,
    val company: String,
    val productPrice: Double,
    val isAddedToCart : Boolean = false
)