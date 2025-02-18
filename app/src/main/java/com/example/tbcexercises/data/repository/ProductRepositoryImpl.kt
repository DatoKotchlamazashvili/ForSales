package com.example.tbcexercises.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tbcexercises.data.local.AppDatabase
import com.example.tbcexercises.data.local.entity.ProductHomeEntity
import com.example.tbcexercises.data.remote.response.ProductResponse
import com.example.tbcexercises.data.remote.service.ProductService
import com.example.tbcexercises.domain.repository.ProductRepository
import com.example.tbcexercises.utils.Resource
import com.example.tbcexercises.utils.handleNetworkRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val productService: ProductService,
    private val appDatabase: AppDatabase
    ) :
    ProductRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getProductsPager(): Flow<PagingData<ProductHomeEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 1
            ),
            remoteMediator = ProductMediator(productService, appDatabase),
            pagingSourceFactory = { appDatabase.productsDao().getProducts() }
        ).flow
    }

    override fun getProductById(id: Int): Flow<Resource<List<ProductResponse>>> {
        return handleNetworkRequest { productService.getProductById(id) }
    }
}