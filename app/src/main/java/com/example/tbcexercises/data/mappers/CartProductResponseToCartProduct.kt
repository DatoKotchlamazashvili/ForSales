package com.example.tbcexercises.data.mappers

import com.example.tbcexercises.data.remote.response.CartProductResponse
import com.example.tbcexercises.domain.model.CartProduct


fun CartProductResponse.toCartProduct(): CartProduct {
    return CartProduct(
        productId = this.productId,
        productName = this.productName,
        company = this.company,
        productPrice = this.productPrice,
        productImgUrl = this.productImgUrl,
        quantity = 1
    )
}