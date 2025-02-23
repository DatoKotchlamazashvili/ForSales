package com.example.tbcexercises.data.repository.auth

import com.example.tbcexercises.domain.repository.auth.SignInRepository
import com.example.tbcexercises.utils.network_helper.Resource
import com.google.firebase.auth.FirebaseAuth
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
            } ?: emit(Resource.Error("User not found"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
}
