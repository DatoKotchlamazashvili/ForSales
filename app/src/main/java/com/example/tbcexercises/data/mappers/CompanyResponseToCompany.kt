package com.example.tbcexercises.data.mappers

import com.example.tbcexercises.data.remote.response.CompanyResponse
import com.example.tbcexercises.domain.model.Company

fun CompanyResponse.toCompany(isClicked: Boolean = false): Company {
    return Company(
        id = id,
        company = company,
        companyImgUrl = companyImgUrl,
        isClicked = isClicked
    )
}