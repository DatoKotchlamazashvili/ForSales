package com.example.tbcexercises.data.mappers.local_to_presentation

import com.example.tbcexercises.data.local.entity.FavouriteProductEntity
import com.example.tbcexercises.domain.model.ProductFavourite


fun FavouriteProductEntity.toProductFavouriteEntity(): ProductFavourite {
    return ProductFavourite(
        productId = productId,
        productName = productName,
        productImgUrl = productImgUrl,
        company = company,
        productPrice = productPrice
    )
}