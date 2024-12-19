package com.example.padresdinamicos.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.padresdinamicos.dataclasses.Step

@Dao
interface StepDao {
    @Query("SELECT * FROM Step WHERE recipe = :recipe")
    suspend fun obtenerPasosPorReceta(recipe: String): List<Step>

    @Query("SELECT * FROM Step")
    suspend fun obtenerTodosLosPasos(): List<Step>

    @Update
    suspend fun update(step: Step)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(step: Step)

    @Insert
    suspend fun insertarRecetas(steps: List<Step>)
}