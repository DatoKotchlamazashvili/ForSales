package com.example.tbcexercises.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <T> handleNetworkRequest(apiCall: suspend () -> Response<T>): Flow<Resource<T>> = flow {
    try {
        emit(Resource.Loading)
        val response = apiCall()

        if (response.isSuccessful) {
            response.body()?.let {
                emit(Resource.Success(it))
            } ?: emit(Resource.Error(""))
        } else {
            emit(Resource.Error(response.message()))
        }
    } catch (e: Exception) {
        emit(Resource.Error(e.localizedMessage ?: ""))
    }
}