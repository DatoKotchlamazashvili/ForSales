package com.example.tbcexercises.data.remote.service

import com.example.tbcexercises.data.remote.response.FavouriteProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FavouriteProductService {

    @GET("products/favourite")
    suspend fun getProductsByIds(@Query("ids") id: String): Response<List<FavouriteProductResponse>>

}