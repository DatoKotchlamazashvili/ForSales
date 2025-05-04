package com.example.tbcexercises.presentation.register_screen

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.R
import com.example.tbcexercises.domain.repository.auth.SignUpRepository
import com.example.tbcexercises.utils.network_helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterScreenUiState())
    val uiState: StateFlow<RegisterScreenUiState> = _uiState

    fun signup(name: String, email: String, password: String,repeatPassword: String) {
        val validationError = validateInput(name, email, password,repeatPassword)

        if (validationError != null) {
            _uiState.update { currentState ->
                currentState.copy(validationError = validationError)
            }
            return
        }

        viewModelScope.launch {
            signUpRepository.signup(name, email, password)
                .collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            _uiState.update { currentState ->
                                currentState.copy(
                                    isLoading = true,
                                    validationError = null
                                )
                            }
                        }
                        is Resource.Success -> {
                            _uiState.update { currentState ->
                                currentState.copy(
                                    isLoading = false,
                                    user = resource.data,
                                    validationError = null
                                )
                            }
                        }
                        is Resource.Error -> {
                            _uiState.update { currentState ->
                                currentState.copy(
                                    isLoading = false,
                                    validationError = try {
                                        resource.message.toInt()
                                    } catch (e: Exception) {
                                        null
                                    }
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun validateInput(name: String, email: String, password: String,repeatPassword:String): Int? {
        return when {
            name.isBlank() -> R.string.error_empty_name
            email.isBlank() -> R.string.error_empty_email
            password != repeatPassword ->R.string.error_password_mismatch
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> R.string.error_invalid_email
            password.length < 6 -> R.string.error_weak_password
            else -> null
        }
    }

    fun clearValidationError() {
        _uiState.update { currentState ->
            currentState.copy(validationError = null)
        }
    }
}