package com.example.tbcexercises.domain.model


data class ProductHome(
    val productId: Int,
    val productName: String,
    val productImgUrl: String,
    val company: List<Company>,
    val productCategory: String,
    val productPrice: Double
)