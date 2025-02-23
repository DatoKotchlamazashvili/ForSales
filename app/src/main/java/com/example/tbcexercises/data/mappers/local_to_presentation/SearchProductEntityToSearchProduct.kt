package com.example.tbcexercises.data.mappers.local_to_presentation

import com.example.tbcexercises.data.local.entity.SearchProductEntity
import com.example.tbcexercises.domain.model.SearchProduct


fun SearchProductEntity.toSearchProduct(): SearchProduct {
    return SearchProduct(
        productId = this.productId,
        company = this.company.map {
            it.toSearchCompany()
        },
        productImgUrl = this.productImgUrl,
        productCategory = this.productCategory,
        productName = this.productName
    )
}