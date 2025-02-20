package com.example.tbcexercises.domain.repository

import com.example.tbcexercises.utils.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow


interface SignUpRepository {
    fun signup(name: String, email: String, password: String): Flow<Resource<FirebaseUser>>
}
