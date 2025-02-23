package com.example.tbcexercises.presentation.login_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.repository.auth.SignInRepository
import com.example.tbcexercises.domain.repository.user.UserPreferencesRepository
import com.example.tbcexercises.utils.network_helper.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInRepository: SignInRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _signInState = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signInState: StateFlow<Resource<FirebaseUser>?> = _signInState

    fun login(email: String, password: String, rememberMe: Boolean) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewModelScope.launch {
                Log.d("rememberMeNewValue", rememberMe.toString())
                userPreferencesRepository.setSession(language = null, rememberMe = rememberMe)

                signInRepository.login(email, password)
                    .collectLatest { _signInState.value = it }

            }
        }
    }

}
