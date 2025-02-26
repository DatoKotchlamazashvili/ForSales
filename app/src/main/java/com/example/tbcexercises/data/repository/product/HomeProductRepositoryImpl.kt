package com.example.tbcexercises.data.repository.product


import com.example.tbcexercises.data.mappers.toProductDetail
import com.example.tbcexercises.data.remote.service.ProductService
import com.example.tbcexercises.domain.model.DetailProduct
import com.example.tbcexercises.domain.repository.product.HomeProductRepository
import com.example.tbcexercises.data.paging.HomeProductPagingSource
import com.example.tbcexercises.utils.network_helper.Resource
import com.example.tbcexercises.utils.network_helper.handleNetworkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
) :
    HomeProductRepository {
    override fun getProductsPagerSource(
        category: String?,
        searchQuery: String?
    ): HomeProductPagingSource {
        return HomeProductPagingSource(productService,searchQuery,category)
    }


    override fun getProductById(id: Int): Flow<Resource<List<DetailProduct>>> {
        return handleNetworkRequest { productService.getProductById(id) }.map { resource ->
            when (resource) {
                is Resource.Success -> Resource.Success(resource.data.map { it.toProductDetail() })
                is Resource.Error -> Resource.Error(resource.message)
                is Resource.Loading -> Resource.Loading
            }
        }
    }
}