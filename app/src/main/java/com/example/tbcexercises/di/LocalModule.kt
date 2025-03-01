package com.example.tbcexercises.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.tbcexercises.data.local.AppDatabase
import com.example.tbcexercises.data.local.daos.CartProductDao
import com.example.tbcexercises.data.local.daos.FavouriteProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "App.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavouriteProductsDao(database: AppDatabase): FavouriteProductDao {
        return database.favouriteProductsDao()
    }

    @Provides
    @Singleton
    fun provideCartProductsDao(database: AppDatabase): CartProductDao {
        return database.cartProductsDao()
    }


    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

}