package com.example.tbcexercises.presentation.home_screen

import com.example.tbcexercises.domain.model.home.Category

data class HomeScreenUiState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val error: Int? = null,
    val searchQuery: String? = null,
    val selectedCategory: String? = null,
    val isOnline: Boolean = false
)