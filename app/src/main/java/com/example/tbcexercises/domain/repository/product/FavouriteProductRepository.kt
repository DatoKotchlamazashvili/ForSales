package com.example.tbcexercises.domain.repository.product

import com.example.tbcexercises.domain.model.ProductFavourite
import com.example.tbcexercises.utils.network_helper.Resource
import kotlinx.coroutines.flow.Flow

interface FavouriteProductRepository {
    suspend fun insertFavouriteProduct(productFavourite: ProductFavourite)
    suspend fun deleteFavouriteProduct(productFavourite: ProductFavourite)
    fun getAllFavouriteProducts(): Flow<Resource<List<ProductFavourite>>>
    fun getAllFavouriteProductIds(): Flow<List<Int>>

}
