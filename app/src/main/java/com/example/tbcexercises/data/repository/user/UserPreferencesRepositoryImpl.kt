package com.example.tbcexercises.data.repository.user

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.tbcexercises.domain.repository.user.UserPreferencesRepository
import com.example.tbcexercises.utils.Constants.REMEMBER_LANGUAGE
import com.example.tbcexercises.utils.Constants.USER_LANGUAGE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class UserPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) :
    UserPreferencesRepository {
    override suspend fun setSession(language: String?, rememberMe: Boolean?) {
        dataStore.edit { preferences ->
            language?.let {
                preferences[USER_LANGUAGE] = it
            }
            if (rememberMe != null) {
                preferences[REMEMBER_LANGUAGE] = rememberMe
            }
            Log.d("executed", "executed")
            Log.d("remembermerepo", preferences[REMEMBER_LANGUAGE].toString())
        }
    }

    override fun getLanguageFlow(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USER_LANGUAGE] ?: "en"
        }
    }

    override fun getRememberMe(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[REMEMBER_LANGUAGE] ?: false
        }
    }
}