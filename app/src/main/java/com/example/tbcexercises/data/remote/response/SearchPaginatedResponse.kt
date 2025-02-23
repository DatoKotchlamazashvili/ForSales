package com.example.tbcexercises.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class SearchPaginatedResponse(
    val page: Int,
    val perPage: Int,
    val totalPages: Int,
    val totalElements: Int,
    val data: List<SearchProductResponse>
)
