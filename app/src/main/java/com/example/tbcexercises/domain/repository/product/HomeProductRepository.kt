package com.example.tbcexercises.domain.repository.product

import androidx.paging.PagingData
import com.example.tbcexercises.data.local.entity.home.HomeProductEntity
import com.example.tbcexercises.domain.model.ProductDetail
import com.example.tbcexercises.utils.network_helper.Resource
import kotlinx.coroutines.flow.Flow

interface HomeProductRepository {

    fun getProductsPager(): Flow<PagingData<HomeProductEntity>>

    fun getProductById(id: Int): Flow<Resource<List<ProductDetail>>>
}