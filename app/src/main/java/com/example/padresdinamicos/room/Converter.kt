package com.example.padresdinamicos.room

import androidx.room.TypeConverter
import com.example.padresdinamicos.dataclasses.Recipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    private val gson = Gson()

    @TypeConverter
    fun fromRecipesList(recipes: List<Recipe>): String {
        return gson.toJson(recipes)
    }

    @TypeConverter
    fun toRecipesList(data: String): List<Recipe> {
        val listType = object : TypeToken<List<Recipe>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toStringList(data: String): List<String> {
        return data.split(",")
    }
}