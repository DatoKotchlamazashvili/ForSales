package com.example.tbcexercises.domain.model

data class ProductFavourite(
    val productId: Int,
    val productName: String,
    val productImgUrl: String,
    val company: String,
    val productPrice: Double
)