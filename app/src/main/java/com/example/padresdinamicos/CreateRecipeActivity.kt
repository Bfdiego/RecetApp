package com.example.padresdinamicos

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.padresdinamicos.databinding.ActivityCreateRecipeBinding

class CreateRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCreateRecipeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Opciones de dificultad
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
