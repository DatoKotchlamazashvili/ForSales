package com.example.tbcexercises.data.remote.service

import com.example.tbcexercises.data.remote.response.cart.CartProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CartProductService {

    @GET("products/cart")
    suspend fun getCartProductsByIds(@Query("ids") id: String): Response<List<CartProductResponse>>

}