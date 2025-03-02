package com.example.tbcexercises.presentation.login_screen

import com.google.firebase.auth.FirebaseUser

data class LoginScreenUiState(
    val isLoading: Boolean = false,
    val user: FirebaseUser? = null,
    val error: String? = null,
    val email: String = "",
    val password: String = ""
)