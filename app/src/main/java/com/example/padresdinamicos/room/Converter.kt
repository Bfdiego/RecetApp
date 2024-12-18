package com.example.padresdinamicos.room

import androidx.room.TypeConverter

class Converter {
    @TypeConverter
    fun fromStringList(ingredients: ArrayList<String>): String {
        return ingredients.joinToString(",") // Convierte la lista en un string separado por comas
    }

    @TypeConverter
    fun toStringList(data: String): ArrayList<String> {
        return ArrayList(data.split(",")) // Convierte el string separado por comas en una lista
    }
}