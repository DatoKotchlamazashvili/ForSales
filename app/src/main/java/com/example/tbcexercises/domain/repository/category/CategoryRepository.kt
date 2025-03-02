package com.example.tbcexercises.domain.repository.category

import com.example.tbcexercises.domain.model.home.Category
import com.example.tbcexercises.utils.network_helper.Resource
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {


    fun getCategories(): Flow<Resource<List<Category>>>
}