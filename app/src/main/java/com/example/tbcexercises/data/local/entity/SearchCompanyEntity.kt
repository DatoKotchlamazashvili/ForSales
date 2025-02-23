package com.example.tbcexercises.data.local.entity

import kotlinx.serialization.Serializable

@Serializable
data class SearchCompanyEntity(
    val name: String,
    val companyImgUrl: String,
    val productPrice : String
)