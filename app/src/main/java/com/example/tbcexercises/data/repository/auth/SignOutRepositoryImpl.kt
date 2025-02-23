package com.example.tbcexercises.data.repository.auth

import com.example.tbcexercises.domain.repository.auth.SignOutRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SignOutRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    SignOutRepository {
    override fun logout() {
        firebaseAuth.signOut()
    }
}