package com.example.tbcexercises.data.remote.service

import com.example.tbcexercises.data.remote.response.category.CategoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface CategoryService {

    @GET("categories")
    suspend fun getCategories(): Response<List<CategoryResponse>>

}