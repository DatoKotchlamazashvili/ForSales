package com.example.tbcexercises.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.tbcexercises.data.local.AppDatabase
import com.example.tbcexercises.data.local.entity.home.HomeProductEntity
import com.example.tbcexercises.data.local.entity.home.RemoteKeyEntity
import com.example.tbcexercises.data.mappers.remote_to_local.toProductHomeEntity
import com.example.tbcexercises.data.remote.service.ProductService
import com.example.tbcexercises.utils.Constants.DURATION_BEFORE_FETCH
import com.example.tbcexercises.utils.Constants.MAX_PRODUCTS_IN_DATABASE
import com.example.tbcexercises.utils.Constants.PER_PAGE_PRODUCT
import com.example.tbcexercises.utils.Constants.PRODUCT_STARTING_PAGE_INDEX
import com.example.tbcexercises.utils.network_helper.ConnectivityObserver
import kotlinx.coroutines.flow.first
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
class HomeProductMediator @Inject constructor(
    private val productService: ProductService,
    private val appDatabase: AppDatabase,
    private val connectivityObserver: ConnectivityObserver
) : RemoteMediator<Int, HomeProductEntity>() {

    override suspend fun initialize(): InitializeAction {
        val lastUpdated = appDatabase.productsDao().getLastUpdatedTime() ?: 0
        val currentTime = System.currentTimeMillis()

        return if (currentTime - lastUpdated > DURATION_BEFORE_FETCH) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HomeProductEntity>
    ): MediatorResult {
        val isConnected = connectivityObserver.isConnected.first()

        if (!isConnected) {
            return MediatorResult.Success(endOfPaginationReached = false)
        }


        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: PRODUCT_STARTING_PAGE_INDEX
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
            val apiResponse = productService.getHomeProducts(page, PER_PAGE_PRODUCT)
            val products = apiResponse.data.map { it.toProductHomeEntity() }
            val endOfPaginationReached = products.isEmpty()

            appDatabase.withTransaction {
                val lastUpdated = appDatabase.productsDao().getLastUpdatedTime() ?: 0
                val currentTime = System.currentTimeMillis()

                if (loadType == LoadType.REFRESH && currentTime - lastUpdated > DURATION_BEFORE_FETCH) {
                    appDatabase.remoteKeysDao().clearRemoteKeys()
                    appDatabase.productsDao().clearProducts()
                }

                val totalProducts = appDatabase.productsDao().getProductCount()
                if (totalProducts >= MAX_PRODUCTS_IN_DATABASE) {
                    appDatabase.productsDao().deleteOldestProducts(PER_PAGE_PRODUCT)
                    appDatabase.remoteKeysDao().deleteOldestRemoteKeys(PER_PAGE_PRODUCT)
                }

                val prevKey = if (page == PRODUCT_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = products.map {
                    RemoteKeyEntity(productId = it.productId, prevKey = prevKey, nextKey = nextKey)
                }
                appDatabase.remoteKeysDao().insertAll(keys)
                appDatabase.productsDao().insertAll(products)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, HomeProductEntity>): RemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { product ->
                appDatabase.remoteKeysDao().remoteKeyByProductId(product.productId)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, HomeProductEntity>): RemoteKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { product ->
                appDatabase.remoteKeysDao().remoteKeyByProductId(product.productId)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, HomeProductEntity>
    ): RemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.productId?.let { productId ->
                appDatabase.remoteKeysDao().remoteKeyByProductId(productId)
            }
        }
    }
}
