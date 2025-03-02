package com.example.tbcexercises.data.repository.product

import com.example.tbcexercises.data.mappers.detail.toProductDetail
import com.example.tbcexercises.data.remote.service.DetailProductService
import com.example.tbcexercises.domain.model.detail.DetailProduct
import com.example.tbcexercises.domain.repository.product.DetailProductRepository
import com.example.tbcexercises.utils.network_helper.Resource
import com.example.tbcexercises.utils.network_helper.handleNetworkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DetailProductRepositoryImpl @Inject constructor(private val detailProductService: DetailProductService) :
    DetailProductRepository {
    override fun getProductById(id: Int): Flow<Resource<List<DetailProduct>>> {
        return handleNetworkRequest { detailProductService.getProductById(id) }.map { resource ->
            when (resource) {
                is Resource.Success -> Resource.Success(resource.data.map { it.toProductDetail() })
                is Resource.Error -> Resource.Error(resource.message)
                is Resource.Loading -> Resource.Loading
            }
        }
    }
}