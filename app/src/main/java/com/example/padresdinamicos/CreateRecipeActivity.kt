package com.example.padresdinamicos

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.padresdinamicos.adapters.RecyclerAddIngredientAdapter
import com.example.padresdinamicos.databinding.ActivityCreateRecipeBinding
import com.example.padresdinamicos.dataclasses.Ingredient

class CreateRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateRecipeBinding
    private lateinit var recyclerAdapter: RecyclerAddIngredientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCreateRecipeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        recyclerAdapter = RecyclerAddIngredientAdapter()
        binding.recyclerViewIngredients.adapter = recyclerAdapter
        binding.recyclerViewIngredients.layoutManager = LinearLayoutManager(this)

        // Botón para agregar nuevos EditText
        binding.buttonAddIngredient.setOnClickListener {
            recyclerAdapter.addEmptyItem() // Añadir un nuevo ítem vacío
        }

//        binding.buttonSaveRecipe.setOnClickListener {
//            val ingredientsList = adapter.getAllIngredients()
//            for (ingredient in ingredientsList) {
//                Log.d("Ingredients", "Cantidad: ${ingredient.amount}, Ingrediente: ${ingredient.name}")
//            }
//            // Aquí puedes usar la lista para guardar la receta
//        }
//

        val difficultyLevels = arrayOf("Fácil", "Medio", "Difícil")

        // Configuración del Spinner
        val spinner = binding.actionBarSpinner // Obtener referencia del Spinner desde el binding

        // Crear un ArrayAdapter con las opciones y un layout predeterminado
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, difficultyLevels)

        // Especificar el estilo para el desplegable
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Asignar el adaptador al spinner
        spinner.adapter = adapter

        // Detectar la opción seleccionada
        spinner.setOnItemClickListener() { _, _, position, _ ->
            val selectedDifficulty = difficultyLevels[position]
            // Realiza alguna acción con la opción seleccionada
            when (selectedDifficulty) {
                "Fácil" -> {
                    // Acción para dificultad fácil
                }
                "Medio" -> {
                    // Acción para dificultad media
                }
                "Difícil" -> {
                    // Acción para dificultad difícil
                }
            }
        }
    }
}
