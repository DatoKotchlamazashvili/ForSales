package com.example.tbcexercises.domain.model


data class DetailProduct(
    val productId: Int,
    val productName: String,
    val productImgUrl: String,
    val company: String,
    val companyImgUrl: String,
    val productCategory: String,
    val productPrice: Double
)