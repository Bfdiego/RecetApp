package com.example.padresdinamicos

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.padresdinamicos.adapters.RecyclerRecipesSearchedAdapter
import com.example.padresdinamicos.databinding.ActivitySearchRecipeBinding
import com.example.padresdinamicos.room.RecipeDatabase
import kotlinx.coroutines.launch

class FavoriteRecipeActivity : BaseActivity() {
    private lateinit var binding: ActivitySearchRecipeBinding
    private lateinit var recyclerRecipe: RecyclerRecipesSearchedAdapter
    private lateinit var dbAccess: RecipeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySearchRecipeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dbAccess = RecipeDatabase.getDatabase(this)

        val recyclerView = binding.recyclerViewRecipesSearched
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerRecipe = RecyclerRecipesSearchedAdapter()
        recyclerView.adapter = recyclerRecipe

        loadFavoriteRecipes()

        binding.buttonExit.setOnClickListener {
            finish()
        }
    }

    private fun loadFavoriteRecipes() {
        lifecycleScope.launch {
            val favoriteRecipes = dbAccess.recipeDao().obtenerRecetasFavoritas()
            recyclerRecipe.addDataToList(favoriteRecipes)
            recyclerRecipe.notifyDataSetChanged()
        }
    }
}
