package com.example.tbcexercises.domain.repository

import com.example.tbcexercises.utils.network_helper.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface SignInRepository {
    fun login(email: String, password: String): Flow<Resource<FirebaseUser>>

}
