package com.example.padresdinamicos

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.padresdinamicos.adapters.RecyclerSubcategoryAdapter
import com.example.padresdinamicos.databinding.ActivityCategoryBinding
import com.example.padresdinamicos.dataclasses.Subcategory
import com.example.padresdinamicos.room.RecipeDatabase
import com.example.padresdinamicos.room.RecipeDatabase.Companion.getDatabase
import kotlinx.coroutines.launch

class CategoryActivity : BaseActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private val recyclerSubcategoryAdapter by lazy { RecyclerSubcategoryAdapter() }
    private lateinit var dbAccess : RecipeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        dbAccess = getDatabase(this)

    }

    fun setUp(){
        lifecycleScope.launch {
            val subcategories = listOf(
                Subcategory(name = "Italia", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("Italia"), category = "Paises"),
                Subcategory(name = "México", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("México"), category = "Paises"),
                Subcategory(name = "Japón", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("Japón"), category = "Paises"),
                Subcategory(name = "Bolivia", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("Bolivia"), category = "Paises"),
                Subcategory(name = "Francia", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("Francia"), category = "Paises"),
                Subcategory(name = "Desayunos", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("Desayunos"), category = "Tipo de comida"),
                Subcategory(name = "Entradas", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("Entradas"), category = "Tipo de comida"),
                Subcategory(name = "Platos Principales", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("Platos Principales"), category = "Tipo de comida"),
                Subcategory(name = "Postres", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("Postres"), category = "Tipo de comida"),
                Subcategory(name = "Bebidas", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("Bebidas"), category = "Tipo de comida"),
                Subcategory(name = "Vegetariana", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("Vegetariana"), category = "Estilo de alimentación"),
                Subcategory(name = "Vegana", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("Vegana"), category = "Estilo de alimentación"),
                Subcategory(name = "Sin Gluten", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("Sin Gluten"), category = "Estilo de alimentación"),
                Subcategory(name = "Low Carb", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("Low Carb"), category = "Estilo de alimentación"),
                Subcategory(name = "Alta en Proteína", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("Alta en Proteína"), category = "Estilo de alimentación"),
                Subcategory(name = "Al Horno", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("Al Horno"), category = "Metodo de preparación"),
                Subcategory(name = "A la Parrilla", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("A la Parrilla"), category = "Metodo de preparación"),
                Subcategory(name = "Frituras", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("Frituras"), category = "Metodo de preparación"),
                Subcategory(name = "En Crudo", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("En Crudo"), category = "Metodo de preparación"),
                Subcategory(name = "Sopa", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("Sopa"), category = "Metodo de preparación"),
                Subcategory(name = "Cumpleaños", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("Cumpleaños"), category = "Eventos"),
                Subcategory(name = "Navidad", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("Navidad"), category = "Eventos"),
                Subcategory(name = "San Valentín", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("San Valentín"), category = "Eventos"),
                Subcategory(name = "Picnic", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("Picnic"), category = "Eventos"),
                Subcategory(name = "Año nuevo", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("Año nuevo"), category = "Eventos")
            )

            recyclerSubcategoryAdapter.addDataToList(subcategories)
            binding.recyclerViewCategory.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = recyclerSubcategoryAdapter
            }
        }
    }
}