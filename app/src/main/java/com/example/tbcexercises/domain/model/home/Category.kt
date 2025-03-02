package com.example.tbcexercises.domain.model.home

data class Category(
    val id: Int,
    val title: String,
    val categoryImgUrl: String?,
    val isClicked: Boolean = false
)
