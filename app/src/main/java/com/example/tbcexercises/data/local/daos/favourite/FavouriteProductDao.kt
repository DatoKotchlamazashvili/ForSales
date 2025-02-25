package com.example.tbcexercises.data.local.daos.favourite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tbcexercises.data.local.entity.favourite.FavouriteProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteProduct(product: FavouriteProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteProducts(product: List<FavouriteProductEntity>)


    @Delete
    suspend fun deleteFavouriteProduct(product: FavouriteProductEntity)

    @Query("SELECT * FROM favourite_product_entity")
    fun getAllFavouriteProducts(): Flow<List<FavouriteProductEntity>>

    @Query("SELECT productId FROM favourite_product_entity")
    fun getAllFavouriteProductIds(): Flow<List<Int>>


}
