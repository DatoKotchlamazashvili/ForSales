package com.example.tbcexercises.data.mappers.local_to_presentation

import com.example.tbcexercises.data.local.entity.home.CompanyEntity
import com.example.tbcexercises.domain.model.Company


fun CompanyEntity.toCompany(): Company {
    return Company(name = this.name, companyImgUrl = this.companyImgUrl)
}

fun List<CompanyEntity>.toCompanyList(): List<Company> {
    return this.map {
        it.toCompany()
    }
}