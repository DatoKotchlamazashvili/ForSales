package com.example.tbcexercises.data.repository.product


import com.example.tbcexercises.data.paging.HomeProductPagingSource
import com.example.tbcexercises.data.remote.service.HomeProductService
import com.example.tbcexercises.domain.repository.product.HomeProductRepository
import javax.inject.Inject

class HomeProductRepositoryImpl @Inject constructor(
    private val homeProductService: HomeProductService,
) :
    HomeProductRepository {
    override fun getProductsPagerSource(
        category: String?,
        searchQuery: String?
    ): HomeProductPagingSource {
        return HomeProductPagingSource(homeProductService,searchQuery,category)
    }
}