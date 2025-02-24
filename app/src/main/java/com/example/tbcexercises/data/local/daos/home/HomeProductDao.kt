package com.example.tbcexercises.data.local.daos.home

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tbcexercises.data.local.entity.home.HomeProductEntity

@Dao
interface HomeProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<HomeProductEntity>)


    @Query("SELECT * FROM product_home_entity")
    fun getProducts(): PagingSource<Int, HomeProductEntity>

    @Query("DELETE FROM product_home_entity")
    suspend fun clearProducts()

    @Query("SELECT COUNT(*) FROM product_home_entity")
    suspend fun getProductCount(): Int

    @Query("DELETE FROM product_home_entity WHERE productId IN (SELECT productId FROM product_home_entity ORDER BY productId ASC LIMIT :limit)")
    suspend fun deleteOldestProducts(limit: Int)

    @Query("SELECT max(createdAt) FROM product_home_entity")
    suspend fun getLastUpdatedTime(): Long?

}