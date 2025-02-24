package com.example.tbcexercises.presentation.mappers.local_to_presentation

import com.example.tbcexercises.data.local.entity.search.SearchProductEntity
import com.example.tbcexercises.data.mappers.local_to_presentation.toSearchCompany
import com.example.tbcexercises.domain.model.search.SearchProduct


fun SearchProductEntity.toSearchProduct(): SearchProduct {
    return SearchProduct(
        productId = this.productId,
        company = this.company.map {
            it.toSearchCompany()
        },
        productImgUrl = this.productImgUrl,
        productName = this.productName
    )
}