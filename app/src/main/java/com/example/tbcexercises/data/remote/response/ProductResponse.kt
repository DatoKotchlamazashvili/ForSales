package com.example.tbcexercises.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val productId: Int,
    val productName: String,
    val productImgUrl: String,
    val company : String,
    val companyImgUrl : String,
    val productCategory: String,
    val productPrice: Double
)