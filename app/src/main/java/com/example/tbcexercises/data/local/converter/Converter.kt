package com.example.tbcexercises.data.local.converter

import androidx.room.TypeConverter
import com.example.tbcexercises.data.local.entity.CompanyEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class Converter  {

    val json: Json = Json{ignoreUnknownKeys = true}
    @TypeConverter
    fun fromCompanyList(value: List<CompanyEntity>): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toCompanyList(value: String): List<CompanyEntity> {
        return json.decodeFromString(value)
    }
}