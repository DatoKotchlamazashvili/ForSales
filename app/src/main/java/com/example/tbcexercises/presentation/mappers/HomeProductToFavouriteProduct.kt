package com.example.tbcexercises.presentation.mappers

import com.example.tbcexercises.domain.model.favourite.FavouriteProduct
import com.example.tbcexercises.domain.model.home.HomeProduct

fun HomeProduct.toFavouriteProduct(): FavouriteProduct {
    return FavouriteProduct(
        productId = this.productId,
        productName = this.productName,
        company = this.company.joinToString(",") { it.name },
        productPrice = this.productPrice,
        productImgUrl = this.productImgUrl
    )
}