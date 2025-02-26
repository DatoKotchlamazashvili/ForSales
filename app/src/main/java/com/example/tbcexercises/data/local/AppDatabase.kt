package com.example.tbcexercises.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tbcexercises.data.local.daos.FavouriteProductDao
import com.example.tbcexercises.data.local.entity.FavouriteProductEntity

@Database(
    entities = [FavouriteProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favouriteProductsDao(): FavouriteProductDao


}