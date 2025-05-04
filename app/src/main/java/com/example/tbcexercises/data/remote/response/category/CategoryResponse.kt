package com.example.tbcexercises.data.remote.response.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val id: Int,
    val title: String,
    val categoryImgUrl: String?
)
