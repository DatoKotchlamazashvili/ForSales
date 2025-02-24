package com.example.tbcexercises.data.local.entity.search

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_remote_keys")
data class SearchRemoteKeyEntity(
    @PrimaryKey
    val productId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)