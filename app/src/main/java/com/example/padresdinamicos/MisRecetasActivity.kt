package com.example.padresdinamicos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.padresdinamicos.adapters.RecyclerRecipeAdapter
import com.example.padresdinamicos.databinding.ActivityMisRecetasBinding
import com.example.padresdinamicos.room.RecipeDatabase
import kotlinx.coroutines.launch

class MisRecetasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMisRecetasBinding
    private lateinit var recyclerAdapter: RecyclerRecipeAdapter
    private lateinit var dbAccess: RecipeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMisRecetasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerAdapter = RecyclerRecipeAdapter() // Adapter para las recetas
        dbAccess = RecipeDatabase.getDatabase(this)

        binding.recyclerViewMisRecetas.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMisRecetas.adapter = recyclerAdapter

        loadRecipes()
        binding.buttonExit.setOnClickListener {
            finish()
        }
    }

    private fun loadRecipes() {
        lifecycleScope.launch {
            val recipes = dbAccess.recipeDao()
                .obtenerTodasLasRecetas()
            recyclerAdapter.addDataToList(recipes)
        }
    }
}

