package com.example.tbcexercises.domain.repository.user

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {


    suspend fun setLanguage(language: String)

    suspend fun setRememberMe(rememberMe: Boolean)

    fun getLanguageFlow(): Flow<String>

    fun getRememberMe(): Flow<Boolean>

}