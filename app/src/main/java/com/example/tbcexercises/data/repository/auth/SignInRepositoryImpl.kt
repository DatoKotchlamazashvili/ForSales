package com.example.tbcexercises.data.repository.auth

import android.util.Log
import com.example.tbcexercises.R
import com.example.tbcexercises.domain.repository.auth.SignInRepository
import com.example.tbcexercises.utils.network_helper.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : SignInRepository {

    override fun login(email: String, password: String): Flow<Resource<FirebaseUser>> = flow {
        emit(Resource.Loading)
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user?.let {
                emit(Resource.Success(it))
            } ?: emit(Resource.Error(R.string.user_not_found.toString()))
        } catch (e: Exception) {

            Log.d("error", e.toString())
            when (e) {
                is FirebaseAuthInvalidCredentialsException -> {
                    emit(Resource.Error(R.string.error_user_not_found.toString()))
                }

                else -> {
                    emit(Resource.Error(R.string.unknown_error.toString()))
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}