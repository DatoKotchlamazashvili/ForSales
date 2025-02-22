package com.example.tbcexercises.presentation.profile_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.repository.SignOutRepository
import com.example.tbcexercises.domain.repository.UserPreferencesRepository
import com.example.tbcexercises.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    userRepository: UserRepository,
    private val signOutRepository: SignOutRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val user = userRepository.getUser()

    suspend fun signOut() {
        withContext(NonCancellable) {
            userPreferencesRepository.setSession(language = null, rememberMe = false)
            signOutRepository.logout()
        }
    }

}