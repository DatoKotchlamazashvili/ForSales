package com.example.tbcexercises.data.mappers.local_to_presentation

import com.example.tbcexercises.data.local.entity.favourite.FavouriteProductEntity
import com.example.tbcexercises.domain.model.FavouriteProduct


fun FavouriteProductEntity.toFavouriteProduct(): FavouriteProduct {
    return FavouriteProduct(
        productId = productId,
        productName = productName,
        productImgUrl = productImgUrl,
        company = company,
        productPrice = productPrice
    )
}