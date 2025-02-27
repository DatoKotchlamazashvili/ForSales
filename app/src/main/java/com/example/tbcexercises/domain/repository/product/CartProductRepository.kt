package com.example.tbcexercises.domain.repository.product

import com.example.tbcexercises.data.remote.response.CartProductResponse
import com.example.tbcexercises.domain.model.CartProduct
import com.example.tbcexercises.utils.network_helper.Resource
import kotlinx.coroutines.flow.Flow


interface CartProductRepository {
    suspend fun insertCartProduct(cartProduct: CartProduct)
    suspend fun insertCartProducts(cartProduct: List<CartProduct>)

    suspend fun deleteCartProduct(cartProduct: CartProduct)
    fun getAllCartProducts(): Flow<Resource<List<CartProduct>>>
    fun getAllCartProductIds(): Flow<List<Int>>

    fun getCartProductsFromServer(ids: String): Flow<Resource<List<CartProductResponse>>>

    suspend fun saveCartProducts()
}
