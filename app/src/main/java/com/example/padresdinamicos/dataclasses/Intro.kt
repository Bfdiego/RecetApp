package com.example.padresdinamicos.dataclasses

data class Intro(
val fondo: Int,
val image: Int? = null,
val titulo: String,
val descripcion: String,
val botonText: String? = null,
)
