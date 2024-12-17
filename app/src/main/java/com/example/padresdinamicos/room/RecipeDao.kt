package com.example.padresdinamicos.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.padresdinamicos.dataclasses.Recipe
import com.example.padresdinamicos.dataclasses.Subcategory

@Dao
interface RecipeDao {

    @Query("SELECT * FROM Recipe WHERE subcategories LIKE '%' || :subcategoriaName || '%'")
    suspend fun obtenerPorSubcategoria(subcategoriaName: String): List<Recipe>

    @Query("SELECT * FROM Recipe")
    suspend fun obtenerTodasLasRecetas(): List<Recipe>

    @Query("SELECT * FROM Recipe WHERE id =:id")
    suspend fun obtenerPorId(id: String): Recipe

    @Update
    suspend fun update(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe)

    @Insert
    suspend fun insertarRecetas(recipe: List<Recipe>)
}