package com.example.tbcexercises.data.mappers.remote_to_local

import com.example.tbcexercises.data.local.entity.ProductHomeEntity
import com.example.tbcexercises.data.remote.response.HomeResponse


fun HomeResponse.toProductHomeEntity(): ProductHomeEntity {
    return ProductHomeEntity(
        productId = this.productId,
        productName = this.productName,
        productCategory = this.productCategory,
        productPrice = this.productPrice,
        productImgUrl = this.productImgUrl,
        company = this.company.toCompanyEntityList()
    )
}