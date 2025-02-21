package com.example.tbcexercises.data.repository

import com.example.tbcexercises.data.local.daos.FavouriteProductDao
import com.example.tbcexercises.data.local.entity.FavouriteProductEntity
import com.example.tbcexercises.data.mappers.local_to_presentation.toProductFavourite
import com.example.tbcexercises.domain.model.ProductFavourite
import com.example.tbcexercises.domain.repository.FavouriteProductRepository
import com.example.tbcexercises.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavouriteProductRepositoryImpl @Inject constructor(
    private val favouriteProductDao: FavouriteProductDao
) : FavouriteProductRepository {

    override suspend fun insertFavouriteProduct(product: FavouriteProductEntity) {
        favouriteProductDao.insertFavouriteProduct(product)
    }

    override suspend fun deleteFavouriteProduct(product: FavouriteProductEntity) {
        favouriteProductDao.deleteFavouriteProduct(product)
    }

    override fun getAllFavouriteProducts(): Flow<Resource<List<ProductFavourite>>> = flow {
        emit(Resource.Loading)
        try {
            favouriteProductDao.getAllFavouriteProducts().collect { products ->
                emit(Resource.Success(products.map { it.toProductFavourite() }))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: ""))
        }
    }

    override fun getAllFavouriteProductIds(): Flow<List<Int>> {
        return favouriteProductDao.getAllFavouriteProductIds()
    }
}
