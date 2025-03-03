package com.example.tbcexercises.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.tbcexercises.R
import com.example.tbcexercises.domain.model.Language

object Constants {
    val USER_LANGUAGE = stringPreferencesKey("user_language")
    val REMEMBER_LANGUAGE = booleanPreferencesKey("remember_me")

    const val DEFAULT_APP_LANGUAGE = "en"
    const val DEFAULT_REMEMBER_ME = false
    const val MAX_FAVOURITE_PRODUCT = 15

    const val MAX_CART_PRODUCT_COUNT = 6
    val languages = listOf(
        Language("en", R.drawable.ic_flag_english),
        Language("ka", R.drawable.iic_flag_georgia)
    )

}