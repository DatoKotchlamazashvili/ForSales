package com.example.tbcexercises.domain.model.cart

data class Company(
    val id: Int,
    val company: String,
    val companyImgUrl: String,
    val isClicked: Boolean = false
)
