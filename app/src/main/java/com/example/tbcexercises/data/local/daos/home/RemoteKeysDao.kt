package com.example.tbcexercises.data.local.daos.home

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tbcexercises.data.local.entity.home.RemoteKeyEntity

@Dao
interface RemoteKeysDao {
    @Query("SELECT * FROM remote_keys WHERE productId = :productId")
    suspend fun remoteKeyByProductId(productId: Int): RemoteKeyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeyEntity>)

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()

    @Query("DELETE FROM remote_keys WHERE productId IN (SELECT productId FROM product_home_entity ORDER BY productId ASC LIMIT :limit)")
    suspend fun deleteOldestRemoteKeys(limit :Int)
}