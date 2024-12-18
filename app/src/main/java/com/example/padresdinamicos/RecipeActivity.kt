package com.example.padresdinamicos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
import kotlin.random.Random

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
        binding.recipeName.text = nameRecipe
        binding.textCategory.text = recipe?.category
        binding.textSubcategory1.text = recipe?.subcategory1
        binding.textSubcategory2.text = recipe?.subcategory2
        binding.imageRecipe.setImageResource(recipe?.image ?: R.drawable.lasagna)

        binding.recyclerViewIngredients.visibility = View.VISIBLE

        val listaIngredientes: ArrayList<String> = recipe?.ingredients ?: arrayListOf()
        val listaIngredientes2: MutableList<Ingredient> = mutableListOf()
        for (i in 0 .. listaIngredientes.size-1) {
            val random = Random
            listaIngredientes2.add(Ingredient(name = listaIngredientes[i], amount = "${random.nextInt(1, 10)}"))
        }

        recyclerIngredientAdapter.addDataToList(listaIngredientes2)

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