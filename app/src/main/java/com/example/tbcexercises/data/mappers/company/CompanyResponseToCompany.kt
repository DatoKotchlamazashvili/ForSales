package com.example.tbcexercises.data.mappers.company

import com.example.tbcexercises.data.remote.response.company.CompanyResponse
import com.example.tbcexercises.domain.model.cart.Company

fun CompanyResponse.toDomainCompany(isClicked: Boolean = false): Company {
    return Company(
        id = id,
        company = company,
        companyImgUrl = companyImgUrl,
        isClicked = isClicked
    )
}