package com.example.tbcexercises.data.mappers.home

import com.example.tbcexercises.data.remote.response.home.HomeCompanyResponse
import com.example.tbcexercises.domain.model.home.HomeCompany


fun HomeCompanyResponse.toDomainCompany(): HomeCompany {
    return HomeCompany(name = this.name, companyImgUrl = this.companyImgUrl)
}