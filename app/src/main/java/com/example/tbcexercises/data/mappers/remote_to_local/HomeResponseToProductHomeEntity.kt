package com.example.tbcexercises.data.mappers.remote_to_local

import com.example.tbcexercises.data.local.entity.home.HomeProductEntity
import com.example.tbcexercises.data.remote.response.home.HomeResponse


fun HomeResponse.toProductHomeEntity(): HomeProductEntity {
    return HomeProductEntity(
        productId = this.productId,
        productName = this.productName,
        productCategory = this.productCategory,
        productPrice = this.productPrice,
        productImgUrl = this.productImgUrl,
        company = this.company.toCompanyEntityList()
    )
}