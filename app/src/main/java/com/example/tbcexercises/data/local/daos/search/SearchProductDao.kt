package com.example.tbcexercises.data.local.daos.search

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tbcexercises.data.local.entity.search.SearchProductEntity

@Dao
interface SearchProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<SearchProductEntity>)


    @Query("SELECT * FROM search_product_entity")
    fun getProducts(): PagingSource<Int, SearchProductEntity>

    @Query("DELETE FROM search_product_entity")
    suspend fun clearProducts()

    @Query("SELECT COUNT(*) FROM search_product_entity")
    suspend fun getProductCount(): Int

    @Query("DELETE FROM search_product_entity WHERE productId IN (SELECT productId FROM search_product_entity ORDER BY productId ASC LIMIT :limit)")
    suspend fun deleteOldestProducts(limit: Int)

    @Query("SELECT max(createdAt) FROM search_product_entity")
    suspend fun getLastUpdatedTime(): Long?

}