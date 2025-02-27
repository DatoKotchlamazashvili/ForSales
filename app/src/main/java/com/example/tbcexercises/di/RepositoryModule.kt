package com.example.tbcexercises.di

import com.example.tbcexercises.data.repository.product.FavouriteProductRepositoryImpl
import com.example.tbcexercises.data.repository.product.HomeProductRepositoryImpl
import com.example.tbcexercises.data.repository.auth.SignInRepositoryImpl
import com.example.tbcexercises.data.repository.auth.SignOutRepositoryImpl
import com.example.tbcexercises.data.repository.auth.SignUpRepositoryImpl
import com.example.tbcexercises.data.repository.category.CategoryRepositoryImpl
import com.example.tbcexercises.data.repository.user.UserPreferencesRepositoryImpl
import com.example.tbcexercises.data.repository.user.UserRepositoryImpl
import com.example.tbcexercises.domain.repository.product.FavouriteProductRepository
import com.example.tbcexercises.domain.repository.product.HomeProductRepository
import com.example.tbcexercises.domain.repository.auth.SignInRepository
import com.example.tbcexercises.domain.repository.auth.SignOutRepository
import com.example.tbcexercises.domain.repository.auth.SignUpRepository
import com.example.tbcexercises.domain.repository.category.CategoryRepository
import com.example.tbcexercises.domain.repository.user.UserPreferencesRepository
import com.example.tbcexercises.domain.repository.user.UserRepository
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
    abstract fun bindProductRepository(impl: HomeProductRepositoryImpl): HomeProductRepository

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

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindFavouriteProductRepository(impl: FavouriteProductRepositoryImpl): FavouriteProductRepository


    @Binds
    @Singleton
    abstract fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository


}