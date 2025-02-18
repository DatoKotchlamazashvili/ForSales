package com.example.tbcexercises.domain.repository

import androidx.paging.PagingData
import com.example.tbcexercises.data.local.entity.ProductHomeEntity
import com.example.tbcexercises.data.remote.response.ProductResponse
import com.example.tbcexercises.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProductsPager(): Flow<PagingData<ProductHomeEntity>>

    fun getProductById(id: Int): Flow<Resource<List<ProductResponse>>>
}