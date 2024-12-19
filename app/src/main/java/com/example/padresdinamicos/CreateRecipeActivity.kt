package com.example.padresdinamicos

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.padresdinamicos.adapters.RecyclerAddIngredientAdapter
import com.example.padresdinamicos.adapters.RecyclerAddStepAdapter
import com.example.padresdinamicos.databinding.ActivityCreateRecipeBinding
import com.example.padresdinamicos.dataclasses.Ingredient
import com.example.padresdinamicos.dataclasses.Recipe
import com.example.padresdinamicos.dataclasses.Step
import com.example.padresdinamicos.room.RecipeDatabase
import kotlinx.coroutines.launch

class CreateRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateRecipeBinding
    private lateinit var recyclerAdapter: RecyclerAddIngredientAdapter
    private lateinit var recyclerAdapter2: RecyclerAddStepAdapter
    private lateinit var dbAccess: RecipeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCreateRecipeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        recyclerAdapter = RecyclerAddIngredientAdapter()
        recyclerAdapter2 = RecyclerAddStepAdapter()

        dbAccess = RecipeDatabase.getDatabase(this)

        // Configuración para el RecyclerView de ingredientes
        binding.recyclerViewIngredients.adapter = recyclerAdapter
        binding.recyclerViewIngredients.layoutManager = LinearLayoutManager(this)

        // Configuración para el RecyclerView de pasos
        binding.recyclerViewPasos.adapter = recyclerAdapter2
        binding.recyclerViewPasos.layoutManager = LinearLayoutManager(this)

        binding.buttonAddIngredient.setOnClickListener {
            recyclerAdapter.addEmptyItem()
        }

        binding.buttonAddPaso.setOnClickListener {
            recyclerAdapter2.addEmptyItem()
        }

        binding.buttonExit.setOnClickListener {
            finish()
        }

        binding.buttonSaveRecipe.setOnClickListener {
            // Recoger los datos de la receta
            val subcategory1 = binding.editTextSubcategory1.text.toString()
            val subcategory2 = binding.editTextSubcategory2.text.toString()
            val recipeName = binding.editTextName.text.toString()
            val difficulty = binding.actionBarSpinner.selectedItem.toString()
            val time = binding.editTextTime.text.toString()  // Si tienes un EditText para el tiempo

            // Recoger los ingredientes
            val ingredientsList = recyclerAdapter.getAllIngredients()
            val ingredientNames = ingredientsList.map { it.name }
            val ingredientAmounts = ingredientsList.map { it.amount }

            val listaIngredientes = ArrayList<String>()
            for (i in 0 until ingredientsList.size) {
                listaIngredientes.add(ingredientNames[i])
            }
            val listaIngredientes2 = ArrayList<String>()
            for (i in 0 until ingredientsList.size) {
                listaIngredientes2.add(ingredientAmounts[i])
            }
            // Recoger los pasos
            val stepsList = recyclerAdapter2.getAllSteps()
            val steps = stepsList.mapIndexed { index, step ->
                Step(index + 1, (index + 1).toString(), step.name, step.description, "$recipeName")
            }

            // Crear la receta
            val recipe = Recipe(
                name = recipeName,
                image = R.drawable.recipenpc,
                category = "Creado",
                subcategory1 = subcategory1,
                subcategory2 = subcategory2,
                ingredients = listaIngredientes,
                isFavorite = false
            )

           lifecycleScope.launch {
               dbAccess.recipeDao().insert(recipe)
               dbAccess.stepDao().insertarRecetas(steps)
           }
        }


        val difficultyLevels = arrayOf("Fácil", "Medio", "Difícil")

        val spinner = binding.actionBarSpinner

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, difficultyLevels)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.setSelection(0)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedDifficulty = difficultyLevels[position]
                when (selectedDifficulty) {
                    "Fácil" -> {
                    }
                    "Medio" -> {
                    }
                    "Difícil" -> {

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

    }
}
