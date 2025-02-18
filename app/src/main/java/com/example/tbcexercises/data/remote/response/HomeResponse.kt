package com.example.tbcexercises.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class HomeResponse(
    val productId: Int,
    val productName: String,
    val productImgUrl: String,
    val company: List<CompanyResponse>,
    val productCategory: String,
    val productPrice: Double
)
