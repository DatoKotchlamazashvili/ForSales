package com.example.tbcexercises.data.remote.service

import com.example.tbcexercises.data.remote.response.CompanyResponse
import retrofit2.Response
import retrofit2.http.GET


interface CompanyService {

    @GET("companies")
    suspend fun getCompanies(): Response<List<CompanyResponse>>

}