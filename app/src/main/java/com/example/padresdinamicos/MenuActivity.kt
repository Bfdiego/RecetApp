package com.example.padresdinamicos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.padresdinamicos.adapters.RecyclerRecipeAdapter
import com.example.padresdinamicos.adapters.RecyclerSubcategoryAdapter
import com.example.padresdinamicos.databinding.ActivityMenuBinding
import com.example.padresdinamicos.dataclasses.Recipe
import com.example.padresdinamicos.dataclasses.Subcategory
import com.example.padresdinamicos.room.RecipeDatabase
import com.example.padresdinamicos.room.RecipeDatabase.Companion.getDatabase
import com.google.gson.Gson
import kotlinx.coroutines.launch

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private val recyclerSubcategoryAdapter by lazy { RecyclerSubcategoryAdapter() }
    private lateinit var dbAccess : RecipeDatabase

    companion object {
        val ID_PASO_RECETA = "ID_PASO_RECETA"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)


        dbAccess = getDatabase(this)

        lifecycleScope.launch {
            if (dbAccess.recipeDao().obtenerTodasLasRecetas().isEmpty()){
                val recipes = listOf(
                    Recipe(name = "Pasta Carbonara", image = R.drawable.pastacarbonara, category =  "Paises", subcategory1 = "Italia", subcategory2 = "Platos Principales", ingredients = arrayListOf("Spaguetti", "Tocino", "Parmesano", "Huevo")),
                    Recipe(name = "Pizza Margarita", image = R.drawable.pizzamargarita, category =  "Paises", subcategory1 = "Italia", subcategory2 = "Platos Principales", ingredients = arrayListOf("Masa", "Tomate", "Mozzarella", "Albahaca")),
                    Recipe(name = "Lasagna", image = R.drawable.lasagna, category =  "Paises", subcategory1 = "Italia", subcategory2 = "Platos Principales", ingredients = arrayListOf("Pasta", "Carne", "Queso", "Tomate")),
                    Recipe(name = "Risotto al pesto", image = R.drawable.risottoalpesto, category =  "Paises", subcategory1 = "Italia", subcategory2 = "Platos Principales", ingredients = arrayListOf("Arroz", "Pescado", "Pesto", "Ajo")),
                    Recipe(name = "Tiramisu", image = R.drawable.tiramisu, category =  "Paises", subcategory1 = "Italia", subcategory2 = "Postres", ingredients = arrayListOf("Masa", "Chocolate", "Leche", "Canela")),
                    Recipe(name = "Tacos al Pastor", image = R.drawable.recetapplogo, category =  "Paises", subcategory1 = "México", subcategory2 = "Entradas", ingredients = arrayListOf("Tortilla", "Carne al Pastor", "Cilantro", "Cebolla", "Piña")),
                    Recipe(name = "Enchiladas Verdes", image = R.drawable.recetapplogo, category =  "Paises", subcategory1 = "México", subcategory2 = "Alta en Proteína", ingredients = arrayListOf("Tortillas", "Salsa verde", "Pollo", "Cebolla","Crema")),
                    Recipe(name = "Guacamole", image = R.drawable.recetapplogo, category =  "Paises", subcategory1 = "México", subcategory2 = "Vegana", ingredients = arrayListOf("Aguacate", "Tomate", "Cebolla", "Cilantro", "Lima")),
                    Recipe(name = "Chiles Rellenos", image = R.drawable.recetapplogo, category =  "Paises", subcategory1 = "México", subcategory2 = "Frituras", ingredients = arrayListOf("Chile poblano", "Carne molida", "Queso", "Salsa")),
                    Recipe(name = "Pozole", image = R.drawable.recetapplogo, category = "Paises", subcategory1 = "México", subcategory2 = "Pozole", ingredients = arrayListOf("Maíz hominy", "Carne de cerdo o pollo", "Ajo", "Cebolla", "Orégano", "Chile en polvo", "Limón", "Rábanos", "Lechuga")),
                    Recipe(name = "Sushi (Maki Rolls)", image = R.drawable.recetapplogo, category = "Japón", subcategory1 = "Japón", subcategory2 = "Sushi", ingredients = arrayListOf("Arroz para sushi", "Alga nori", "Pescado crudo (salmón o atún)", "Pepino", "Aguacate", "Vinagre de arroz", "Azúcar", "Sal")),
                    Recipe(name = "Ramen", image = R.drawable.recetapplogo, category = "Japón", subcategory1 = "Japón", subcategory2 = "Ramen", ingredients = arrayListOf("Fideos de ramen", "Caldo de pollo o cerdo", "Salsa de soja", "Huevo cocido", "Cebollines", "Alga nori", "Chashu (carne de cerdo)")),
                    Recipe(name = "Tempura", image = R.drawable.recetapplogo, category = "Japón", subcategory1 = "Japón", subcategory2 = "Tempura", ingredients = arrayListOf("Mariscos (camarones, calamares)", "Verduras (zanahoria, calabacín, berenjena)", "Harina de trigo", "Huevo", "Agua fría", "Aceite para freír")),
                    Recipe(name = "Okonomiyaki", image = R.drawable.recetapplogo, category = "Japón", subcategory1 = "Japón", subcategory2 = "Okonomiyaki", ingredients = arrayListOf("Harina de trigo", "Huevo", "Col rallada", "Cebollín", "Tocino o cerdo", "Salsa okonomiyaki", "Mayonesa japonesa", "Katsuobushi (copos de bonito seco)")),
                    Recipe(name = "Dorayaki", image = R.drawable.recetapplogo, category = "Japón", subcategory1 = "Japón", subcategory2 = "Dorayaki", ingredients = arrayListOf("Harina de trigo", "Huevo", "Azúcar", "Miel", "Pasta de frijol rojo (anko)", "Bicarbonato de sodio")),
                    Recipe(name = "fSilpancho", image = R.drawable.recetapplogo, category = "Bolivia", subcategory1 = "Bolivia", subcategory2 = "Silpancho", ingredients = arrayListOf("Carne de res", "Arroz", "Papa", "Huevo", "Cebolla", "Tomate")),
                    Recipe(name = "Plato Paceño", image = R.drawable.recetapplogo, category = "Bolivia", subcategory1 = "Bolivia", subcategory2 = "Plato Paceño", ingredients = arrayListOf("Arroz", "Papa", "Carne de res", "Chicharrón", "Salsa de maní")),
                    Recipe(name = "Majadito", image = R.drawable.recetapplogo, category = "Bolivia", subcategory1 = "Bolivia", subcategory2 = "Majadito", ingredients = arrayListOf("Arroz", "Carne de res", "Plátano", "Huevo", "Verduras")),
                    Recipe(name = "Sopa de Maní", image = R.drawable.recetapplogo, category = "Bolivia", subcategory1 = "Bolivia", subcategory2 = "Sopa de Maní", ingredients = arrayListOf("Maní", "Carne de res", "Papa", "Fideos", "Verduras")),
                    Recipe(name = "Pique a lo Macho", image = R.drawable.recetapplogo, category = "Bolivia", subcategory1 = "Bolivia", subcategory2 = "Pique a lo Macho", ingredients = arrayListOf("Carne de res", "Papa", "Cebolla", "Tomate", "Ají rojo")),
                    Recipe(name = "Quiche Lorraine", image = R.drawable.recetapplogo, category = "Francia", subcategory1 = "Francia", subcategory2 = "Quiche Lorraine", ingredients = arrayListOf("Masa quebrada", "Huevo", "Crema de leche", "Bacon", "Queso Gruyère", "Cebolla", "Pimienta")),
                    Recipe(name = "Ratatouille", image = R.drawable.recetapplogo, category = "Francia", subcategory1 = "Francia", subcategory2 = "Ratatouille", ingredients = arrayListOf("Berenjena", "Calabacín", "Pimiento rojo", "Tomate", "Cebolla", "Ajo", "Aceite de oliva", "Hierbas provenzales")),
                    Recipe(name = "Soupe à l'Oignon", image = R.drawable.recetapplogo, category = "Francia", subcategory1 = "Francia", subcategory2 = "Soupe à l'Oignon", ingredients = arrayListOf("Cebolla", "Caldo de carne", "Vino blanco", "Pan", "Queso Gruyère", "Mantequilla", "Harina")),
                    Recipe(name = "Croissant", image = R.drawable.recetapplogo, category = "Francia", subcategory1 = "Francia",subcategory2 =  "Desayuno", ingredients = arrayListOf("Harina", "Mantequilla", "Levadura", "Leche", "Azúcar", "Sal", "Huevos")),
                    Recipe(name = "Crème Brûlée", image = R.drawable.recetapplogo, category = "Francia", subcategory1 = "Francia", subcategory2 = "Crème Brûlée", ingredients = arrayListOf("Yemas de huevo", "Crema de leche", "Azúcar", "Vainilla", "Azúcar moreno (para caramelizar)")),
                    Recipe(name = "Pancakes", image = R.drawable.recetapplogo, category = "Tipo de comida", subcategory1 = "Desayunos", subcategory2 = "Pancakes", ingredients = arrayListOf("Harina", "Leche", "Huevo", "Azúcar", "Mantequilla", "Polvo de hornear", "Miel", "Frutas (opcional)")),
                    Recipe(name = "Smoothie Bowl", image = R.drawable.recetapplogo, category = "Tipo de comida", subcategory1 = "Desayunos", subcategory2 = "Smoothie Bowl", ingredients = arrayListOf("Frutas congeladas", "Leche o yogur", "Granola", "Semillas", "Frutas frescas", "Miel")),
                    Recipe(name = "Huevos Benedictinos", image = R.drawable.recetapplogo, category = "Tipo de comida", subcategory1 = "Desayunos", subcategory2 = "Huevos Benedictinos", ingredients = arrayListOf("Huevos", "Pan tostado", "Jamón", "Salsa holandesa", "Vinagre", "Sal", "Pimienta")),
                    Recipe(name = "Avena con Frutas", image = R.drawable.recetapplogo, category = "Tipo de comida", subcategory1 = "Desayunos", subcategory2 = "Avena con Frutas", ingredients = arrayListOf("Avena", "Leche", "Miel", "Frutas (plátano, fresas, arándanos)", "Frutos secos")),
                    Recipe(name = "Tostadas de Aguacate", image = R.drawable.recetapplogo, category = "Tipo de comida", subcategory1 = "Desayunos", subcategory2 = "Tostadas de Aguacate", ingredients = arrayListOf("Pan integral", "Aguacate", "Huevo", "Sal", "Pimienta", "Aceite de oliva")),
                    Recipe(name = "Bruschettas", image = R.drawable.recetapplogo, category = "Tipo de comida", subcategory1 = "Entradas", subcategory2 = "Bruschettas", ingredients = arrayListOf("Pan baguette", "Tomate", "Albahaca", "Ajo", "Aceite de oliva", "Vinagre balsámico")),
                    Recipe(name = "Hummus con Pita", image = R.drawable.recetapplogo, category = "Tipo de comida", subcategory1 = "Entradas", subcategory2 = "Hummus con Pita", ingredients = arrayListOf("Garbanzos", "Tahini", "Ajo", "Limón", "Aceite de oliva", "Pita")),
                    Recipe(name = "Sopa de Tomate", image = R.drawable.recetapplogo, category = "Tipo de comida", subcategory1 = "Entradas", subcategory2 = "Sopa de Tomate", ingredients = arrayListOf("Tomates", "Cebolla", "Ajo", "Caldo de pollo", "Aceite de oliva", "Sal", "Pimienta")),
                    Recipe(name = "Calamares Fritos", image = R.drawable.recetapplogo, category = "Tipo de comida", subcategory1 = "Entradas", subcategory2 = "Calamares Fritos", ingredients = arrayListOf("Calamares", "Harina", "Sal", "Pimienta", "Aceite para freír", "Limón")),
                    Recipe(name = "Tabule", image = R.drawable.recetapplogo, category = "Tipo de comida", subcategory1 = "Entradas", subcategory2 = "Tabule", ingredients = arrayListOf("Trigo bulgur", "Perejil", "Tomate", "Pepino", "Cebolla", "Menta", "Jugo de limón", "Aceite de oliva")),
                    Recipe(name = "Pollo al Curry", image = R.drawable.recetapplogo, category = "Tipo de comida", subcategory1 = "Plato Principal", subcategory2 = "Pollo al Curry", ingredients = arrayListOf("Pollo", "Curry en polvo", "Cebolla", "Ajo", "Leche de coco", "Tomate", "Aceite de oliva", "Arroz")),
                    Recipe(name = "Espagueti Boloñesa", image = R.drawable.recetapplogo, category = "Tipo de comida", subcategory1 = "Plato Principal", subcategory2 = "Espagueti Boloñesa", ingredients = arrayListOf("Espagueti", "Carne molida de res", "Tomate", "Cebolla", "Ajo", "Aceite de oliva", "Pasta de tomate", "Vino tinto")),
                    Recipe(name = "Estofado de Res", image = R.drawable.recetapplogo, category = "Tipo de comida", subcategory1 = "Plato Principal", subcategory2 = "Estofado de Res", ingredients = arrayListOf("Carne de res", "Zanahoria", "Papa", "Cebolla", "Ajo", "Caldo de res", "Pimiento", "Laurel", "Aceite de oliva")),
                    Recipe(name = "Paella", image = R.drawable.recetapplogo, category = "Plato Principal", subcategory1 = "Plato Principal", subcategory2 = "Paella", ingredients = arrayListOf("Arroz", "Mariscos", "Pollo", "Pimiento", "Guisante", "Tomate", "Caldo de pollo", "Azafrán", "Aceite de oliva")),
                    Recipe(name = "Salmón a la Plancha", image = R.drawable.recetapplogo, category = "Plato Principal", subcategory1 = "Plato Principal", subcategory2 = "Salmón a la Plancha", ingredients = arrayListOf("Salmón", "Aceite de oliva", "Limón", "Ajo", "Pimienta", "Sal", "Perejil")),
                    Recipe(name = "Cheesecake", image = R.drawable.recetapplogo, category = "Postres", subcategory1 = "Postres", subcategory2 = "Cheesecake", ingredients = arrayListOf("Galletas Digestive", "Queso crema", "Azúcar", "Huevos", "Crema de leche", "Esencia de vainilla", "Mantequilla")),
                    Recipe(name = "Brownies", image = R.drawable.recetapplogo, category = "Postres", subcategory1 = "Postres", subcategory2 = "Brownies", ingredients = arrayListOf("Harina", "Chocolate", "Azúcar", "Mantequilla", "Huevos", "Nueces", "Esencia de vainilla")),
                    Recipe(name = "Arroz con Leche", image = R.drawable.recetapplogo, category = "Postres", subcategory1 = "Postres", subcategory2 = "Arroz con Leche", ingredients = arrayListOf("Arroz", "Leche", "Azúcar", "Canela", "Cáscara de limón", "Esencia de vainilla")),
                    Recipe(name = "Flan", image = R.drawable.recetapplogo, category = "Postres", subcategory1 = "Postres", subcategory2 = "Flan", ingredients = arrayListOf("Leche", "Azúcar", "Huevos", "Esencia de vainilla", "Caramelo")),
                    Recipe(name = "Churros", image = R.drawable.recetapplogo, category = "Postres", subcategory1 = "Postres", subcategory2 = "Churros", ingredients = arrayListOf("Harina", "Agua", "Azúcar", "Sal", "Aceite", "Canela", "Chocolate para mojar")),
                    Recipe(name = "Mojito", image = R.drawable.recetapplogo, category = "Bebidas", subcategory1 = "Bebidas", subcategory2 = "Mojito", ingredients = arrayListOf("Ron blanco", "Menta", "Azúcar", "Limón", "Agua con gas", "Hielo")),
                    Recipe(name = "Margarita", image = R.drawable.recetapplogo, category = "Bebidas", subcategory1 = "Bebidas", subcategory2 = "Margarita", ingredients = arrayListOf("Tequila", "Triple sec", "Jugo de lima", "Sal", "Hielo")),
                    Recipe(name = "Té Matcha", image = R.drawable.recetapplogo, category = "Bebidas", subcategory1 = "Bebidas", subcategory2 = "Té Matcha", ingredients = arrayListOf("Té matcha", "Leche", "Azúcar", "Hielo")),
                    Recipe(name = "Batido de Fresa", image = R.drawable.recetapplogo, category = "Bebidas", subcategory1 = "Bebidas", subcategory2 = "Batido de Fresa", ingredients = arrayListOf("Fresas", "Leche", "Yogur", "Miel", "Hielo")),
                    Recipe(name = "Horchata", image = R.drawable.recetapplogo, category = "Bebidas", subcategory1 = "Bebidas", subcategory2 = "Horchata", ingredients = arrayListOf("Arroz", "Canela", "Azúcar", "Leche", "Vainilla", "Hielo")),
                    Recipe(name = "Ensalada Caprese", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Vegetariana", subcategory2 = "Ensalada Caprese", ingredients = arrayListOf("Tomate", "Mozzarella", "Albahaca", "Aceite de oliva", "Vinagre balsámico")),
                    Recipe(name = "Curry de Garbanzos", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Vegetariana", subcategory2 = "Curry de Garbanzos", ingredients = arrayListOf("Garbanzos", "Cebolla", "Ajo", "Jengibre", "Curry en polvo", "Leche de coco", "Espinaca")),
                    Recipe(name = "Hamburguesa de Lentejas", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Vegetariana", subcategory2 = "Hamburguesa de Lentejas", ingredients = arrayListOf("Lentejas", "Ajo", "Cebolla", "Pan rallado", "Especias", "Lechuga", "Tomate")),
                    Recipe(name = "Risotto de Champiñones", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Vegetariana", subcategory2 = "Risotto de Champiñones", ingredients = arrayListOf("Arroz arborio", "Champiñones", "Caldo vegetal", "Cebolla", "Vino blanco", "Queso parmesano")),
                    Recipe(name = "Ratatouille", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Vegetariana", subcategory2 = "Ratatouille", ingredients = arrayListOf("Berenjena", "Calabacín", "Tomate", "Pimiento", "Cebolla", "Ajo", "Aceite de oliva")),
                    Recipe(name = "Tacos de Soya", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Vegana", subcategory2 = "Tacos de Soya", ingredients = arrayListOf("Soya texturizada", "Tortillas de maíz", "Cebolla", "Aguacate", "Limón", "Cilantro", "Salsa")),
                    Recipe(name = "Smoothie Verde", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Vegana", subcategory2 = "Smoothie Verde", ingredients = arrayListOf("Espinaca", "Plátano", "Leche de almendra", "Semillas de chía", "Miel")),
                    Recipe(name = "Pasta con Salsa de Anacardos", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Vegana", subcategory2 = "Pasta con Salsa de Anacardos", ingredients = arrayListOf("Pasta", "Anacardos", "Ajo", "Leche de coco", "Limón", "Pimienta")),
                    Recipe(name = "Brownies Veganos", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Vegana", subcategory2 = "Brownies Veganos", ingredients = arrayListOf("Harina de avena", "Cacao", "Plátano", "Leche de almendra", "Polvo de hornear", "Nueces")),
                    Recipe(name = "Helado de Plátano", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Vegana", subcategory2 = "Helado de Plátano", ingredients = arrayListOf("Plátanos", "Leche de almendra", "Extracto de vainilla")),
                    Recipe(name = "Pizza de Coliflor", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Sin Gluten", subcategory2 = "Pizza de Coliflor", ingredients = arrayListOf("Coliflor", "Huevo", "Queso mozzarella", "Tomate", "Albahaca", "Aceite de oliva")),
                    Recipe(name = "Ensalada Griega", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Sin Gluten", subcategory2 = "Ensalada Griega", ingredients = arrayListOf("Pepino", "Tomate", "Aceitunas", "Feta", "Cebolla roja", "Aceite de oliva", "Limón")),
                    Recipe(name = "Pollo a la Parrilla con Vegetales", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Sin Gluten", subcategory2 = "Pollo a la Parrilla con Vegetales", ingredients = arrayListOf("Pechuga de pollo", "Zanahorias", "Calabacín", "Aceite de oliva", "Especias")),
                    Recipe(name = "Sopa de Lentejas", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Sin Gluten", subcategory2 = "Sopa de Lentejas", ingredients = arrayListOf("Lentejas", "Cebolla", "Ajo", "Tomate", "Zanahorias", "Caldo de verduras")),
                    Recipe(name = "Bizcocho de Almendra", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Sin Gluten", subcategory2 = "Bizcocho de Almendra", ingredients = arrayListOf("Harina de almendra", "Huevo", "Miel", "Aceite de coco", "Levadura en polvo")),
                    Recipe(name = "Zoodles con Pesto", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Low Carb", subcategory2 = "Zoodles con Pesto", ingredients = arrayListOf("Calabacín", "Albahaca", "Ajo", "Piñones", "Aceite de oliva", "Parmesano")),
                    Recipe(name = "Pollo a la Mostaza", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Low Carb", subcategory2 = "Pollo a la Mostaza", ingredients = arrayListOf("Pechuga de pollo", "Mostaza", "Miel", "Ajo", "Aceite de oliva")),
                    Recipe(name = "Ensalada César sin Croutons", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Low Carb", subcategory2 = "Ensalada César sin Croutons", ingredients = arrayListOf("Lechuga", "Pollo a la parrilla", "Aderezo César", "Queso parmesano", "Aceite de oliva")),
                    Recipe(name = "Tacos de Lechuga", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Low Carb", subcategory2 = "Tacos de Lechuga", ingredients = arrayListOf("Carne molida", "Lechuga", "Tomate", "Cebolla", "Salsa")),
                    Recipe(name = "Cheesecake Keto", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Low Carb", subcategory2 = "Cheesecake Keto", ingredients = arrayListOf("Queso crema", "Harina de almendra", "Mantequilla", "Edulcorante", "Extracto de vainilla")),
                    Recipe(name = "Pechuga de Pollo al Limón", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Alta en Proteína", subcategory2 = "Pechuga de Pollo al Limón", ingredients = arrayListOf("Pechuga de pollo", "Limón", "Ajo", "Aceite de oliva", "Pimienta")),
                    Recipe(name = "Tortilla de Claras con Espinaca", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Alta en Proteína", subcategory2 = "Tortilla de Claras con Espinaca", ingredients = arrayListOf("Claras de huevo", "Espinaca", "Cebolla", "Tomate", "Aceite de oliva")),
                    Recipe(name = "Salmón con Salsa Teriyaki", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Alta en Proteína", subcategory2 = "Salmón con Salsa Teriyaki", ingredients = arrayListOf("Salmón", "Salsa teriyaki", "Ajo", "Jengibre", "Cebollín")),
                    Recipe(name = "Batido de Proteína", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Alta en Proteína", subcategory2 = "Batido de Proteína", ingredients = arrayListOf("Proteína en polvo", "Leche de almendra", "Plátano", "Espinaca", "Semillas de chía")),
                    Recipe(name = "Albóndigas al Horno", image = R.drawable.recetapplogo, category = "Estilo de Alimentación", subcategory1 = "Alta en Proteína", subcategory2 = "Albóndigas al Horno", ingredients = arrayListOf("Carne de res", "Huevo", "Pan rallado", "Ajo", "Pimienta", "Tomate")),
                    Recipe(name = "Lasagna", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "Al Horno", subcategory2 = "Lasagna", ingredients = arrayListOf("Pasta de lasaña", "Carne molida", "Salsa bechamel", "Queso mozzarella", "Tomate", "Cebolla", "Ajo")),
                    Recipe(name = "Pollo Rostizado", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "Al Horno", subcategory2 = "Pollo Rostizado", ingredients = arrayListOf("Pollo entero", "Ajo", "Limón", "Romero", "Aceite de oliva", "Pimienta")),
                    Recipe(name = "Pan de Plátano", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "Al Horno", subcategory2 = "Pan de Plátano", ingredients = arrayListOf("Plátanos", "Harina", "Huevo", "Azúcar", "Bicarbonato de sodio", "Nueces")),
                    Recipe(name = "Pizza Casera", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "Al Horno", subcategory2 = "Pizza Casera", ingredients = arrayListOf("Masa para pizza", "Tomate", "Mozzarella", "Aceitunas", "Pepperoni", "Albahaca", "Aceite de oliva")),
                    Recipe(name = "Pescado en Papillote", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "Al Horno", subcategory2 = "Pescado en Papillote", ingredients = arrayListOf("Pescado blanco", "Ajo", "Limón", "Tomillo", "Aceite de oliva", "Papel aluminio")),
                    Recipe(name = "Costillas BBQ", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "A la Parrilla", subcategory2 = "Costillas BBQ", ingredients = arrayListOf("Costillas de cerdo", "Salsa BBQ", "Ajo", "Pimienta negra", "Aceite de oliva")),
                    Recipe(name = "Espárragos a la Parrilla", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "A la Parrilla", subcategory2 = "Espárragos a la Parrilla", ingredients = arrayListOf("Espárragos", "Aceite de oliva", "Ajo", "Sal", "Pimienta")),
                    Recipe(name = "Hamburguesas", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "A la Parrilla", subcategory2 = "Hamburguesas", ingredients = arrayListOf("Carne molida", "Pan de hamburguesa", "Lechuga", "Tomate", "Cebolla", "Queso", "Sal", "Pimienta")),
                    Recipe(name = "Brochetas de Pollo", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "A la Parrilla", subcategory2 = "Brochetas de Pollo", ingredients = arrayListOf("Pechuga de pollo", "Pimientos", "Cebolla", "Aceite de oliva", "Especias")),
                    Recipe(name = "Piña a la Parrilla", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "A la Parrilla", subcategory2 = "Piña a la Parrilla", ingredients = arrayListOf("Piña", "Azúcar moreno", "Canela", "Mantequilla")),
                    Recipe(name = "Croquetas de Jamón", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "Frituras", subcategory2 = "Croquetas de Jamón", ingredients = arrayListOf("Jamón", "Harina", "Leche", "Huevo", "Pan rallado", "Mantequilla", "Pimienta")),
                    Recipe(name = "Papas Fritas", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "Frituras", subcategory2 = "Papas Fritas", ingredients = arrayListOf("Papas", "Aceite para freír", "Sal")),
                    Recipe(name = "Tempura de Verduras", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "Frituras", subcategory2 = "Tempura de Verduras", ingredients = arrayListOf("Calabacín", "Berenjena", "Pimientos", "Harina", "Huevo", "Agua fría", "Aceite para freír")),
                    Recipe(name = "Pollo Frito", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "Frituras", subcategory2 = "Pollo Frito", ingredients = arrayListOf("Pechuga de pollo", "Harina", "Huevo", "Aceite para freír", "Sal", "Pimienta")),
                    Recipe(name = "Donas", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "Frituras", subcategory2 = "Donas", ingredients = arrayListOf("Harina", "Levadura", "Leche", "Huevo", "Azúcar", "Mantequilla", "Aceite para freír")),
                    Recipe(name = "Ceviche", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "En Crudo", subcategory2 = "Ceviche", ingredients = arrayListOf("Pescado fresco", "Cebolla morada", "Limón", "Cilantro", "Ají", "Sal", "Pimienta")),
                    Recipe(name = "Tartare de Salmón", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "En Crudo", subcategory2 = "Tartare de Salmón", ingredients = arrayListOf("Salmón fresco", "Aguacate", "Cebollín", "Salsa de soja", "Aceite de oliva", "Jengibre")),
                    Recipe(name = "Carpaccio de Res", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "En Crudo", subcategory2 = "Carpaccio de Res", ingredients = arrayListOf("Carne de res", "Aceite de oliva", "Queso parmesano", "Rúcula", "Limón", "Sal", "Pimienta")),
                    Recipe(name = "Ensalada Waldorf", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "En Crudo", subcategory2 = "Ensalada Waldorf", ingredients = arrayListOf("Manzana", "Apio", "Nueces", "Uvas", "Yogur", "Mayonesa", "Limón")),
                    Recipe(name = "Gazpacho", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "En Crudo", subcategory2 = "Gazpacho", ingredients = arrayListOf("Tomate", "Pepino", "Pimiento", "Ajo", "Aceite de oliva", "Vinagre", "Pan", "Sal")),
                    Recipe(name = "Chili con Carne", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "Sopa", subcategory2 = "Chili con Carne", ingredients = arrayListOf("Carne molida", "Frijoles", "Tomate", "Cebolla", "Ajo", "Especias")),
                    Recipe(name = "Sopa Minestrone", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "Sopa", subcategory2 = "Sopa Minestrone", ingredients = arrayListOf("Pasta", "Verduras", "Caldo de verduras", "Tomate", "Ajo", "Albahaca")),
                    Recipe(name = "Pho", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "Sopa", subcategory2 = "Pho", ingredients = arrayListOf("Fideos de arroz", "Caldo de res", "Hierbas frescas", "Limón", "Jengibre", "Salsa de soja")),
                    Recipe(name = "Estofado de Ternera", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "Sopa", subcategory2 = "Estofado de Ternera", ingredients = arrayListOf("Carne de ternera", "Zanahorias", "Papas", "Cebolla", "Ajo", "Caldo de res")),
                    Recipe(name = "Sopa de Pollo", image = R.drawable.recetapplogo, category = "Método de Preparación", subcategory1 = "Sopa", subcategory2 = "Sopa de Pollo", ingredients = arrayListOf("Pollo", "Zanahorias", "Apio", "Fideos", "Cebolla", "Ajo")),
                    Recipe(name = "Pastel de Chocolate", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "Cumpleaños", subcategory2 = "Pastel de Chocolate", ingredients = arrayListOf("Harina", "Cacao en polvo", "Huevo", "Azúcar", "Leche", "Mantequilla", "Polvo de hornear")),
                    Recipe(name = "Cupcakes Decorados", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "Cumpleaños", subcategory2 = "Cupcakes Decorados", ingredients = arrayListOf("Harina", "Huevo", "Azúcar", "Mantequilla", "Esencia de vainilla", "Glaseado")),
                    Recipe(name = "Croissants Rellenos", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "Cumpleaños", subcategory2 = "Croissants Rellenos", ingredients = arrayListOf("Masa de hojaldre", "Jamón", "Queso", "Mantequilla", "Huevo")),
                    Recipe(name = "Mini Pizzas", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "Cumpleaños", subcategory2 = "Mini Pizzas", ingredients = arrayListOf("Masa para pizza", "Tomate", "Queso", "Pepperoni", "Albahaca")),
                    Recipe(name = "Sangría", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "Cumpleaños", subcategory2 = "Sangría", ingredients = arrayListOf("Vino tinto", "Frutas (naranjas, manzanas)", "Brandy", "Azúcar", "Agua con gas")),
                    Recipe(name = "Pavo Relleno", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "Navidad", subcategory2 = "Pavo Relleno", ingredients = arrayListOf("Pavo entero", "Pan rallado", "Cebolla", "Ajo", "Especias", "Caldo de pollo")),
                    Recipe(name = "Ensalada Waldorf", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "Navidad", subcategory2 = "Ensalada Waldorf", ingredients = arrayListOf("Manzana", "Apio", "Nueces", "Uvas", "Yogur", "Mayonesa")),
                    Recipe(name = "Galletas de Jengibre", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "Navidad", subcategory2 = "Galletas de Jengibre", ingredients = arrayListOf("Harina", "Jengibre en polvo", "Canela", "Mantequilla", "Azúcar", "Huevo")),
                    Recipe(name = "Ponche Navideño", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "Navidad", subcategory2 = "Ponche Navideño", ingredients = arrayListOf("Leche", "Azúcar", "Clavo de olor", "Nuez moscada", "Huevos", "Ron")),
                    Recipe(name = "Tronco de Navidad", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "Navidad", subcategory2 = "Tronco de Navidad", ingredients = arrayListOf("Bizcocho", "Mantequilla", "Cacao", "Crema de chocolate", "Azúcar")),
                    Recipe(name = "Fresas Cubiertas de Chocolate", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "San Valentín", subcategory2 = "Fresas Cubiertas de Chocolate", ingredients = arrayListOf("Fresas", "Chocolate negro", "Aceite de coco")),
                    Recipe(name = "Fondue de Queso", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "San Valentín", subcategory2 = "Fondue de Queso", ingredients = arrayListOf("Queso suizo", "Vino blanco", "Ajo", "Pan", "Pimienta")),
                    Recipe(name = "Raviolis en Salsa de Queso", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "San Valentín", subcategory2 = "Raviolis en Salsa de Queso", ingredients = arrayListOf("Raviolis", "Queso crema", "Leche", "Ajo", "Mantequilla")),
                    Recipe(name = "Tarta de Fresas", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "San Valentín", subcategory2 = "Tarta de Fresas", ingredients = arrayListOf("Fresas", "Harina", "Azúcar", "Mantequilla", "Huevo", "Crema pastelera")),
                    Recipe(name = "Martini de Frambuesa", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "San Valentín", subcategory2 = "Martini de Frambuesa", ingredients = arrayListOf("Vodka", "Frambuesas", "Jugo de limón", "Azúcar")),
                    Recipe(name = "Sandwiches Club", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "Picnic", subcategory2 = "Sandwiches Club", ingredients = arrayListOf("Pan de molde", "Pollo", "Bacon", "Lechuga", "Tomate", "Mayonesa")),
                    Recipe(name = "Ensalada César", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "Picnic", subcategory2 = "Ensalada César", ingredients = arrayListOf("Manzanas", "Masa para tarta", "Azúcar", "Canela", "Mantequilla")),
                    Recipe(name = "Wraps de Pollo", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "Picnic", subcategory2 = "Wraps de Pollo", ingredients = arrayListOf("Pollo a la parrilla", "Tortillas", "Lechuga", "Tomate", "Mayonesa", "Aguacate")),
                    Recipe(name = "Lemonade", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "Picnic", subcategory2 = "Lemonade", ingredients = arrayListOf("Limón", "Agua", "Azúcar", "Hielo")),
                    Recipe(name = "Cocteles Variados", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "Fiesta de Año Nuevo", subcategory2 = "Cocteles Variados", ingredients = arrayListOf("Vodka", "Ron", "Ginebra", "Jugo de naranja", "Hielo")),
                    Recipe(name = "Canapés", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "Fiesta de Año Nuevo", subcategory2 = "Canapés", ingredients = arrayListOf("Pan de canapé", "Queso crema", "Salmón ahumado", "Caviar", "Pepino")),
                    Recipe(name = "Mini Quiches", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "Fiesta de Año Nuevo", subcategory2 = "Canapés", ingredients = arrayListOf("Masa para quiche", "Huevos", "Queso", "Espinacas", "Bacon")),
                    Recipe(name = "Tarta de Santiago", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "Fiesta de Año Nuevo", subcategory2 = "Canapés", ingredients = arrayListOf("Almendras", "Huevo", "Azúcar", "Limón", "Harina")),
                    Recipe(name = "Fondue de Chocolate", image = R.drawable.recetapplogo, category = "Evento", subcategory1 = "Fiesta de Año Nuevo", subcategory2 = "Canapés", ingredients = arrayListOf("Chocolate negro", "Crema de leche", "Frutas", "Malvaviscos"))
                )

                dbAccess.recipeDao().insertarRecetas(recipes)
                setUp()

            } else {
                lifecycleScope.launch {
                    setUp()
                }

            }
        }
        lifecycleScope.launch {
            val randomRecipe = dbAccess.recipeDao().obtenerRecetaAleatoria()
            binding.nameRecipe.text = randomRecipe.name
            binding.imageRecipe.setImageResource(randomRecipe.image)
            binding.categoryName.text = randomRecipe.category
            binding.subcategoryName.text = randomRecipe.subcategory1
        }

        //setUpRecyclerView()
    }
    private fun navigateToHome() {
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
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
                Subcategory(name = "Año nuevo", recipes = dbAccess.recipeDao().obtenerPorSubcategoria("Año nuevo"), category = "Eventos"))

            recyclerSubcategoryAdapter.addDataToList(subcategories)
            binding.recyclerViewSubcategories.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = recyclerSubcategoryAdapter
            }
        }

    }

//    fun setUpRecyclerView() {
//        val listaDatos = mutableListOf(
//            Subcategory(name = "Italia", recipes = listOf(
//                Recipe(name = "Pasta Carbonara", ingredients = listOf("Spaguetti", "Tocino", "Parmesano", "Huevo")),
//                Recipe(name = "Pizza Margarita", ingredients = listOf("Masa", "Tomate", "Mozzarella", "Albahaca")),
//                Recipe(name = "Lasagna", ingredients = listOf("Pasta", "Carne", "Queso", "Tomate")),
//                Recipe(name = "Risotto al pesto", ingredients = listOf("Arroz", "Pescado", "Pesto", "Ajo")),
//                Recipe(name = "Tiramisu", ingredients = listOf("Masa", "Chocolate", "Leche", "Canela"))
//            )),
//            Subcategory(name = "México", recipes = listOf(
//                Recipe(name = "Tacos al Pastor", ingredients = listOf("Tortilla", "Carne al Pastor", "Cilantro", "Cebolla", "Piña")),
//                Recipe(name = "Enchiladas Verdes", ingredients = listOf("Tortillas", "Salsa verde", "Pollo", "Cebolla", "Crema")),
//                Recipe(name = "Guacamole", ingredients = listOf("Aguacate", "Tomate", "Cebolla", "Cilantro", "Lima")),
//                Recipe(name = "Chiles Rellenos", ingredients = listOf("Chile poblano", "Carne molida", "Queso", "Salsa", "Huevos")),
//                Recipe(name = "Pozole", ingredients = listOf("Maíz pozolero", "Carne de cerdo", "Chile ancho", "Lechuga", "Orégano"))
//            )),
//            Subcategory(name = "Japón", recipes = listOf(
//                Recipe(name = "Sushi (Maki Rolls)", ingredients = listOf("Arroz de sushi", "Alga nori", "Pescado", "Aguacate", "Vinagre")),
//                Recipe(name = "Ramen", ingredients = listOf("Fideos", "Caldo de cerdo", "Cerdo", "Huevo", "Cebollines")),
//                Recipe(name = "Tempura", ingredients = listOf("Verduras", "Mariscos", "Harina", "Huevo", "Aceite")),
//                Recipe(name = "Okonomiyaki", ingredients = listOf("Harina", "Col", "Cebolla", "Cerdo", "Salsa Okonomiyaki")),
//                Recipe(name = "Dorayaki", ingredients = listOf("Harina", "Azúcar", "Huevos", "Anko (pasta de frijoles rojos)", "Miel"))
//            )),
//            Subcategory(name = "Bolivia", recipes = listOf(
//                Recipe(name = "Silpancho", ingredients = listOf("Carne de res", "Arroz", "Papa", "Huevo", "Cebolla", "Tomate")),
//                Recipe(name = "Plato Paceño", ingredients = listOf("Arroz", "Papa", "Carne de res", "Chicharrón", "Salsa de maní")),
//                Recipe(name = "Majadito", ingredients = listOf("Arroz", "Carne de res", "Plátano", "Huevo", "Verduras")),
//                Recipe(name = "Sopa de Maní", ingredients = listOf("Maní", "Carne de res", "Papa", "Fideos", "Verduras")),
//                Recipe(name = "Pique a lo Macho", ingredients = listOf("Carne de res", "Papa", "Cebolla", "Tomate", "Ají rojo"))
//            )),
//            Subcategory(name = "Francia", recipes = listOf(
//                Recipe(name = "Quiche Lorraine", ingredients = listOf("Masa", "Huevos", "Crema", "Bacon", "Queso")),
//                Recipe(name = "Ratatouille", ingredients = listOf("Calabacín", "Berenjena", "Pimiento", "Tomate", "Aceite de oliva")),
//                Recipe(name = "Soupe à l'Oignon", ingredients = listOf("Cebolla", "Caldo de carne", "Vino blanco", "Queso Gruyère", "Pan")),
//                Recipe(name = "Croissant", ingredients = listOf("Harina", "Mantequilla", "Levadura", "Leche", "Azúcar")),
//                Recipe(name = "Crème Brûlée", ingredients = listOf("Yemas de huevo", "Azúcar", "Crema", "Vainilla", "Caramelo"))
//            )),
//            Subcategory(name = "Desayunos", recipes = listOf(
//                Recipe(name = "Pancakes", ingredients = listOf("Harina", "Leche", "Huevo", "Azúcar", "Mantequilla")),
//                Recipe(name = "Smoothie Bowl", ingredients = listOf("Frutas", "Yogur", "Granola", "Miel", "Semillas")),
//                Recipe(name = "Huevos Benedictinos", ingredients = listOf("Huevos", "Muffins ingleses", "Jamón", "Salsa holandesa", "Espárragos")),
//                Recipe(name = "Avena con Frutas", ingredients = listOf("Avena", "Leche", "Frutas frescas", "Miel", "Nueces")),
//                Recipe(name = "Tostadas de Aguacate", ingredients = listOf("Aguacate", "Pan integral", "Limón", "Tomate", "Sal"))
//            )),
//            Subcategory(name = "Entradas", recipes = listOf(
//                Recipe(name = "Bruschettas", ingredients = listOf("Pan de baguette", "Tomate", "Ajo", "Aceite de oliva", "Albahaca")),
//                Recipe(name = "Hummus con Pita", ingredients = listOf("Garbanzos", "Tahini", "Ajo", "Aceite de oliva", "Pita")),
//                Recipe(name = "Sopa de Tomate", ingredients = listOf("Tomates", "Cebolla", "Ajo", "Aceite de oliva", "Caldo de verduras")),
//                Recipe(name = "Calamares Fritos", ingredients = listOf("Calamares", "Harina", "Aceite", "Sal", "Pimienta")),
//                Recipe(name = "Tabule", ingredients = listOf("Cebada", "Tomates", "Pepino", "Perejil", "Menta"))
//            )),
//            Subcategory(name = "Platos Principales", recipes = listOf(
//                Recipe(name = "Pollo al Curry", ingredients = listOf("Pollo", "Curry", "Cebolla", "Leche de coco", "Arroz")),
//                Recipe(name = "Espagueti Boloñesa", ingredients = listOf("Espaguetis", "Carne molida", "Tomate", "Cebolla", "Ajo")),
//                Recipe(name = "Estofado de Res", ingredients = listOf("Carne de res", "Papa", "Zanahoria", "Cebolla", "Caldo de carne")),
//                Recipe(name = "Paella", ingredients = listOf("Arroz", "Mariscos", "Pollo", "Pimiento", "Caldo de pescado")),
//                Recipe(name = "Salmón a la Plancha", ingredients = listOf("Salmón", "Aceite de oliva", "Limón", "Ajo", "Romero"))
//            )),
//            Subcategory(name = "Postres", recipes = listOf(
//                Recipe(name = "Cheesecake", ingredients = listOf("Queso crema", "Galletas", "Mantequilla", "Azúcar", "Gelatina")),
//                Recipe(name = "Brownies", ingredients = listOf("Chocolate", "Harina", "Azúcar", "Mantequilla", "Huevos")),
//                Recipe(name = "Arroz con Leche", ingredients = listOf("Arroz", "Leche", "Azúcar", "Canela", "Pasas")),
//                Recipe(name = "Flan", ingredients = listOf("Leche", "Azúcar", "Huevos", "Vainilla", "Caramelo")),
//                Recipe(name = "Churros", ingredients = listOf("Harina", "Azúcar", "Leche", "Aceite", "Canela"))
//            )),
//            Subcategory(name = "Bebidas", recipes = listOf(
//                Recipe(name = "Mojito", ingredients = listOf("Ron", "Menta", "Lima", "Azúcar", "Soda")),
//                Recipe(name = "Margarita", ingredients = listOf("Tequila", "Limón", "Triple sec", "Sal", "Hielo")),
//                Recipe(name = "Té Matcha", ingredients = listOf("Té Matcha", "Leche", "Miel", "Hielo", "Agua caliente")),
//                Recipe(name = "Batido de Fresa", ingredients = listOf("Fresas", "Leche", "Yogur", "Miel", "Hielo")),
//                Recipe(name = "Horchata", ingredients = listOf("Arroz", "Canela", "Leche", "Azúcar", "Vanilla"))
//            )),
//            Subcategory(name = "Vegetariana", recipes = listOf(
//                Recipe(name = "Ensalada Caprese", ingredients = listOf("Tomate", "Mozzarella", "Albahaca", "Aceite de oliva", "Vinagre balsámico")),
//                Recipe(name = "Curry de Garbanzos", ingredients = listOf("Garbanzos", "Curry", "Cebolla", "Leche de coco", "Espinaca")),
//                Recipe(name = "Hamburguesa de Lentejas", ingredients = listOf("Lentejas", "Avena", "Cebolla", "Ajo", "Especias")),
//                Recipe(name = "Risotto de Champiñones", ingredients = listOf("Arroz", "Champiñones", "Cebolla", "Caldo de verduras", "Queso parmesano")),
//                Recipe(name = "Ratatouille", ingredients = listOf("Berenjena", "Calabacín", "Pimiento", "Tomate", "Aceite de oliva"))
//            )),
//            Subcategory(name = "Vegana", recipes = listOf(
//                Recipe(name = "Tacos de Soya", ingredients = listOf("Soya texturizada", "Tortillas", "Aguacate", "Cebolla", "Limón")),
//                Recipe(name = "Smoothie Verde", ingredients = listOf("Espinaca", "Plátano", "Manzana", "Leche de almendra", "Jengibre")),
//                Recipe(name = "Pasta con Salsa de Anacardos", ingredients = listOf("Pasta", "Anacardos", "Leche de coco", "Ajo", "Albahaca")),
//                Recipe(name = "Brownies Veganos", ingredients = listOf("Harina de avena", "Cacao", "Plátano", "Almendras", "Jarabe de arce")),
//                Recipe(name = "Helado de Plátano", ingredients = listOf("Plátano", "Leche de coco", "Almendras", "Miel", "Extracto de vainilla"))
//            )),
//            Subcategory(name = "Sin Gluten", recipes = listOf(
//                Recipe(name = "Pizza de Coliflor", ingredients = listOf("Coliflor", "Harina de almendra", "Queso", "Huevo", "Tomate")),
//                Recipe(name = "Ensalada Griega", ingredients = listOf("Pepino", "Tomate", "Aceitunas", "Queso feta", "Aceite de oliva")),
//                Recipe(name = "Pollo a la Parrilla con Vegetales", ingredients = listOf("Pollo", "Calabacín", "Pimiento", "Aceite de oliva", "Romero")),
//                Recipe(name = "Sopa de Lentejas", ingredients = listOf("Lentejas", "Zanahorias", "Cebolla", "Ajo", "Caldo de verduras")),
//                Recipe(name = "Bizcocho de Almendra", ingredients = listOf("Harina de almendra", "Huevos", "Mantequilla", "Miel", "Polvo de hornear"))
//            )),
//            Subcategory(name = "Low Carb", recipes = listOf(
//                Recipe(name = "Zoodles con Pesto", ingredients = listOf("Calabacín", "Albahaca", "Ajo", "Aceite de oliva", "Queso parmesano")),
//                Recipe(name = "Pollo a la Mostaza", ingredients = listOf("Pollo", "Mostaza", "Crema de coco", "Aceite de oliva", "Ajo")),
//                Recipe(name = "Ensalada César sin Croutons", ingredients = listOf("Lechuga romana", "Queso parmesano", "Aceite de oliva", "Ajo", "Salsa César sin gluten")),
//                Recipe(name = "Tacos de Lechuga", ingredients = listOf("Carne molida", "Tortillas de lechuga", "Aguacate", "Tomate", "Limón")),
//                Recipe(name = "Cheesecake Keto", ingredients = listOf("Queso crema", "Harina de almendra", "Mantequilla", "Stevia", "Extracto de vainilla"))
//            )),
//            Subcategory(name = "Alta en Proteína", recipes = listOf(
//                Recipe(name = "Pechuga de Pollo al Limón", ingredients = listOf("Pechuga de pollo", "Limón", "Aceite de oliva", "Ajo", "Romero")),
//                Recipe(name = "Tortilla de Claras con Espinaca", ingredients = listOf("Claras de huevo", "Espinaca", "Cebolla", "Aceite de oliva", "Tomate")),
//                Recipe(name = "Salmón con Salsa Teriyaki", ingredients = listOf("Salmón", "Salsa teriyaki", "Ajo", "Jengibre", "Aceite de oliva")),
//                Recipe(name = "Batido de Proteína", ingredients = listOf("Proteína en polvo", "Leche de almendra", "Plátano", "Almendras", "Hielo")),
//                Recipe(name = "Albóndigas al Horno", ingredients = listOf("Carne de res", "Ajo", "Huevo", "Pan rallado sin gluten", "Perejil"))
//            )),
//            Subcategory(name = "Al Horno", recipes = listOf(
//                Recipe(name = "Lasagna", ingredients = listOf("Pasta", "Carne molida", "Tomate", "Queso", "Ajo")),
//                Recipe(name = "Pollo Rostizado", ingredients = listOf("Pollo", "Ajo", "Limón", "Pimienta", "Romero")),
//                Recipe(name = "Pan de Plátano", ingredients = listOf("Plátano", "Harina", "Huevos", "Mantequilla", "Azúcar")),
//                Recipe(name = "Pizza Casera", ingredients = listOf("Masa de pizza", "Tomate", "Mozzarella", "Albahaca", "Aceite de oliva")),
//                Recipe(name = "Pescado en Papillote", ingredients = listOf("Pescado", "Verduras", "Limón", "Aceite de oliva", "Papel de hornear"))
//            )),
//            Subcategory(name = "A la Parrilla", recipes = listOf(
//                Recipe(name = "Costillas BBQ", ingredients = listOf("Costillas", "Salsa BBQ", "Sal", "Pimienta", "Ajo")),
//                Recipe(name = "Espárragos a la Parrilla", ingredients = listOf("Espárragos", "Aceite de oliva", "Ajo", "Sal", "Pimienta")),
//                Recipe(name = "Hamburguesas", ingredients = listOf("Carne molida", "Pan", "Queso", "Lechuga", "Tomate")),
//                Recipe(name = "Brochetas de Pollo", ingredients = listOf("Pollo", "Pimientos", "Cebolla", "Aceite de oliva", "Especias")),
//                Recipe(name = "Piña a la Parrilla", ingredients = listOf("Piña", "Miel", "Canela"))
//            )),
//            Subcategory(name = "Frituras", recipes = listOf(
//                Recipe(name = "Croquetas de Jamón", ingredients = listOf("Jamón", "Harina", "Leche", "Huevo", "Pan rallado")),
//                Recipe(name = "Papas Fritas", ingredients = listOf("Papas", "Aceite", "Sal")),
//                Recipe(name = "Tempura de Verduras", ingredients = listOf("Verduras", "Harina", "Agua fría", "Aceite", "Salsa de soja")),
//                Recipe(name = "Pollo Frito", ingredients = listOf("Pollo", "Harina", "Huevo", "Pan rallado", "Aceite")),
//                Recipe(name = "Donas", ingredients = listOf("Harina", "Leche", "Azúcar", "Levadura", "Aceite"))
//            )),
//            Subcategory(name = "En Crudo", recipes = listOf(
//                Recipe(name = "Ceviche", ingredients = listOf("Pescado", "Limón", "Cebolla", "Cilantro", "Tomate")),
//                Recipe(name = "Tartare de Salmón", ingredients = listOf("Salmón", "Cebollín", "Aguacate", "Limón", "Aceite de oliva")),
//                Recipe(name = "Carpaccio de Res", ingredients = listOf("Res", "Alcaparras", "Aceite de oliva", "Limón", "Pimienta")),
//                Recipe(name = "Ensalada Waldorf", ingredients = listOf("Manzana", "Apio", "Nueces", "Yogur", "Uvas")),
//                Recipe(name = "Gazpacho", ingredients = listOf("Tomate", "Pepino", "Pimiento", "Cebolla", "Aceite de oliva"))
//            )),
//            Subcategory(name = "Sopa", recipes = listOf(
//                Recipe(name = "Chili con Carne", ingredients = listOf("Carne molida", "Frijoles", "Tomate", "Pimienta", "Cebolla")),
//                Recipe(name = "Sopa Minestrone", ingredients = listOf("Verduras", "Pasta", "Caldo de verduras", "Tomate", "Albahaca")),
//                Recipe(name = "Pho", ingredients = listOf("Fideos de arroz", "Carne de res", "Caldo", "Jengibre", "Limón")),
//                Recipe(name = "Estofado de Ternera", ingredients = listOf("Ternera", "Zanahorias", "Cebolla", "Caldo de carne", "Papas")),
//                Recipe(name = "Sopa de Pollo", ingredients = listOf("Pollo", "Zanahorias", "Apio", "Fideos", "Caldo de pollo"))
//            )),
//            Subcategory(name = "Cumpleaños", recipes = listOf(
//                Recipe(name = "Pastel de Chocolate", ingredients = listOf("Harina", "Chocolate", "Huevo", "Azúcar", "Mantequilla")),
//                Recipe(name = "Cupcakes Decorados", ingredients = listOf("Harina", "Azúcar", "Huevo", "Mantequilla", "Colorante")),
//                Recipe(name = "Croissants Rellenos", ingredients = listOf("Masa de hojaldre", "Chocolate", "Azúcar", "Mantequilla")),
//                Recipe(name = "Mini Pizzas", ingredients = listOf("Masa de pizza", "Tomate", "Mozzarella", "Aceitunas", "Pimiento")),
//                Recipe(name = "Sangría", ingredients = listOf("Vino tinto", "Frutas", "Azúcar", "Brandy", "Agua con gas"))
//            )),
//            Subcategory(name = "Navidad", recipes = listOf(
//                Recipe(name = "Pavo Relleno", ingredients = listOf("Pavo", "Pan rallado", "Cebolla", "Ajo", "Especias")),
//                Recipe(name = "Ensalada Waldorf", ingredients = listOf("Manzana", "Apio", "Nueces", "Yogur", "Uvas")),
//                Recipe(name = "Galletas de Jengibre", ingredients = listOf("Harina", "Jengibre", "Mantequilla", "Azúcar", "Canela")),
//                Recipe(name = "Ponche Navideño", ingredients = listOf("Leche", "Azúcar", "Nuez moscada", "Huevo", "Ron")),
//                Recipe(name = "Tronco de Navidad", ingredients = listOf("Bizcocho", "Crema de chocolate", "Mantequilla", "Azúcar", "Cacao"))
//            )),
//            Subcategory(name = "San Valentín", recipes = listOf(
//                Recipe(name = "Fresas Cubiertas de Chocolate", ingredients = listOf("Fresas", "Chocolate", "Azúcar")),
//                Recipe(name = "Fondue de Queso", ingredients = listOf("Queso", "Vino blanco", "Ajo", "Pan", "Pimienta")),
//                Recipe(name = "Raviolis en Salsa de Queso", ingredients = listOf("Pasta", "Queso", "Crema", "Ajo", "Pimienta")),
//                Recipe(name = "Tarta de Fresas", ingredients = listOf("Fresas", "Harina", "Azúcar", "Mantequilla", "Crema")),
//                Recipe(name = "Martini de Frambuesa", ingredients = listOf("Vodka", "Frambuesas", "Lima", "Azúcar"))
//            )),
//            Subcategory(name = "Picnic", recipes = listOf(
//                Recipe(name = "Sandwiches Club", ingredients = listOf("Pan", "Pavo", "Lechuga", "Tomate", "Mayonesa")),
//                Recipe(name = "Ensalada César", ingredients = listOf("Lechuga", "Pollo", "Croutons", "Queso parmesano", "Salsa César")),
//                Recipe(name = "Tarta de Manzana", ingredients = listOf("Manzana", "Harina", "Azúcar", "Mantequilla", "Canela")),
//                Recipe(name = "Wraps de Pollo", ingredients = listOf("Pollo", "Tortillas", "Lechuga", "Tomate", "Salsa")),
//                Recipe(name = "Lemonade", ingredients = listOf("Limón", "Azúcar", "Agua", "Hielo"))
//            )),
//            Subcategory(name = "Fiesta de Año Nuevo", recipes = listOf(
//                Recipe(name = "Cocteles Variados", ingredients = listOf("Vodka", "Ron", "Gin", "Jugo de frutas", "Soda")),
//                Recipe(name = "Canapés", ingredients = listOf("Pan", "Queso", "Jamon", "Aceitunas", "Pimientos")),
//                Recipe(name = "Mini Quiches", ingredients = listOf("Masa", "Huevo", "Queso", "Verduras", "Bacon")),
//                Recipe(name = "Tarta de Santiago", ingredients = listOf("Almendra", "Huevo", "Azúcar", "Harina", "Limón")),
//                Recipe(name = "Fondue de Chocolate", ingredients = listOf("Chocolate", "Crema", "Frutas", "Galletas"))
//            )),
//        )
//
//        recyclerSubcategoryAdapter.addDataToList(listaDatos)
//
//        // Configuración del primer RecyclerView
//        binding.recyclerViewSubcategories.apply {
//            layoutManager =
//                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//            adapter = recyclerSubcategoryAdapter
//        }
//
//        binding.categoryIcon.setOnClickListener {
//            val intentCategory = Intent(this, CategoriesActivity::class.java)
//            startActivity(intentCategory)
//        }
//
//    }
}