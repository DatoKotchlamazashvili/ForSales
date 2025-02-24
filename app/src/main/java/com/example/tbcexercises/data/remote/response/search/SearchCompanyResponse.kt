package com.example.tbcexercises.data.remote.response.search

import kotlinx.serialization.Serializable

@Serializable
data class SearchCompanyResponse(
    val name : String,
    val companyImgUrl : String,
    val productPrice : Double
)
