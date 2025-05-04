package com.example.tbcexercises.presentation.detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.repository.product.DetailProductRepository
import com.example.tbcexercises.utils.network_helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val detailProductRepository: DetailProductRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(DetailScreenUiState(isLoading = true))
    val uiState: StateFlow<DetailScreenUiState> = _uiState

    fun getProduct(id: Int) {
        viewModelScope.launch {
            detailProductRepository.getProductById(id).collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = true,
                                error = null
                            )
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                products = resource.data,
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                error = resource.message
                            )
                        }
                    }
                }
            }
        }
    }
}
