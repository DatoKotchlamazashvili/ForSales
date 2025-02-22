package com.example.tbcexercises.data.repository

import com.example.tbcexercises.domain.repository.SignUpRepository
import com.example.tbcexercises.utils.network_helper.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : SignUpRepository {

    override fun signup(name: String, email: String, password: String): Flow<Resource<FirebaseUser>> = flow {
        emit(Resource.Loading)
        try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user?.let { firebaseUser ->
                val profileUpdates = userProfileChangeRequest {
                    displayName = name
                }
                firebaseUser.updateProfile(profileUpdates).await()
                emit(Resource.Success(firebaseUser))
            } ?: emit(Resource.Error("Sign-up failed"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
}
