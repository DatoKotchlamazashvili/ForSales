package com.example.tbcexercises.data.remote.response.home

import kotlinx.serialization.Serializable

@Serializable
data class HomeCompanyResponse(
    val name : String,
    val companyImgUrl : String
)
