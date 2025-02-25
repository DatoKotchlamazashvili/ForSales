package com.example.tbcexercises.data.repository.product

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tbcexercises.data.local.AppDatabase
import com.example.tbcexercises.data.local.entity.search.SearchProductEntity
import com.example.tbcexercises.data.paging.SearchProductMediator
import com.example.tbcexercises.data.remote.service.SearchProductService
import com.example.tbcexercises.domain.repository.product.SearchProductRepository
import com.example.tbcexercises.utils.Constants.PER_PAGE_PRODUCT
import com.example.tbcexercises.data.connectivity.ConnectivityObserver
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchProductRepositoryImpl @Inject constructor(
    private val searchProductService: SearchProductService,
    private val appDatabase: AppDatabase,
    private val connectivityObserver: ConnectivityObserver
) : SearchProductRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getSearchedProductsPager(query: String): Flow<PagingData<SearchProductEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = PER_PAGE_PRODUCT,
                prefetchDistance = 1
            ),
            remoteMediator = SearchProductMediator(
                searchProductService,
                appDatabase,
                connectivityObserver,
                query
            ),
            pagingSourceFactory = { appDatabase.searchProductsDao().getProducts(query) }
        ).flow
    }
}