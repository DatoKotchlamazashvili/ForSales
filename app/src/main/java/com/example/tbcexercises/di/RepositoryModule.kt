package com.example.tbcexercises.di

import com.example.tbcexercises.data.repository.ProductRepositoryImpl
import com.example.tbcexercises.data.repository.UserPreferencesRepositoryImpl
import com.example.tbcexercises.domain.repository.ProductRepository
import com.example.tbcexercises.domain.repository.UserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    abstract fun bindUserPreferencesRepository(impl: UserPreferencesRepositoryImpl): UserPreferencesRepository
}