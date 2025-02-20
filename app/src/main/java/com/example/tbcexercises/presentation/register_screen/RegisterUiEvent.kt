package com.example.tbcexercises.presentation.register_screen

sealed class RegisterUiEvent {
    data class Error(val messageResId: Int) : RegisterUiEvent()
}