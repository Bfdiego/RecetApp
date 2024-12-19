package com.example.padresdinamicos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.padresdinamicos.databinding.ActivityGuardadoBinding
//import com.google.firebase.database.*

class GuardadoActivity : AppCompatActivity() {

    //private lateinit var binding:

  /*  private lateinit var database: DatabaseReference
    private var favoriteCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuración de View Binding
        binding = ActivityGuardadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicialización de Firebase Database
        database = FirebaseDatabase.getInstance().getReference("recipes")

        // Contador de recetas favoritas
        loadFavoriteRecipes()

        // Configura el botón de "Mis Recetas" para mostrar el contador
        binding.Misrecetas.setOnClickListener {
            binding.textViewCount.text = "Guardados: $favoriteCount"
        }
    }

    private fun loadFavoriteRecipes() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                favoriteCount = 0 // Resetear contador

                for (recipeSnapshot in snapshot.children) {
                    val recipe = recipeSnapshot.getValue(Recipe::class.java)
                    if (recipe != null && recipe.isFavorite) {
                        favoriteCount++
                    }
                }

                // Actualizar TextView
                binding.textViewCount.text = "Guardados: $favoriteCount"
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejo de errores
                binding.textViewCount.text = "Error al cargar recetas"
            }
        })
    }*/
}
