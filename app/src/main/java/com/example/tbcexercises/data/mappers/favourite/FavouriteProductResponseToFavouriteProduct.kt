package com.example.tbcexercises.data.mappers.favourite

import com.example.tbcexercises.data.remote.response.favourite.FavouriteProductResponse
import com.example.tbcexercises.domain.model.favourite.FavouriteProduct


fun FavouriteProductResponse.toFavouriteProduct(): FavouriteProduct {
    return FavouriteProduct(
        productId = this.productId,
        productImgUrl = this.productImgUrl,
        productName = this.productName,
        productPrice = this.productPrice,
        company = this.company
    )
}