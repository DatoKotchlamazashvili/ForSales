package com.example.tbcexercises.data.mappers.local_to_presentation

import com.example.tbcexercises.data.local.entity.home.HomeProductEntity
import com.example.tbcexercises.domain.model.HomeProduct

fun HomeProductEntity.toProduct(): HomeProduct {
    return HomeProduct(
        productId = this.productId,
        productCategory = this.productCategory,
        productImgUrl = this.productImgUrl,
        productName = this.productName,
        productPrice = this.productPrice,
        company = this.company.toCompanyList()
    )
}