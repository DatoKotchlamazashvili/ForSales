package com.example.tbcexercises.data.mappers.remote_to_presentation

import com.example.tbcexercises.data.remote.response.ProductResponse
import com.example.tbcexercises.domain.model.ProductDetail


fun ProductResponse.toProductDetail(): ProductDetail {
    return ProductDetail(
        productId = this.productId,
        company = this.company,
        companyImgUrl = this.companyImgUrl,
        productCategory = this.productCategory,
        productImgUrl = this.productImgUrl,
        productName = this.productName,
        productPrice = this.productPrice
    )
}