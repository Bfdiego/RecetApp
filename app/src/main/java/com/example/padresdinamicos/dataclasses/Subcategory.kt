package com.example.padresdinamicos.dataclasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Subcategory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val recipes: List<Recipe>,
    val category: String
)
