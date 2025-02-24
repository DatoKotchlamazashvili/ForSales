package com.example.tbcexercises.domain.repository.product

import com.example.tbcexercises.domain.model.FavouriteProduct
import com.example.tbcexercises.utils.network_helper.Resource
import kotlinx.coroutines.flow.Flow

interface FavouriteProductRepository {
    suspend fun insertFavouriteProduct(favouriteProduct: FavouriteProduct)
    suspend fun deleteFavouriteProduct(favouriteProduct: FavouriteProduct)
    fun getAllFavouriteProducts(): Flow<Resource<List<FavouriteProduct>>>
    fun getAllFavouriteProductIds(): Flow<List<Int>>

}
