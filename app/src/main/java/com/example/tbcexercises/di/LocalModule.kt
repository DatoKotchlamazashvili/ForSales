package com.example.tbcexercises.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.tbcexercises.data.local.AppDatabase
import com.example.tbcexercises.data.local.daos.favourite.FavouriteProductDao
import com.example.tbcexercises.data.local.daos.home.HomeProductDao
import com.example.tbcexercises.data.local.daos.home.RemoteKeysDao
import com.example.tbcexercises.data.local.daos.search.SearchProductDao
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
    fun provideUserDao(database: AppDatabase): HomeProductDao {
        return database.productsDao()
    }

    @Provides
    @Singleton
    fun provideRemoteKeysDao(database: AppDatabase): RemoteKeysDao {
        return database.remoteKeysDao()
    }

    @Provides
    @Singleton
    fun provideFavouriteProductsDao(database: AppDatabase): FavouriteProductDao {
        return database.favouriteProductsDao()
    }


    @Provides
    @Singleton
    fun provideSearchProductsDao(database: AppDatabase): SearchProductDao {
        return database.searchProductsDao()
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

}