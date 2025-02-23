package com.example.tbcexercises.domain.repository.product

import androidx.paging.PagingData
import com.example.tbcexercises.data.local.entity.SearchProductEntity
import kotlinx.coroutines.flow.Flow

interface SearchProductRepository {
    fun getSearchedProductsPager(query:String): Flow<PagingData<SearchProductEntity>>
}