package com.example.tbcexercises.di

import com.example.tbcexercises.utils.network_helper.ConnectivityObserver
import com.example.tbcexercises.utils.network_helper.ConnectivityObserverImpl
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
    abstract fun bindConnectivityObserver(
        impl: ConnectivityObserverImpl
    ): ConnectivityObserver
}
