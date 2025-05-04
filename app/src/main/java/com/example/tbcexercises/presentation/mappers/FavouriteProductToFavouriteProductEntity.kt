package com.example.tbcexercises.presentation.mappers

import com.example.tbcexercises.data.local.entity.FavouriteProductEntity
import com.example.tbcexercises.domain.model.favourite.FavouriteProduct


fun FavouriteProduct.toFavouriteProductEntity(): FavouriteProductEntity {
    return FavouriteProductEntity(
        productId = this.productId,
        company = this.company,
        productImgUrl = this.productImgUrl,
        productName = this.productName,
        productPrice = this.productPrice
    )
}