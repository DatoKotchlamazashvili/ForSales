package com.example.tbcexercises.data.local.converter

import androidx.room.TypeConverter
import com.example.tbcexercises.data.local.entity.home.CompanyEntity
import com.example.tbcexercises.data.local.entity.search.SearchCompanyEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class Converter  {

    private val json: Json = Json{ignoreUnknownKeys = true}
    @TypeConverter
    fun fromCompanyList(value: List<CompanyEntity>): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toCompanyList(value: String): List<CompanyEntity> {
        return json.decodeFromString(value)
    }

    @TypeConverter
    fun fromSearchCompanyList(value: List<SearchCompanyEntity>): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toSearchCompanyList(value: String): List<SearchCompanyEntity> {
        return json.decodeFromString(value)
    }

}