package com.example.tbcexercises.data.local.entity.home

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_home_entity")
data class HomeProductEntity(
    @PrimaryKey val productId: Int,
    val productName: String,
    val productImgUrl: String,
    val company: List<CompanyEntity>,
    val productCategory: String,
    val productPrice: Double,
    val createdAt: Long = System.currentTimeMillis()
)
