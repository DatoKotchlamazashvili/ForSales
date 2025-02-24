package com.example.tbcexercises.data.mappers.remote_to_local

import com.example.tbcexercises.data.local.entity.search.SearchProductEntity
import com.example.tbcexercises.data.remote.response.search.SearchProductResponse


fun SearchProductResponse.toSearchProductEntity(): SearchProductEntity {
    return SearchProductEntity(
        productId = this.productId,
        productName = this.productName,
        productCategory = this.productCategory,
        productImgUrl = this.productImgUrl,
        company = this.company.map { it.toSearchCompanyEntity() }
    )
}