package com.example.tbcexercises.data.mappers.local_to_presentation

import com.example.tbcexercises.data.local.entity.search.SearchCompanyEntity
import com.example.tbcexercises.domain.model.SearchCompany


fun SearchCompanyEntity.toSearchCompany(): SearchCompany {
    return SearchCompany(
        name = this.name,
        companyImgUrl = this.companyImgUrl,
        productPrice = this.productPrice
    )
}