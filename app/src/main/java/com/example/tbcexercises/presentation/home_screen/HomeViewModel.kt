package com.example.tbcexercises.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.tbcexercises.data.mappers.local_to_presentation.toProduct
import com.example.tbcexercises.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) :
    ViewModel() {
    val usersFlow = productRepository.getProductsPager().map { pagingData ->
        pagingData.map { it.toProduct() }
    }.cachedIn(viewModelScope)

}