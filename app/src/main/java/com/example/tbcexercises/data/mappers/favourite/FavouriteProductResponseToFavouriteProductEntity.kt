package com.example.tbcexercises.data.mappers.favourite

import com.example.tbcexercises.data.local.entity.FavouriteProductEntity
import com.example.tbcexercises.data.remote.response.favourite.FavouriteProductResponse


fun FavouriteProductResponse.toFavouriteProductEntity(): FavouriteProductEntity {
    return FavouriteProductEntity(
        productId = this.productId,
        company = this.company,
        productImgUrl = this.productImgUrl,
        productName = this.productName,
        productPrice = productPrice
    )
}