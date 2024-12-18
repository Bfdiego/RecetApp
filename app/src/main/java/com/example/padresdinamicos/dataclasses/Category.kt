package com.example.padresdinamicos.dataclasses

data class Category(
    val name: String,
    val subcategories: List<Subcategory>
)
