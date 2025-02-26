package com.example.tbcexercises.domain.repository.product


import com.example.tbcexercises.data.paging.HomeProductPagingSource
import com.example.tbcexercises.domain.model.DetailProduct
import com.example.tbcexercises.utils.network_helper.Resource
import kotlinx.coroutines.flow.Flow

interface HomeProductRepository {

    fun getProductsPagerSource(category: String?, searchQuery: String?): HomeProductPagingSource

    fun getProductById(id: Int): Flow<Resource<List<DetailProduct>>>
}