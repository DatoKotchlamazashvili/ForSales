package com.example.tbcexercises.data.mappers.home

import com.example.tbcexercises.data.remote.response.home.HomeResponse
import com.example.tbcexercises.domain.model.home.HomeProduct


fun HomeResponse.toDomainHomeProduct(): HomeProduct {
    return HomeProduct(
        productId = this.productId,
        productPrice = this.productPrice,
        productCategory = this.productCategory,
        productName = this.productName,
        productImgUrl = this.productImgUrl,
        company = this.company.map { it.toDomainCompany() }
    )
}