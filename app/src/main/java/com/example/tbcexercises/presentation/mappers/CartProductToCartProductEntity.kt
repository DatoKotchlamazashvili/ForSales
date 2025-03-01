package com.example.tbcexercises.presentation.mappers

import com.example.tbcexercises.data.local.entity.CartProductEntity
import com.example.tbcexercises.domain.model.CartProduct

fun CartProduct.toCartProductEntity(): CartProductEntity {
    return CartProductEntity(
        productId = this.productId,
        productName = this.productName,
        company = this.company,
        productImgUrl = this.productImgUrl,
        productQuantity = this.quantity,
        productPrice = this.productPrice
    )
}
