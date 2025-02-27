package com.example.tbcexercises.data.mappers

import com.example.tbcexercises.data.remote.response.home.HomeCompanyResponse
import com.example.tbcexercises.domain.model.HomeCompany


fun HomeCompanyResponse.toCompany(): HomeCompany {
    return HomeCompany(name = this.name, companyImgUrl = this.companyImgUrl)
}