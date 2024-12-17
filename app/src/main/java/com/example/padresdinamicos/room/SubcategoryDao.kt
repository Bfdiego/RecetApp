package com.example.padresdinamicos.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.padresdinamicos.dataclasses.Subcategory

@Dao
interface SubcategoryDao {

    @Query("SELECT * FROM Subcategory")
    suspend fun obtenerTodasLasSubcategorias(): List<Subcategory>

    @Query("SELECT * FROM Recipe WHERE id =:id")
    suspend fun obtenerPorId(id: String): Subcategory

    @Update
    suspend fun update(subcategory: Subcategory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subcategory: Subcategory)

    @Insert
    suspend fun insertarSubcategorias(subcategory: List<Subcategory>)
}