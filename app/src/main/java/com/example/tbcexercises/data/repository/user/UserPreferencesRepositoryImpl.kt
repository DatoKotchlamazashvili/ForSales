package com.example.tbcexercises.data.repository.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.tbcexercises.domain.repository.user.UserPreferencesRepository
import com.example.tbcexercises.utils.Constants.DEFAULT_APP_LANGUAGE
import com.example.tbcexercises.utils.Constants.DEFAULT_REMEMBER_ME
import com.example.tbcexercises.utils.Constants.REMEMBER_LANGUAGE
import com.example.tbcexercises.utils.Constants.USER_LANGUAGE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class UserPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) :
    UserPreferencesRepository {


    override suspend fun setLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[USER_LANGUAGE] = language

        }
    }

    override suspend fun setRememberMe(rememberMe: Boolean) {
        dataStore.edit { preferences ->
            preferences[REMEMBER_LANGUAGE] = rememberMe
        }
    }

    override fun getLanguageFlow(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USER_LANGUAGE] ?: DEFAULT_APP_LANGUAGE
        }
    }

    override fun getRememberMe(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[REMEMBER_LANGUAGE] ?: DEFAULT_REMEMBER_ME
        }
    }
}