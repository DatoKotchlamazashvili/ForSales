package com.example.tbcexercises.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.tbcexercises.data.local.AppDatabase
import com.example.tbcexercises.data.local.entity.ProductHomeEntity
import com.example.tbcexercises.data.local.entity.RemoteKeyEntity
import com.example.tbcexercises.data.mappers.remote_to_local.toProductHomeEntity
import com.example.tbcexercises.data.remote.service.ProductService
import javax.inject.Inject


private const val PRODUCT_STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class ProductMediator @Inject constructor(
    private val productService: ProductService,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, ProductHomeEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductHomeEntity>
    ): MediatorResult {

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
            val apiResponse = productService.getHomeProducts(page, 20)

            val products = apiResponse.data.map { it.toProductHomeEntity() }

            Log.d("productsmediator",products.toString())
            val endOfPaginationReached = products.isEmpty()
            appDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    appDatabase.remoteKeysDao().clearRemoteKeys()
                    appDatabase.productsDao().clearUsers()
                }
                val prevKey = if (page == PRODUCT_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = products.map {
                    RemoteKeyEntity(productId = it.productId, prevKey = prevKey, nextKey = nextKey)
                }
                appDatabase.remoteKeysDao().insertAll(keys)
                Log.d("productbeforeinsert",products.toString())

                appDatabase.productsDao().insertAll(products)
                Log.d("productafterinsert",products.toString())


            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            Log.d("exception",exception.message.toString())
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ProductHomeEntity>): RemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { product ->
                appDatabase.remoteKeysDao().remoteKeyByUserId(product.productId)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ProductHomeEntity>): RemoteKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { product ->
                appDatabase.remoteKeysDao().remoteKeyByUserId(product.productId)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ProductHomeEntity>
    ): RemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.productId?.let { productId ->
                appDatabase.remoteKeysDao().remoteKeyByUserId(productId)
            }
        }
    }
}
