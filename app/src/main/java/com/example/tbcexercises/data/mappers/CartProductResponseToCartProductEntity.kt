package com.example.tbcexercises.data.mappers

import com.example.tbcexercises.data.local.entity.CartProductEntity
import com.example.tbcexercises.data.remote.response.CartProductResponse


fun CartProductResponse.toCartProductEntity(): CartProductEntity {
    return CartProductEntity(
        productId = this.productId,
        company = this.company,
        productPrice = this.productPrice,
        productImgUrl = this.productImgUrl,
        productName = this.productName,
    )
}