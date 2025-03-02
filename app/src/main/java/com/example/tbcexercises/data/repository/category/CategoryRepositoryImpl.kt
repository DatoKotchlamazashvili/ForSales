package com.example.tbcexercises.data.repository.category

import com.example.tbcexercises.data.mappers.cateogory.toCategory
import com.example.tbcexercises.data.remote.service.CategoryService
import com.example.tbcexercises.domain.model.Category
import com.example.tbcexercises.domain.repository.category.CategoryRepository
import com.example.tbcexercises.utils.network_helper.Resource
import com.example.tbcexercises.utils.network_helper.handleNetworkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryService: CategoryService
) : CategoryRepository {
    override fun getCategories(): Flow<Resource<List<Category>>> {
        return handleNetworkRequest { categoryService.getCategories() }.map { response ->
            when (response) {
                is Resource.Success -> Resource.Success(response.data.map { it.toCategory() })
                is Resource.Error -> Resource.Error(response.message)
                is Resource.Loading -> Resource.Loading
            }
        }
    }
}