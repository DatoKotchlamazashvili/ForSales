package com.example.tbcexercises.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tbcexercises.data.local.entity.SearchRemoteKeyEntity

@Dao
interface SearchRemoteKeysDao {
    @Query("SELECT * FROM search_remote_keys WHERE productId = :productId")
    suspend fun remoteKeyByProductId(productId: Int): SearchRemoteKeyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<SearchRemoteKeyEntity>)

    @Query("DELETE FROM search_remote_keys")
    suspend fun clearRemoteKeys()

    @Query("DELETE FROM search_remote_keys WHERE productId IN (SELECT productId FROM search_remote_keys ORDER BY productId ASC LIMIT :limit)")
    suspend fun deleteOldestRemoteKeys(limit :Int)
}