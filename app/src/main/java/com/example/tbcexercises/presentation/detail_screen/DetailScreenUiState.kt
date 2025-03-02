package com.example.tbcexercises.presentation.detail_screen

import com.example.tbcexercises.domain.model.detail.DetailProduct

data class DetailScreenUiState(
    val isLoading: Boolean = false,
    val products: List<DetailProduct> = emptyList(),
    val error: String? = null
)