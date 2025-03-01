package com.example.tbcexercises.data.mappers

import com.example.tbcexercises.data.remote.response.FavouriteProductResponse
import com.example.tbcexercises.domain.model.FavouriteProduct


fun FavouriteProductResponse.toFavouriteProduct(): FavouriteProduct {
    return FavouriteProduct(
        productId = this.productId,
        productImgUrl = this.productImgUrl,
        productName = this.productName,
        productPrice = this.productPrice,
        company = this.company
    )
}