package com.example.tbcexercises.domain.model.home


data class HomeProduct(
    val productId: Int,
    val productName: String,
    val productImgUrl: String,
    val company: List<HomeCompany>,
    val productCategory: String,
    val productPrice: Double,
    val isFavourite: Boolean = false,
    val isAddedToCart: Boolean = false
)