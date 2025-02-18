package com.example.tbcexercises.data.mappers.local_to_presentation

import com.example.tbcexercises.data.local.entity.ProductHomeEntity
import com.example.tbcexercises.domain.model.ProductHome

fun ProductHomeEntity.toProduct(): ProductHome {
    return ProductHome(
        productId = this.productId,
        productCategory = this.productCategory,
        productImgUrl = this.productImgUrl,
        productName = this.productName,
        productPrice = this.productPrice,
        company = this.company.toCompanyList()
    )
}