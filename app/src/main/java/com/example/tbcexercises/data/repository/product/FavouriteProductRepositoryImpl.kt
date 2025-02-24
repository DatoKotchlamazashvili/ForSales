package com.example.tbcexercises.data.repository.product

import com.example.tbcexercises.data.local.daos.favourite.FavouriteProductDao
import com.example.tbcexercises.data.mappers.local_to_presentation.toProductFavouriteEntity
import com.example.tbcexercises.domain.model.FavouriteProduct
import com.example.tbcexercises.domain.repository.product.FavouriteProductRepository
import com.example.tbcexercises.utils.network_helper.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavouriteProductRepositoryImpl @Inject constructor(
    private val favouriteProductDao: FavouriteProductDao
) : FavouriteProductRepository {

    override suspend fun insertFavouriteProduct(favouriteProduct: FavouriteProduct) {
        favouriteProductDao.insertFavouriteProduct(favouriteProduct.toProductEntity())
    }

    override suspend fun deleteFavouriteProduct(favouriteProduct: FavouriteProduct) {
        favouriteProductDao.deleteFavouriteProduct(favouriteProduct.toProductEntity())
    }

    override fun getAllFavouriteProducts(): Flow<Resource<List<FavouriteProduct>>> = flow {
        emit(Resource.Loading)
        try {
            favouriteProductDao.getAllFavouriteProducts().collect { products ->
                emit(Resource.Success(products.map { it.toProductFavouriteEntity() }))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: ""))
        }
    }

    override fun getAllFavouriteProductIds(): Flow<List<Int>> {
        return favouriteProductDao.getAllFavouriteProductIds()
    }
}