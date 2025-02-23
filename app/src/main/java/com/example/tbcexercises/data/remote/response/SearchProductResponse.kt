package com.example.tbcexercises.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class SearchProductResponse(
    val productId: Int,
    val productName: String,
    val productImgUrl: String,
    val company: List<SearchCompanyResponse>,
    val productCategory: String,
)