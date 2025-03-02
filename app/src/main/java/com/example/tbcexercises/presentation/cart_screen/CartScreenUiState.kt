package com.example.tbcexercises.presentation.cart_screen

import com.example.tbcexercises.domain.model.cart.CartProduct
import com.example.tbcexercises.domain.model.cart.Company

data class CartScreenUiState(
    val cartProducts: List<CartProduct> = emptyList(),
    val companies: List<Company> = emptyList(),
    val selectedCompanyName: String? = null,
    val totalPrice: Float = 0f,
    val isLoading: Boolean = false,
    val error: String? = null
)