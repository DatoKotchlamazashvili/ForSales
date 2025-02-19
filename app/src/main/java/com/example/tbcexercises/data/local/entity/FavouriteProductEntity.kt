package com.example.tbcexercises.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favourite_product_entity")
data class FavouriteProductEntity(
    @PrimaryKey val productId: Int,
    val productName: String,
    val productImgUrl: String,
    val company: List<CompanyEntity>,
    val productCategory: String,
    val productPrice: Double
)