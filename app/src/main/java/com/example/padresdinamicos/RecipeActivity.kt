package com.example.padresdinamicos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.padresdinamicos.MenuActivity.Companion.ID_PASO_RECETA
import com.example.padresdinamicos.adapters.RecyclerIngredientsAdapter
import com.example.padresdinamicos.adapters.RecyclerStepAdapter
import com.example.padresdinamicos.databinding.ActivityRecipeBinding
import com.example.padresdinamicos.dataclasses.Ingredient
import com.example.padresdinamicos.dataclasses.Recipe
import com.example.padresdinamicos.dataclasses.Step

class RecipeActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityRecipeBinding
    private val recyclerIngredientAdapter by lazy { RecyclerIngredientsAdapter() }
    private val recyclerStepAdapter by lazy { RecyclerStepAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        setUpRecyclerView()
        setUpRecyclerView2()

        binding.buttonBack.setOnClickListener {
            finish()
        }
    }


    fun setUpRecyclerView() {
        val recipe: Recipe? = intent.getSerializableExtra(ID_PASO_RECETA) as? Recipe
        val nameRecipe = recipe?.name
        val listaDatos = mutableListOf(
            Ingredient(name = "$nameRecipe", amount = "12"),
            Ingredient(name = "Cebolla", amount = "1"),
            Ingredient(name = "Zanahoria", amount = "1"),
            Ingredient(name = "Papa", amount = "1"),
            Ingredient(name = "Lechuga", amount = "1"),
            Ingredient(name = "Tomate", amount = "12"),
            Ingredient(name = "Cebolla", amount = "1"),
            Ingredient(name = "Zanahoria", amount = "1"),
            Ingredient(name = "Papa", amount = "1"),
        )

        recyclerIngredientAdapter.addDataToList(listaDatos)

        binding.recyclerViewIngredients.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerIngredientAdapter
        }
    }

    fun setUpRecyclerView2() {
        val listaDatos = mutableListOf(
            Step(number = "1", name = "Cortar", description = "Cortar todo"),
            Step(number = "2", name = "Cortar", description = "Cortar todo"),
            Step(number = "3", name = "Cortar", description = "Cortar todo"),
            Step(number = "4", name = "Cortar", description = "Cortar todo"),)

        recyclerStepAdapter.addDataToList(listaDatos)

        binding.recyclerViewProcess.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerStepAdapter
        }
    }
}