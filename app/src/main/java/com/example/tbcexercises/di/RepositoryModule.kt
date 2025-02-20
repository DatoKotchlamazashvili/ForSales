package com.example.tbcexercises.di

import com.example.tbcexercises.data.repository.ProductRepositoryImpl
import com.example.tbcexercises.data.repository.SignInRepositoryImpl
import com.example.tbcexercises.data.repository.SignOutRepositoryImpl
import com.example.tbcexercises.data.repository.SignUpRepositoryImpl
import com.example.tbcexercises.data.repository.UserPreferencesRepositoryImpl
import com.example.tbcexercises.domain.image_loader.ImageLoader
import com.example.tbcexercises.domain.repository.ProductRepository
import com.example.tbcexercises.domain.repository.SignInRepository
import com.example.tbcexercises.domain.repository.SignOutRepository
import com.example.tbcexercises.domain.repository.SignUpRepository
import com.example.tbcexercises.domain.repository.UserPreferencesRepository
import com.example.tbcexercises.utils.GlideImageLoader
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

    @Binds
    @Singleton
    abstract fun bindSignInRepository(impl: SignInRepositoryImpl): SignInRepository

    @Binds
    @Singleton
    abstract fun bindSignOutRepository(impl: SignOutRepositoryImpl): SignOutRepository

    @Binds
    @Singleton
    abstract fun bindSignUpRepository(impl: SignUpRepositoryImpl): SignUpRepository

}