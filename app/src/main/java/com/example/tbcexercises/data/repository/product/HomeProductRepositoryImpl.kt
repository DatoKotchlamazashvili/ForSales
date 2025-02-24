package com.example.tbcexercises.data.repository.product

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tbcexercises.data.local.AppDatabase
import com.example.tbcexercises.data.local.entity.home.HomeProductEntity
import com.example.tbcexercises.data.mappers.remote_to_presentation.toProductDetail
import com.example.tbcexercises.data.paging.HomeProductMediator
import com.example.tbcexercises.data.remote.service.ProductService
import com.example.tbcexercises.domain.model.ProductDetail
import com.example.tbcexercises.domain.repository.product.HomeProductRepository
import com.example.tbcexercises.utils.network_helper.ConnectivityObserver
import com.example.tbcexercises.utils.network_helper.Resource
import com.example.tbcexercises.utils.network_helper.handleNetworkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
    private val appDatabase: AppDatabase,
    private val connectivityObserver: ConnectivityObserver
) :
    HomeProductRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getProductsPager(): Flow<PagingData<HomeProductEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 1
            ),
            remoteMediator = HomeProductMediator(productService, appDatabase, connectivityObserver),
            pagingSourceFactory = { appDatabase.productsDao().getProducts() }
        ).flow
    }

    override fun getProductById(id: Int): Flow<Resource<List<ProductDetail>>> {
        return handleNetworkRequest { productService.getProductById(id) }.map { resource ->
            when (resource) {
                is Resource.Success -> Resource.Success(resource.data.map { it.toProductDetail() })
                is Resource.Error -> Resource.Error(resource.message)
                is Resource.Loading -> Resource.Loading
            }
        }
    }
}