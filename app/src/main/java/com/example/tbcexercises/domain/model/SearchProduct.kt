package com.example.tbcexercises.domain.model

data class SearchProduct(
    val productId: Int,
    val productName: String,
    val productImgUrl: String,
    val company: List<SearchCompany>,
    val productCategory: String,
    val isFavourite: Boolean = false
)