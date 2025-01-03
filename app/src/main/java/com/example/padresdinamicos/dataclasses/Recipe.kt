package com.example.padresdinamicos.dataclasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val image: Int,
    val category: String,
    val subcategory1: String,
    val subcategory2: String,
    val ingredients: ArrayList<String>,
    var isFavorite: Boolean = false,
    val isCreatedByUser: Boolean = false,
    val dificulty : String,
    val time : String,
    val quantity : String,
    val amount : ArrayList<String>
): Serializable

