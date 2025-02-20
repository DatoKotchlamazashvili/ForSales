package com.example.tbcexercises.data.repository

import com.example.tbcexercises.domain.repository.SignOutRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SignOutRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    SignOutRepository {
    override fun logout() {
        firebaseAuth.signOut()
    }
}