package com.example.tbcexercises.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.tbcexercises.data.local.AppDatabase
import com.example.tbcexercises.data.local.entity.search.SearchProductEntity
import com.example.tbcexercises.data.local.entity.search.SearchRemoteKeyEntity
import com.example.tbcexercises.data.mappers.remote_to_local.toSearchProductEntity
import com.example.tbcexercises.data.remote.service.SearchProductService
import com.example.tbcexercises.utils.Constants.DURATION_BEFORE_FETCH
import com.example.tbcexercises.utils.Constants.MAX_PRODUCTS_IN_DATABASE
import com.example.tbcexercises.utils.Constants.PER_PAGE_PRODUCT
import com.example.tbcexercises.utils.Constants.PRODUCT_STARTING_PAGE_INDEX
import com.example.tbcexercises.utils.network_helper.ConnectivityObserver
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class SearchProductMediator @Inject constructor(
    private val searchProductService: SearchProductService,
    private val appDatabase: AppDatabase,
    private val connectivityObserver: ConnectivityObserver,
    val query: String
) : RemoteMediator<Int, SearchProductEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchProductEntity>
    ): MediatorResult {
        val isConnected = connectivityObserver.isConnected.first()
        if (!isConnected) {
            return MediatorResult.Success(endOfPaginationReached = false)
        }

        val page = when (loadType) {
            LoadType.REFRESH -> {
                PRODUCT_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val apiResponse =
                searchProductService.getSearchedProducts(query, page, PER_PAGE_PRODUCT)
            val products = apiResponse.data.map { it.toSearchProductEntity() }
            val endOfPaginationReached = products.isEmpty()

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.searchRemoteKeysDao().clearRemoteKeys()
                    appDatabase.searchProductsDao().clearProducts()
                }

                val totalProducts = appDatabase.searchProductsDao().getProductCount()
                if (totalProducts >= MAX_PRODUCTS_IN_DATABASE) {
                    appDatabase.searchProductsDao().deleteOldestProducts(PER_PAGE_PRODUCT)
                    appDatabase.searchRemoteKeysDao().deleteOldestRemoteKeys(PER_PAGE_PRODUCT)
                }

                val prevKey = if (page == PRODUCT_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = products.map {
                    SearchRemoteKeyEntity(
                        productId = it.productId,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                appDatabase.searchRemoteKeysDao().insertAll(keys)
                appDatabase.searchProductsDao().insertAll(products)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, SearchProductEntity>): SearchRemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { product ->
                appDatabase.searchRemoteKeysDao().remoteKeyByProductId(product.productId)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, SearchProductEntity>): SearchRemoteKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { product ->
                appDatabase.searchRemoteKeysDao().remoteKeyByProductId(product.productId)
            }
    }
}
