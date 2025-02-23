package com.example.tbcexercises.data.repository.user

import com.example.tbcexercises.domain.repository.user.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    UserRepository {
    override fun getUser(): FirebaseUser {
        return firebaseAuth.currentUser!!
    }
}