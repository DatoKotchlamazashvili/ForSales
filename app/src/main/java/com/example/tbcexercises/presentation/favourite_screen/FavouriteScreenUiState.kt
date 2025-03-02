package com.example.tbcexercises.presentation.favourite_screen

import com.example.tbcexercises.domain.model.FavouriteProduct

data class FavouriteScreenUiState(
    val isLoading: Boolean = false,
    val favouriteProducts: List<FavouriteProduct> = emptyList(),
    val error: String? = null
)