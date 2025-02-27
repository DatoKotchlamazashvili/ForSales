package com.example.tbcexercises.data.mappers

import com.example.tbcexercises.data.remote.response.CategoryResponse
import com.example.tbcexercises.domain.model.Category


fun CategoryResponse.toCategory(): Category {
    return Category(
        id = this.id,
        categoryImgUrl = this.categoryImgUrl,
        title = this.title
    )
}