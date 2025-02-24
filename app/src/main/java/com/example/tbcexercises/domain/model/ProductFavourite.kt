package com.example.tbcexercises.domain.model

import com.example.tbcexercises.data.local.entity.favourite.FavouriteProductEntity

data class ProductFavourite(
    val productId: Int,
    val productName: String,
    val productImgUrl: String,
    val company: String,
    val productPrice: Double
) {
    fun toProductEntity(): FavouriteProductEntity {
        return FavouriteProductEntity(
            productId = this.productId,
            company = this.company,
            productImgUrl = this.productImgUrl,
            productName = this.productName,
            productPrice = this.productPrice
        )
    }
}