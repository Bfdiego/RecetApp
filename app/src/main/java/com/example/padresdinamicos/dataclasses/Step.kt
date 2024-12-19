package com.example.padresdinamicos.dataclasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Step(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val number: String,
    var name: String,
    var description: String,
    val recipe: String
)
