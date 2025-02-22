package com.example.tbcexercises.presentation.register_screen

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.R
import com.example.tbcexercises.domain.repository.SignUpRepository
import com.example.tbcexercises.utils.network_helper.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository
) : ViewModel() {

    private val _signUpState = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signUpState: StateFlow<Resource<FirebaseUser>?> = _signUpState

    private val _validationEvent = MutableSharedFlow<RegisterUiEvent>()
    val validationEvent = _validationEvent.asSharedFlow()

    fun signup(name: String, email: String, password: String) {
        val errorResId = validateInput(name, email, password)
        if (errorResId != null) {
            viewModelScope.launch {
                _validationEvent.emit(RegisterUiEvent.Error(errorResId))
            }
            return
        }

        viewModelScope.launch {
            signUpRepository.signup(name, email, password)
                .collect { _signUpState.value = it }
        }
    }

    private fun validateInput(name: String, email: String, password: String): Int? {
        return when {
            name.isBlank() -> R.string.error_empty_name
            email.isBlank() -> R.string.error_empty_email
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> R.string.error_invalid_email
            password.length < 6 -> R.string.error_weak_password
            else -> null
        }
    }

}
