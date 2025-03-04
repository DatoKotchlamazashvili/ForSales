package com.example.tbcexercises.domain.manager

import kotlinx.coroutines.flow.Flow

interface ConnectivityManager {
    val isConnected: Flow<Boolean>
}