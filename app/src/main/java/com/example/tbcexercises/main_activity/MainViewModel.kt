package com.example.tbcexercises.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val userPreferencesRepository: UserPreferencesRepository) :
    ViewModel() {

    val languagePreference = userPreferencesRepository.getLanguageFlow()


    fun setSession(language: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferencesRepository.setSession(language)
        }
    }


}