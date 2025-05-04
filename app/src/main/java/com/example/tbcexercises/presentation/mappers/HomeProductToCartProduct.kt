package com.example.tbcexercises.presentation.mappers

import com.example.tbcexercises.domain.model.cart.CartProduct
import com.example.tbcexercises.domain.model.home.HomeProduct


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