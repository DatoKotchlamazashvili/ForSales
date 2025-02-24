package com.example.tbcexercises.data.mappers.remote_to_local

import com.example.tbcexercises.data.local.entity.home.CompanyEntity
import com.example.tbcexercises.data.remote.response.home.CompanyResponse


fun CompanyResponse.toCompanyEntity(): CompanyEntity {
    return CompanyEntity(name = this.name, companyImgUrl = this.companyImgUrl)
}

fun List<CompanyResponse>.toCompanyEntityList(): List<CompanyEntity> {
    return this.map {
        it.toCompanyEntity()
    }
}
