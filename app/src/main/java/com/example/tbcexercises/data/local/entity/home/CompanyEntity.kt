package com.example.tbcexercises.data.local.entity.home

import kotlinx.serialization.Serializable

@Serializable
data class CompanyEntity(
    val name: String,
    val companyImgUrl: String
)