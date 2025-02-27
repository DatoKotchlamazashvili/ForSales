package com.example.tbcexercises.data.mappers

import com.example.tbcexercises.data.remote.response.CompanyResponse
import com.example.tbcexercises.domain.model.Company

fun CompanyResponse.toCompany(): Company {
    return Company(
        id = this.id,
        company = this.company,
        companyImgUrl = this.companyImgUrl
    )
}