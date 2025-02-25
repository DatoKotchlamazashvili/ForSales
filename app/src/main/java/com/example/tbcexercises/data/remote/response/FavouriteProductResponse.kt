package com.example.tbcexercises.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class FavouriteProductResponse(
    val productId: Int,
    val productName: String,
    val productImgUrl: String,
    val company: String,
    val productPrice: Double
)