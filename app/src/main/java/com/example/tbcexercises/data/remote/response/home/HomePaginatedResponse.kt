package com.example.tbcexercises.data.remote.response.home

import kotlinx.serialization.Serializable

@Serializable
data class HomePaginatedResponse(
    val page : Int,
    val perPage:Int,
    val totalPages:Int,
    val totalElements : Int,
    val data : List<HomeResponse>
)
