package com.example.tbcexercises.data.remote.service

import com.example.tbcexercises.data.remote.response.detail.DetailProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailProductService {
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Response<List<DetailProductResponse>>

}
