package com.example.tbcexercises.data.mappers.detail

import com.example.tbcexercises.data.remote.response.detail.DetailProductResponse
import com.example.tbcexercises.domain.model.detail.DetailProduct


fun DetailProductResponse.toDomainProductDetail(): DetailProduct {
    return DetailProduct(
        productId = this.productId,
        company = this.company,
        companyImgUrl = this.companyImgUrl,
        productCategory = this.productCategory,
        productImgUrl = this.productImgUrl,
        productName = this.productName,
        productPrice = this.productPrice
    )
}