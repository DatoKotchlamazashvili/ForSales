package com.example.tbcexercises.presentation.register_screen

import com.google.firebase.auth.FirebaseUser

data class RegisterScreenUiState(
    val isLoading: Boolean = false,
    val user: FirebaseUser? = null,
    val error: String? = null,
    val validationError: Int? = null
)
