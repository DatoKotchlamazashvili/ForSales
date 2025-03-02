package com.example.tbcexercises.data.repository.product


import com.example.tbcexercises.data.local.daos.FavouriteProductDao
import com.example.tbcexercises.data.mappers.favourite.toFavouriteProduct
import com.example.tbcexercises.data.remote.service.FavouriteProductService
import com.example.tbcexercises.domain.model.favourite.FavouriteProduct
import com.example.tbcexercises.domain.repository.product.FavouriteProductRepository
import com.example.tbcexercises.presentation.mappers.toFavouriteProductEntity
import com.example.tbcexercises.utils.network_helper.Resource
import com.example.tbcexercises.utils.network_helper.handleNetworkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouriteProductRepositoryImpl @Inject constructor(
    private val favouriteProductDao: FavouriteProductDao,
    private val favouriteProductService: FavouriteProductService,
) : FavouriteProductRepository {

    override suspend fun insertFavouriteProduct(favouriteProduct: FavouriteProduct) {
        favouriteProductDao.insertFavouriteProduct(favouriteProduct.toFavouriteProductEntity())
    }

    override suspend fun insertFavouriteProducts(favouriteProduct: List<FavouriteProduct>) {
        favouriteProductDao.insertFavouriteProducts(favouriteProduct.map { it.toFavouriteProductEntity() })
    }

    override suspend fun deleteFavouriteProduct(favouriteProduct: FavouriteProduct) {
        favouriteProductDao.deleteFavouriteProduct(favouriteProduct.toFavouriteProductEntity())
    }

    override fun getAllFavouriteProducts(): Flow<Resource<List<FavouriteProduct>>> = flow {
        emit(Resource.Loading)
        try {
            favouriteProductDao.getAllFavouriteProducts().collect { products ->
                emit(Resource.Success(products.map { it.toFavouriteProduct() }))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: ""))
        }
    }

    override fun getAllFavouriteProductIds(): Flow<List<Int>> {
        return favouriteProductDao.getAllFavouriteProductIds()
    }


    override fun getFavouriteProductsFromServer(ids: String): Flow<Resource<List<FavouriteProduct>>> {
        return handleNetworkRequest { favouriteProductService.getProductsByIds(ids) }.map { result ->
            when (result) {
                is Resource.Error -> Resource.Error(result.message)
                Resource.Loading -> Resource.Loading
                is Resource.Success -> Resource.Success(result.data.map { it.toFavouriteProduct() })
            }
        }
    }

    override suspend fun saveFavouriteProducts() {
        val localIds = getAllFavouriteProductIds().first()

        if (localIds.isNotEmpty()) {
            getFavouriteProductsFromServer(localIds.joinToString(",") { it.toString() }).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        insertFavouriteProducts(resource.data)
                    }
                    else -> {}
                }
            }
        }
    }
}