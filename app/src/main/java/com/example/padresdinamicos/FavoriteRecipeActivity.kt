package com.example.padresdinamicos

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.padresdinamicos.adapters.RecyclerFavoAdapter
import com.example.padresdinamicos.adapters.RecyclerRecipesSearchedAdapter
import com.example.padresdinamicos.databinding.ActivityFavoriteRecipeBinding
import com.example.padresdinamicos.databinding.ActivityRecipeBinding
import com.example.padresdinamicos.databinding.ActivitySearchRecipeBinding
import com.example.padresdinamicos.room.RecipeDatabase
import kotlinx.coroutines.launch

class FavoriteRecipeActivity : BaseActivity() {
    private lateinit var binding: ActivityFavoriteRecipeBinding
    private lateinit var recyclerRecipe: RecyclerFavoAdapter
    private lateinit var dbAccess: RecipeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoriteRecipeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dbAccess = RecipeDatabase.getDatabase(this)

        val recyclerView = binding.recyclerViewFav
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerRecipe = RecyclerFavoAdapter()
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
