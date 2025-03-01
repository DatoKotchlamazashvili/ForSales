package com.example.tbcexercises.presentation.mappers

import com.example.tbcexercises.domain.model.CartProduct
import com.example.tbcexercises.domain.model.HomeProduct


fun HomeProduct.toCartProduct(): CartProduct {
    return CartProduct(
        productId = this.productId,
        productImgUrl = this.productImgUrl,
        productPrice = this.productPrice,
        productName = this.productName,
        quantity = 1,
        company = this.company.first().name
    )
}