package com.example.tbcexercises.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.tbcexercises.R
import com.example.tbcexercises.domain.model.Language

object Constants {
    val USER_LANGUAGE = stringPreferencesKey("user_language")
    val REMEMBER_LANGUAGE = booleanPreferencesKey("remember_me")
    const val PRODUCT_STARTING_PAGE_INDEX = 1

    const val PER_PAGE_PRODUCT = 20
    const val DURATION_BEFORE_FETCH = 20 * 60 * 1000 //this is 20 minutes

    const val MAX_PRODUCTS_IN_DATABASE = 60

    val languages = listOf(
        Language("en", R.drawable.ic_flag_english),
        Language("ka", R.drawable.iic_flag_georgia)
    )

}