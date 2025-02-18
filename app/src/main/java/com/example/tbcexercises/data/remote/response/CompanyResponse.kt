package com.example.tbcexercises.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class CompanyResponse(
    val name : String,
    val companyImgUrl : String
)
