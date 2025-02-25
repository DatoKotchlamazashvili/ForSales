package com.example.tbcexercises.data.mappers.remote_to_local

import com.example.tbcexercises.data.local.entity.favourite.FavouriteProductEntity
import com.example.tbcexercises.data.remote.response.FavouriteProductResponse


fun FavouriteProductResponse.toFavouriteProductEntity(): FavouriteProductEntity {
    return FavouriteProductEntity(
        productId = this.productId,
        company = this.company,
        productImgUrl = this.productImgUrl,
        productName = this.productName,
        productPrice = productPrice
    )
}