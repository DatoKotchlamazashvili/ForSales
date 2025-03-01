package com.example.tbcexercises.presentation.mappers

import com.example.tbcexercises.domain.model.FavouriteProduct
import com.example.tbcexercises.domain.model.HomeProduct

fun HomeProduct.toFavouriteProduct(): FavouriteProduct {
    return FavouriteProduct(
        productId = this.productId,
        productName = this.productName,
        company = this.company.joinToString(",") { it.name },
        productPrice = this.productPrice,
        productImgUrl = this.productImgUrl
    )
}