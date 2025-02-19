package com.example.tbcexercises.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    suspend fun setSession(language: String)


    fun getLanguageFlow(): Flow<String>
}