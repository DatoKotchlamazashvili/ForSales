package com.example.tbcexercises.data.mappers

import com.example.tbcexercises.data.remote.response.home.CompanyResponse
import com.example.tbcexercises.domain.model.Company


fun CompanyResponse.toCompany(): Company {
    return Company(name = this.name, companyImgUrl = this.companyImgUrl)
}