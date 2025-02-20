package com.example.tbcexercises.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.repository.SignOutRepository
import com.example.tbcexercises.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val signOutRepository: SignOutRepository
) :
    ViewModel() {

    val languagePreference = userPreferencesRepository.getLanguageFlow()

    val rememberMe = userPreferencesRepository.getRememberMe()

    fun setSession(language: String?, rememberMe: Boolean?) {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferencesRepository.setSession(language, rememberMe)
        }
    }

    fun logout() {
        viewModelScope.launch {
            rememberMe.collectLatest { value ->
                if (!value) {
                    signOutRepository.logout()
                }

            }
        }
    }


}