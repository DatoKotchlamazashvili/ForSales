package com.example.tbcexercises.data.remote.service

import com.example.tbcexercises.data.remote.response.SearchPaginatedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchProductService {

    @GET("products/search")
    suspend fun getSearchedProducts(
        @Query("query") query: String,
        @Query("page") page: Int, @Query("perPage") perPage: Int
    ): SearchPaginatedResponse
}