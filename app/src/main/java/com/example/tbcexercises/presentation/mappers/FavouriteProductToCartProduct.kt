package com.example.tbcexercises.presentation.mappers

import com.example.tbcexercises.domain.model.cart.CartProduct
import com.example.tbcexercises.domain.model.favourite.FavouriteProduct
import com.example.tbcexercises.utils.extension.getCompany


fun FavouriteProduct.toCartProduct(): CartProduct {
    return CartProduct(
        productId = this.productId,
        productImgUrl = this.productImgUrl,
        productPrice = this.productPrice,
        productName = this.productName,
        quantity = 1,
        company = company.getCompany()
    )
}

