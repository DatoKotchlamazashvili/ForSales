package com.example.tbcexercises.domain.repository.user

import com.google.firebase.auth.FirebaseUser

interface UserRepository {

    fun getUser() : FirebaseUser?
}