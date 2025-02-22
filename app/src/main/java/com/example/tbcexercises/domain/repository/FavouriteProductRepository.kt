package com.example.tbcexercises.domain.repository

import com.example.tbcexercises.data.local.entity.FavouriteProductEntity
import com.example.tbcexercises.domain.model.ProductFavourite
import com.example.tbcexercises.utils.network_helper.Resource
import kotlinx.coroutines.flow.Flow

interface FavouriteProductRepository {
    suspend fun insertFavouriteProduct(product: FavouriteProductEntity)
    suspend fun deleteFavouriteProduct(product: FavouriteProductEntity)
    fun getAllFavouriteProducts(): Flow<Resource<List<ProductFavourite>>>
    fun getAllFavouriteProductIds(): Flow<List<Int>>

}
