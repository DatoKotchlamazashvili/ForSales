package com.example.tbcexercises.presentation.home_screen

import com.example.tbcexercises.domain.model.Category

data class HomeScreenUiState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val categoryError: String? = null,
    val searchQuery: String? = null,
    val selectedCategory: String? = null
)