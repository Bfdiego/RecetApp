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

    @Query("SELECT * FROM Recipe WHERE subcategory1 = :subcategoriaName OR subcategory2 = :subcategoriaName")
    suspend fun obtenerPorSubcategoria(subcategoriaName: String): List<Recipe>


    @Query("SELECT * FROM Recipe")
    suspend fun obtenerTodasLasRecetas(): List<Recipe>

    @Query("SELECT * FROM Recipe WHERE id =:id")
    suspend fun obtenerPorId(id: Int): Recipe

    @Query("SELECT * FROM Recipe WHERE category =:category")
    suspend fun obtenerPorCategoria(category: String): List<Recipe>

    @Query("SELECT * FROM recipe WHERE LOWER(name) LIKE LOWER(:query)")
    suspend fun obtenerPorNombre(query: String): List<Recipe>

    @Query("SELECT * FROM Recipe ORDER BY RANDOM() LIMIT 1")
    suspend fun obtenerRecetaAleatoria(): Recipe

    @Query("SELECT * FROM Recipe WHERE isFavorite = 1")
    suspend fun obtenerRecetasFavoritas(): List<Recipe>

    @Query("SELECT * FROM Recipe WHERE isCreatedByUser = 1")
    suspend fun obtenerRecetasCreadasPorElUsuario(): List<Recipe>

    @Query("UPDATE Recipe SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun actualizarFavorito(id: Int, isFavorite: Boolean)

    @Update
    suspend fun update(recipe: Recipe)




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe)

    @Insert
    suspend fun insertarRecetas(recipe: List<Recipe>)
}