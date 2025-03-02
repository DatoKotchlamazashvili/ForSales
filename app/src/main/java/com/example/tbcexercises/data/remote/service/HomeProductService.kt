package com.example.tbcexercises.data.remote.service

import com.example.tbcexercises.data.remote.response.home.HomePaginatedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeProductService {

    @GET("products/lowest_priced")
    suspend fun getProducts(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("search") search: String?,
        @Query("category") category: String?
    ): HomePaginatedResponse



}