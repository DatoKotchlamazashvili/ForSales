package com.example.tbcexercises.data.local.entity.home

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey
    val productId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)