package com.example.padresdinamicos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.padresdinamicos.databinding.ActivityGuardadoBinding

class GuardadoActivity : BaseActivity() {
    private lateinit var binding: ActivityGuardadoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityGuardadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recetasfavoritas.setOnClickListener {
            val intentFavor = Intent(this, FavoriteRecipeActivity::class.java)
            startActivity(intentFavor)
        }
        binding.Misrecetas.setOnClickListener {
            val intentMisRecetas = Intent(this, MisRecetasActivity::class.java)
            startActivity(intentMisRecetas)
        }
        binding.menuIcon.setOnClickListener {
                val intentMenuActivity = Intent(this, MenuActivity::class.java)
                startActivity(intentMenuActivity)
            }
            binding.buttonCreateRecipe.setOnClickListener {
                val intentCreateRecipe = Intent(this, CreateRecipeActivity::class.java)
                startActivity(intentCreateRecipe)
            }

            binding.categoryIcon.setOnClickListener {
                val intentCategory = Intent(this, CategoriesActivity::class.java)
                startActivity(intentCategory)
            }

            binding.userIcon.setOnClickListener {
                val intentUser = Intent(this, UserScreenActivity::class.java)
                startActivity(intentUser)
            }
        }
    }




