package com.example.tbcexercises.domain.repository.product


import com.example.tbcexercises.data.paging.HomeProductPagingSource

interface HomeProductRepository {

    fun getProductsPagerSource(category: String?, searchQuery: String?): HomeProductPagingSource

}