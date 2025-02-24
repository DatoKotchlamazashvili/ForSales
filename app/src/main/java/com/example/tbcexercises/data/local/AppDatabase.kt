package com.example.tbcexercises.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tbcexercises.data.local.converter.Converter
import com.example.tbcexercises.data.local.daos.favourite.FavouriteProductDao
import com.example.tbcexercises.data.local.daos.home.HomeProductDao
import com.example.tbcexercises.data.local.daos.home.RemoteKeysDao
import com.example.tbcexercises.data.local.daos.search.SearchProductDao
import com.example.tbcexercises.data.local.daos.search.SearchRemoteKeysDao
import com.example.tbcexercises.data.local.entity.favourite.FavouriteProductEntity
import com.example.tbcexercises.data.local.entity.home.HomeProductEntity
import com.example.tbcexercises.data.local.entity.home.RemoteKeyEntity
import com.example.tbcexercises.data.local.entity.search.SearchProductEntity
import com.example.tbcexercises.data.local.entity.search.SearchRemoteKeyEntity

@Database(
    entities = [HomeProductEntity::class,
        RemoteKeyEntity::class,
        FavouriteProductEntity::class,
        SearchProductEntity::class,
        SearchRemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productsDao(): HomeProductDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun favouriteProductsDao(): FavouriteProductDao
    abstract fun searchProductsDao(): SearchProductDao
    abstract fun searchRemoteKeysDao(): SearchRemoteKeysDao


}