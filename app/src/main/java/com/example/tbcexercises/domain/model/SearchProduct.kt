package com.example.tbcexercises.domain.model

import com.example.tbcexercises.utils.extension.companiesWithLowestPrice
import com.example.tbcexercises.utils.extension.getLowestPrice

data class SearchProduct(
    val productId: Int,
    val productName: String,
    val productImgUrl: String,
    val company: List<SearchCompany>,
    val isFavourite: Boolean = false
) {
    fun toFavouriteProduct(): FavouriteProduct {
        return FavouriteProduct(
            productId = this.productId,
            productName = this.productName,
            productImgUrl = this.productImgUrl,
            company = this.company.companiesWithLowestPrice(),
            productPrice = this.company.getLowestPrice()
        )
    }
}