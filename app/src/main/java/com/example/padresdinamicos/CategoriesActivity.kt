package com.example.padresdinamicos


import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.padresdinamicos.MenuActivity.Companion.ID_PASO_SUBCATEGORIA
import com.example.padresdinamicos.databinding.ActivityCategoriesBinding
import com.example.padresdinamicos.databinding.ActivityMenuBinding
import com.example.padresdinamicos.databinding.ActivityUserScreenBinding

class CategoriesActivity : BaseActivity() {
    private lateinit var binding: ActivityCategoriesBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        val view = binding.root
        setContentView(view)

        binding.menuIcon.setOnClickListener {
            val intentMenuActivity = Intent(this, MenuActivity::class.java)
            startActivity(intentMenuActivity)
        }
        binding.userIcon.setOnClickListener{
            val intentUserActivity = Intent(this, UserScreenActivity::class.java)
            startActivity(intentUserActivity)
        }
        binding.recetIcon.setOnClickListener{
            val intentrecetActivity = Intent(this, GuardadoActivity::class.java)
            startActivity(intentrecetActivity)
        }

        binding.buttonCreateRecipe.setOnClickListener{
            val intentCreateRecipe = Intent(this, CreateRecipeActivity::class.java)
            startActivity(intentCreateRecipe)
        }

        binding.categoryPaises.setOnClickListener{
            val intentPaisesActivity = Intent(this, CategoryActivity::class.java)
            intentPaisesActivity.putExtra(ID_PASO_SUBCATEGORIA, "Paises")
            startActivity(intentPaisesActivity)
        }
        binding.categoryMetodoDePreparacion.setOnClickListener{
            val intentPaisesActivity = Intent(this, CategoryActivity::class.java)
            intentPaisesActivity.putExtra(ID_PASO_SUBCATEGORIA, "Metodo de preparación")
            startActivity(intentPaisesActivity)
        }
        binding.categoryTipoDeComida.setOnClickListener {
            val intentTipoComidaActivity = Intent(this, CategoryActivity::class.java)
            intentTipoComidaActivity.putExtra(ID_PASO_SUBCATEGORIA, "Tipo de comida")
            startActivity(intentTipoComidaActivity)
        }
        binding.categoryEstiloDeAlimentacion.setOnClickListener {
            val intentEstiloAlimentacionActivity = Intent(this, CategoryActivity::class.java)
            intentEstiloAlimentacionActivity.putExtra(ID_PASO_SUBCATEGORIA, "Estilo de alimentación")
            startActivity(intentEstiloAlimentacionActivity)
        }
        binding.categoryEventos.setOnClickListener {
            val intentEventosActivity = Intent(this, CategoryActivity::class.java)
            intentEventosActivity.putExtra(ID_PASO_SUBCATEGORIA, "Eventos")
            startActivity(intentEventosActivity)
        }
    }
}