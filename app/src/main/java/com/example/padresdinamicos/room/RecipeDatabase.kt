package com.example.padresdinamicos.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.padresdinamicos.dataclasses.Recipe
import com.example.padresdinamicos.dataclasses.Subcategory

@Database(
    entities = [Recipe::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class RecipeDatabase: RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    companion object{
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase{
            return INSTANCE ?: synchronized(this){
                val room = Room
                    .databaseBuilder(
                        context,
                        RecipeDatabase::class.java,
                        "Recipes"
                    ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = room
                room
            }
        }

    }
}