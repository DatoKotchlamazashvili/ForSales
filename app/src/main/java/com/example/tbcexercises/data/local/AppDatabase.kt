package com.example.tbcexercises.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tbcexercises.data.local.converter.Converter
import com.example.tbcexercises.data.local.daos.ProductDao
import com.example.tbcexercises.data.local.daos.RemoteKeysDao
import com.example.tbcexercises.data.local.entity.ProductHomeEntity
import com.example.tbcexercises.data.local.entity.RemoteKeyEntity

@Database(
    entities = [ProductHomeEntity::class, RemoteKeyEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}