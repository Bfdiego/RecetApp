package com.example.padresdinamicos

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.padresdinamicos.adapters.RecyclerAddIngredientAdapter
import com.example.padresdinamicos.databinding.ActivityCreateRecipeBinding
import com.example.padresdinamicos.dataclasses.Ingredient

class CreateRecipeActivity : BaseActivity() {
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

        binding.buttonExit.setOnClickListener{
            finish()
        }
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
        val spinner = binding.actionBarSpinner

// Crear un ArrayAdapter con las opciones y un layout predeterminado
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, difficultyLevels)

// Especificar el estilo para el desplegable
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

// Asignar el adaptador al spinner
        spinner.adapter = adapter

// Seleccionar la opción predeterminada (opcional)
        spinner.setSelection(0)  // Para seleccionar "Fácil" por defecto

// Detectar la opción seleccionada
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedDifficulty = difficultyLevels[position]
                // Realiza alguna acción con la opción seleccionada
                when (selectedDifficulty) {
                    "Fácil" -> {
                        // Acción para dificultad fácil
                        // por ejemplo: showToast("Dificultad fácil seleccionada")
                    }
                    "Medio" -> {
                        // Acción para dificultad media
                        // por ejemplo: showToast("Dificultad media seleccionada")
                    }
                    "Difícil" -> {
                        // Acción para dificultad difícil
                        // por ejemplo: showToast("Dificultad difícil seleccionada")
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Acción opcional cuando no se selecciona nada
            }
        }

    }
}
