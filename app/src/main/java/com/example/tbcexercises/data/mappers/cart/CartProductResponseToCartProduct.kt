package com.example.tbcexercises.data.mappers.cart

import com.example.tbcexercises.data.remote.response.cart.CartProductResponse
import com.example.tbcexercises.domain.model.cart.CartProduct


fun CartProductResponse.toDomainCartProduct(): CartProduct {
    return CartProduct(
        productId = this.productId,
        productName = this.productName,
        company = this.company,
        productPrice = this.productPrice,
        productImgUrl = this.productImgUrl,
        quantity = 1
    )
}