package com.example.tbcexercises.domain.repository

import com.google.firebase.auth.FirebaseUser

interface UserRepository {

    fun getUser() : FirebaseUser
}