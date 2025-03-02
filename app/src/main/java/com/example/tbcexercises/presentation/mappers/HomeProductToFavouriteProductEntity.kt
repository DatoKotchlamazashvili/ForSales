package com.example.tbcexercises.presentation.mappers

import com.example.tbcexercises.data.local.entity.FavouriteProductEntity
import com.example.tbcexercises.domain.model.home.HomeProduct


fun HomeProduct.toFavouriteProductEntity(): FavouriteProductEntity {
    return FavouriteProductEntity(
        productId = productId,
        productName = productName,
        productImgUrl = productImgUrl,
        company = company.joinToString(",") { it.name },
        productPrice = productPrice
    )
}