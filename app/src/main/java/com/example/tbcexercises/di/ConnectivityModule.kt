package com.example.tbcexercises.di

import com.example.tbcexercises.data.manager.ConnectivityManagerImpl
import com.example.tbcexercises.domain.manager.ConnectivityManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConnectivityModule {

    @Binds
    @Singleton
    abstract fun bindConnectivityManager(
        impl: ConnectivityManagerImpl
    ): ConnectivityManager
}
