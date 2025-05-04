package com.example.tbcexercises.data.repository.auth

import com.example.tbcexercises.R
import com.example.tbcexercises.domain.repository.auth.SignUpRepository
import com.example.tbcexercises.utils.network_helper.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
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

    override fun signup(
        name: String,
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser>> = flow {
        emit(Resource.Loading)
        try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user?.let { firebaseUser ->
                val profileUpdates = userProfileChangeRequest {
                    displayName = name
                }
                firebaseUser.updateProfile(profileUpdates).await()
                emit(Resource.Success(firebaseUser))
            } ?: emit(Resource.Error(R.string.sign_up_failed.toString()))
        } catch (e: Exception) {
            when (e) {
                is FirebaseAuthUserCollisionException -> {
                    emit(Resource.Error(R.string.email_is_already_used.toString()))
                }

                else -> {
                    emit(Resource.Error(R.string.unknown_error.toString()))
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}
