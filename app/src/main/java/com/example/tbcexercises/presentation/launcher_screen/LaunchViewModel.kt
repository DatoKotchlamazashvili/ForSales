package com.example.tbcexercises.presentation.launcher_screen

import androidx.lifecycle.ViewModel
import com.example.tbcexercises.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(userPreferencesRepository: UserPreferencesRepository) :ViewModel() {

    val rememberMeFlow = userPreferencesRepository.getRememberMe()

}