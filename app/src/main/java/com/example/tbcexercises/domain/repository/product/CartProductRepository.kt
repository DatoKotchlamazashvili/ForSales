package com.example.tbcexercises.domain.repository.product

import com.example.tbcexercises.domain.model.CartProduct
import com.example.tbcexercises.utils.network_helper.Resource
import kotlinx.coroutines.flow.Flow


interface CartProductRepository {
    suspend fun upsertCartProduct(cartProduct: CartProduct)
    suspend fun upsertCartProducts(cartProduct: List<CartProduct>)

    suspend fun deleteCartProduct(cartProduct: CartProduct)

    suspend fun incrementCartProductQuantity(cartProduct: CartProduct)

    suspend fun decrementCartProductQuantity(cartProduct: CartProduct)


    fun getAllCartProducts(company: String): Flow<Resource<List<CartProduct>>>
    fun getAllCartProductIds(): Flow<List<Int>>

    fun getCartProductsFromServer(ids: String): Flow<Resource<List<CartProduct>>>

    suspend fun saveCartProducts()
}
