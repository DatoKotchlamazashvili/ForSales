package com.example.tbcexercises.domain.repository.product

import com.example.tbcexercises.domain.model.ProductFavourite
import com.example.tbcexercises.domain.model.ProductHome
import com.example.tbcexercises.utils.network_helper.Resource
import kotlinx.coroutines.flow.Flow

interface FavouriteProductRepository {
    suspend fun insertFavouriteProduct(product: ProductHome)
    suspend fun deleteFavouriteProduct(product: ProductHome)
    fun getAllFavouriteProducts(): Flow<Resource<List<ProductFavourite>>>
    fun getAllFavouriteProductIds(): Flow<List<Int>>

}
