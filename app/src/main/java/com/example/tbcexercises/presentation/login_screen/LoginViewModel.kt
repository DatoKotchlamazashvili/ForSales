package com.example.tbcexercises.presentation.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.repository.auth.SignInRepository
import com.example.tbcexercises.domain.repository.user.UserPreferencesRepository
import com.example.tbcexercises.utils.network_helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInRepository: SignInRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginScreenUiState())
    val uiState: StateFlow<LoginScreenUiState> = _uiState

    fun login(email: String, password: String, rememberMe: Boolean) {
        if (email.isEmpty() || password.isEmpty()) {
            return
        }

        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(isLoading = true, error = null)
            }

            userPreferencesRepository.setRememberMe(rememberMe = rememberMe)

            signInRepository.login(email, password)
                .collectLatest { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            _uiState.update { currentState ->
                                currentState.copy(isLoading = true, error = null)
                            }
                        }
                        is Resource.Success -> {
                            _uiState.update { currentState ->
                                currentState.copy(
                                    isLoading = false,
                                    user = resource.data,
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

    fun updateCredentials(email: String, password: String) {
        _uiState.update { currentState ->
            currentState.copy(email = email, password = password)
        }
    }
}
