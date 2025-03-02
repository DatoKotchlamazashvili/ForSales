package com.example.tbcexercises.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tbcexercises.data.remote.response.home.HomeResponse
import com.example.tbcexercises.data.remote.service.HomeProductService
import retrofit2.HttpException
import java.io.IOException

class HomeProductPagingSource(
    private val apiService: HomeProductService,
    private val searchQuery: String?,
    private val category: String?
) : PagingSource<Int, HomeResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HomeResponse> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getProducts(
                page = page,
                perPage = params.loadSize,
                search = searchQuery,
                category = category
            )
            LoadResult.Page(
                data = response.data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.data.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, HomeResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
