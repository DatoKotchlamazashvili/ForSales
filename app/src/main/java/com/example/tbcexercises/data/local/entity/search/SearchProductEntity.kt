package com.example.tbcexercises.data.local.entity.search

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_product_entity")
data class SearchProductEntity(
    @PrimaryKey val productId: Int,
    val productName: String,
    val productImgUrl: String,
    val company: List<SearchCompanyEntity>,
    val productCategory: String,
    val createdAt: Long = System.currentTimeMillis()

)
