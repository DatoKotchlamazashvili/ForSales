package com.example.tbcexercises.data.mappers

import com.example.tbcexercises.data.local.entity.CartProductEntity
import com.example.tbcexercises.domain.model.CartProduct


fun CartProductEntity.toCartProduct(): CartProduct {
    return CartProduct(
        productId = this.productId,
        company = this.company,
        productImgUrl = this.productImgUrl,
        productName = this.productName,
        quantity = this.productQuantity
    )
}