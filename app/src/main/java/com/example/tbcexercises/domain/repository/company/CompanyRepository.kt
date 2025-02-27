package com.example.tbcexercises.domain.repository.company

import com.example.tbcexercises.domain.model.Company
import com.example.tbcexercises.utils.network_helper.Resource
import kotlinx.coroutines.flow.Flow

interface CompanyRepository {


    fun getCompanies(): Flow<Resource<List<Company>>>
}