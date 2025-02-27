package com.example.tbcexercises.domain.model

import com.example.tbcexercises.data.local.entity.CartProductEntity

data class CartProduct(
    val productId: Int,
    val productName: String,
    val productImgUrl: String,
    val company: String,
    val productPrice: Double? = null,
    val quantity: Int
) {
    fun toCartProductEntity(): CartProductEntity {
        return CartProductEntity(
            productId = this.productId,
            productName = this.productName,
            company = this.company,
            productImgUrl = this.productImgUrl,
            productQuantity = this.quantity,
            productPrice = this.productPrice

        )
    }
}