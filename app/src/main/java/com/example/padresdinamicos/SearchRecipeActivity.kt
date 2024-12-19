package com.example.padresdinamicos

import java.text.Normalizer
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.padresdinamicos.adapters.RecyclerRecipesSearchedAdapter
import com.example.padresdinamicos.databinding.ActivitySearchRecipeBinding
import com.example.padresdinamicos.room.RecipeDatabase
import kotlinx.coroutines.launch

class SearchRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchRecipeBinding
    private lateinit var recyclerRecipe: RecyclerRecipesSearchedAdapter
    private lateinit var dbAccess : RecipeDatabase


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

        val editTextSearch: EditText = binding.textInputSearch

        binding.buttonExit.setOnClickListener {
            finish()
        }

        binding.imageButtonSearch.setOnClickListener {
            val query = editTextSearch.text.toString()
            val normalizedQuery = "%" + removeAccents(query.toLowerCase()) + "%"

            lifecycleScope.launch {
                val listaRecetas = dbAccess.recipeDao().obtenerPorNombre(normalizedQuery)
                recyclerRecipe.addDataToList(listaRecetas)
                recyclerRecipe.notifyDataSetChanged()
            }
        }
    }

    fun removeAccents(input: String): String {
        val normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
        return normalized.replace("[^\\p{ASCII}]".toRegex(), "")
    }
}