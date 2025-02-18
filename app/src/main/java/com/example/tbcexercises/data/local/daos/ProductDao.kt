package com.example.tbcexercises.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tbcexercises.data.local.entity.ProductHomeEntity

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductHomeEntity>)

    @Query("SELECT * FROM product_home_entity")
    fun getProducts(): PagingSource<Int, ProductHomeEntity>

    @Query("DELETE FROM product_home_entity")
    suspend fun clearUsers()
}