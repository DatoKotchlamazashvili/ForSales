package com.example.tbcexercises.utils.extension

import android.util.Log
import com.example.tbcexercises.domain.model.SearchCompany

fun List<SearchCompany>.getLowestPrice(): Double {
    return this.mapNotNull { it.productPrice.toDoubleOrNull() }.minOrNull() ?: 0.0
}


fun List<SearchCompany>.companiesWithLowestPrice(): String {
    val lowestPrice = this.getLowestPrice()
    Log.d("tags", this.filter { it.productPrice.toDoubleOrNull() == lowestPrice }
        .map { it.name }.toString())
    return this.filter { it.productPrice.toDoubleOrNull() == lowestPrice }
        .map { it.name }.joinToString(",") { it }
}