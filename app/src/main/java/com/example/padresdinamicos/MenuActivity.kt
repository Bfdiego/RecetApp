package com.example.padresdinamicos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.padresdinamicos.adapters.RecyclerRecipeAdapter
import com.example.padresdinamicos.adapters.RecyclerSubcategoryAdapter
import com.example.padresdinamicos.databinding.ActivityMenuBinding
import com.example.padresdinamicos.dataclasses.Recipe
import com.example.padresdinamicos.dataclasses.Subcategory
import com.google.gson.Gson

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private val recyclerSubcategoryAdapter by lazy { RecyclerSubcategoryAdapter() }
    private val recyclerRecipeAdapter by lazy { RecyclerRecipeAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)

        setUpRecyclerView()
    }
    private fun navigateToHome() {
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }


    fun setUpRecyclerView() {
        val listaDatos = mutableListOf(
            Subcategory(name = "Italia", recipes = listOf(
                Recipe(name = "Pasta Carbonara", ingredients = listOf("Spaguetti", "Tocino", "Parmesano", "Huevo")),
                Recipe(name = "Pizza Margarita", ingredients = listOf("Masa", "Tomate", "Mozzarella", "Albahaca")),
                Recipe(name = "Lasagna", ingredients = listOf("Pasta", "Carne", "Queso", "Tomate")),
                Recipe(name = "Risotto al pesto", ingredients = listOf("Arroz", "Pescado", "Pesto", "Ajo")),
                Recipe(name = "Tiramisu", ingredients = listOf("Masa", "Chocolate", "Leche", "Canela"))
            )),
            Subcategory(name = "México", recipes = listOf(
                Recipe(name = "Tacos al Pastor", ingredients = listOf("Tortilla", "Carne al Pastor", "Cilantro", "Cebolla", "Piña")),
                Recipe(name = "Enchiladas Verdes", ingredients = listOf("Tortillas", "Salsa verde", "Pollo", "Cebolla", "Crema")),
                Recipe(name = "Guacamole", ingredients = listOf("Aguacate", "Tomate", "Cebolla", "Cilantro", "Lima")),
                Recipe(name = "Chiles Rellenos", ingredients = listOf("Chile poblano", "Carne molida", "Queso", "Salsa", "Huevos")),
                Recipe(name = "Pozole", ingredients = listOf("Maíz pozolero", "Carne de cerdo", "Chile ancho", "Lechuga", "Orégano"))
            )),
            Subcategory(name = "Japón", recipes = listOf(
                Recipe(name = "Sushi (Maki Rolls)", ingredients = listOf("Arroz de sushi", "Alga nori", "Pescado", "Aguacate", "Vinagre")),
                Recipe(name = "Ramen", ingredients = listOf("Fideos", "Caldo de cerdo", "Cerdo", "Huevo", "Cebollines")),
                Recipe(name = "Tempura", ingredients = listOf("Verduras", "Mariscos", "Harina", "Huevo", "Aceite")),
                Recipe(name = "Okonomiyaki", ingredients = listOf("Harina", "Col", "Cebolla", "Cerdo", "Salsa Okonomiyaki")),
                Recipe(name = "Dorayaki", ingredients = listOf("Harina", "Azúcar", "Huevos", "Anko (pasta de frijoles rojos)", "Miel"))
            )),
            Subcategory(name = "Bolivia", recipes = listOf(
                Recipe(name = "Silpancho", ingredients = listOf("Carne de res", "Arroz", "Papa", "Huevo", "Cebolla", "Tomate")),
                Recipe(name = "Plato Paceño", ingredients = listOf("Arroz", "Papa", "Carne de res", "Chicharrón", "Salsa de maní")),
                Recipe(name = "Majadito", ingredients = listOf("Arroz", "Carne de res", "Plátano", "Huevo", "Verduras")),
                Recipe(name = "Sopa de Maní", ingredients = listOf("Maní", "Carne de res", "Papa", "Fideos", "Verduras")),
                Recipe(name = "Pique a lo Macho", ingredients = listOf("Carne de res", "Papa", "Cebolla", "Tomate", "Ají rojo"))
            )),
            Subcategory(name = "Francia", recipes = listOf(
                Recipe(name = "Quiche Lorraine", ingredients = listOf("Masa", "Huevos", "Crema", "Bacon", "Queso")),
                Recipe(name = "Ratatouille", ingredients = listOf("Calabacín", "Berenjena", "Pimiento", "Tomate", "Aceite de oliva")),
                Recipe(name = "Soupe à l'Oignon", ingredients = listOf("Cebolla", "Caldo de carne", "Vino blanco", "Queso Gruyère", "Pan")),
                Recipe(name = "Croissant", ingredients = listOf("Harina", "Mantequilla", "Levadura", "Leche", "Azúcar")),
                Recipe(name = "Crème Brûlée", ingredients = listOf("Yemas de huevo", "Azúcar", "Crema", "Vainilla", "Caramelo"))
            )),
            Subcategory(name = "Desayunos", recipes = listOf(
                Recipe(name = "Pancakes", ingredients = listOf("Harina", "Leche", "Huevo", "Azúcar", "Mantequilla")),
                Recipe(name = "Smoothie Bowl", ingredients = listOf("Frutas", "Yogur", "Granola", "Miel", "Semillas")),
                Recipe(name = "Huevos Benedictinos", ingredients = listOf("Huevos", "Muffins ingleses", "Jamón", "Salsa holandesa", "Espárragos")),
                Recipe(name = "Avena con Frutas", ingredients = listOf("Avena", "Leche", "Frutas frescas", "Miel", "Nueces")),
                Recipe(name = "Tostadas de Aguacate", ingredients = listOf("Aguacate", "Pan integral", "Limón", "Tomate", "Sal"))
            )),
            Subcategory(name = "Entradas", recipes = listOf(
                Recipe(name = "Bruschettas", ingredients = listOf("Pan de baguette", "Tomate", "Ajo", "Aceite de oliva", "Albahaca")),
                Recipe(name = "Hummus con Pita", ingredients = listOf("Garbanzos", "Tahini", "Ajo", "Aceite de oliva", "Pita")),
                Recipe(name = "Sopa de Tomate", ingredients = listOf("Tomates", "Cebolla", "Ajo", "Aceite de oliva", "Caldo de verduras")),
                Recipe(name = "Calamares Fritos", ingredients = listOf("Calamares", "Harina", "Aceite", "Sal", "Pimienta")),
                Recipe(name = "Tabule", ingredients = listOf("Cebada", "Tomates", "Pepino", "Perejil", "Menta"))
            )),
            Subcategory(name = "Platos Principales", recipes = listOf(
                Recipe(name = "Pollo al Curry", ingredients = listOf("Pollo", "Curry", "Cebolla", "Leche de coco", "Arroz")),
                Recipe(name = "Espagueti Boloñesa", ingredients = listOf("Espaguetis", "Carne molida", "Tomate", "Cebolla", "Ajo")),
                Recipe(name = "Estofado de Res", ingredients = listOf("Carne de res", "Papa", "Zanahoria", "Cebolla", "Caldo de carne")),
                Recipe(name = "Paella", ingredients = listOf("Arroz", "Mariscos", "Pollo", "Pimiento", "Caldo de pescado")),
                Recipe(name = "Salmón a la Plancha", ingredients = listOf("Salmón", "Aceite de oliva", "Limón", "Ajo", "Romero"))
            )),
            Subcategory(name = "Postres", recipes = listOf(
                Recipe(name = "Cheesecake", ingredients = listOf("Queso crema", "Galletas", "Mantequilla", "Azúcar", "Gelatina")),
                Recipe(name = "Brownies", ingredients = listOf("Chocolate", "Harina", "Azúcar", "Mantequilla", "Huevos")),
                Recipe(name = "Arroz con Leche", ingredients = listOf("Arroz", "Leche", "Azúcar", "Canela", "Pasas")),
                Recipe(name = "Flan", ingredients = listOf("Leche", "Azúcar", "Huevos", "Vainilla", "Caramelo")),
                Recipe(name = "Churros", ingredients = listOf("Harina", "Azúcar", "Leche", "Aceite", "Canela"))
            )),
            Subcategory(name = "Bebidas", recipes = listOf(
                Recipe(name = "Mojito", ingredients = listOf("Ron", "Menta", "Lima", "Azúcar", "Soda")),
                Recipe(name = "Margarita", ingredients = listOf("Tequila", "Limón", "Triple sec", "Sal", "Hielo")),
                Recipe(name = "Té Matcha", ingredients = listOf("Té Matcha", "Leche", "Miel", "Hielo", "Agua caliente")),
                Recipe(name = "Batido de Fresa", ingredients = listOf("Fresas", "Leche", "Yogur", "Miel", "Hielo")),
                Recipe(name = "Horchata", ingredients = listOf("Arroz", "Canela", "Leche", "Azúcar", "Vanilla"))
            )),
            Subcategory(name = "Vegetariana", recipes = listOf(
                Recipe(name = "Ensalada Caprese", ingredients = listOf("Tomate", "Mozzarella", "Albahaca", "Aceite de oliva", "Vinagre balsámico")),
                Recipe(name = "Curry de Garbanzos", ingredients = listOf("Garbanzos", "Curry", "Cebolla", "Leche de coco", "Espinaca")),
                Recipe(name = "Hamburguesa de Lentejas", ingredients = listOf("Lentejas", "Avena", "Cebolla", "Ajo", "Especias")),
                Recipe(name = "Risotto de Champiñones", ingredients = listOf("Arroz", "Champiñones", "Cebolla", "Caldo de verduras", "Queso parmesano")),
                Recipe(name = "Ratatouille", ingredients = listOf("Berenjena", "Calabacín", "Pimiento", "Tomate", "Aceite de oliva"))
            )),
            Subcategory(name = "Vegana", recipes = listOf(
                Recipe(name = "Tacos de Soya", ingredients = listOf("Soya texturizada", "Tortillas", "Aguacate", "Cebolla", "Limón")),
                Recipe(name = "Smoothie Verde", ingredients = listOf("Espinaca", "Plátano", "Manzana", "Leche de almendra", "Jengibre")),
                Recipe(name = "Pasta con Salsa de Anacardos", ingredients = listOf("Pasta", "Anacardos", "Leche de coco", "Ajo", "Albahaca")),
                Recipe(name = "Brownies Veganos", ingredients = listOf("Harina de avena", "Cacao", "Plátano", "Almendras", "Jarabe de arce")),
                Recipe(name = "Helado de Plátano", ingredients = listOf("Plátano", "Leche de coco", "Almendras", "Miel", "Extracto de vainilla"))
            )),
            Subcategory(name = "Sin Gluten", recipes = listOf(
                Recipe(name = "Pizza de Coliflor", ingredients = listOf("Coliflor", "Harina de almendra", "Queso", "Huevo", "Tomate")),
                Recipe(name = "Ensalada Griega", ingredients = listOf("Pepino", "Tomate", "Aceitunas", "Queso feta", "Aceite de oliva")),
                Recipe(name = "Pollo a la Parrilla con Vegetales", ingredients = listOf("Pollo", "Calabacín", "Pimiento", "Aceite de oliva", "Romero")),
                Recipe(name = "Sopa de Lentejas", ingredients = listOf("Lentejas", "Zanahorias", "Cebolla", "Ajo", "Caldo de verduras")),
                Recipe(name = "Bizcocho de Almendra", ingredients = listOf("Harina de almendra", "Huevos", "Mantequilla", "Miel", "Polvo de hornear"))
            )),
            Subcategory(name = "Low Carb", recipes = listOf(
                Recipe(name = "Zoodles con Pesto", ingredients = listOf("Calabacín", "Albahaca", "Ajo", "Aceite de oliva", "Queso parmesano")),
                Recipe(name = "Pollo a la Mostaza", ingredients = listOf("Pollo", "Mostaza", "Crema de coco", "Aceite de oliva", "Ajo")),
                Recipe(name = "Ensalada César sin Croutons", ingredients = listOf("Lechuga romana", "Queso parmesano", "Aceite de oliva", "Ajo", "Salsa César sin gluten")),
                Recipe(name = "Tacos de Lechuga", ingredients = listOf("Carne molida", "Tortillas de lechuga", "Aguacate", "Tomate", "Limón")),
                Recipe(name = "Cheesecake Keto", ingredients = listOf("Queso crema", "Harina de almendra", "Mantequilla", "Stevia", "Extracto de vainilla"))
            )),
            Subcategory(name = "Alta en Proteína", recipes = listOf(
                Recipe(name = "Pechuga de Pollo al Limón", ingredients = listOf("Pechuga de pollo", "Limón", "Aceite de oliva", "Ajo", "Romero")),
                Recipe(name = "Tortilla de Claras con Espinaca", ingredients = listOf("Claras de huevo", "Espinaca", "Cebolla", "Aceite de oliva", "Tomate")),
                Recipe(name = "Salmón con Salsa Teriyaki", ingredients = listOf("Salmón", "Salsa teriyaki", "Ajo", "Jengibre", "Aceite de oliva")),
                Recipe(name = "Batido de Proteína", ingredients = listOf("Proteína en polvo", "Leche de almendra", "Plátano", "Almendras", "Hielo")),
                Recipe(name = "Albóndigas al Horno", ingredients = listOf("Carne de res", "Ajo", "Huevo", "Pan rallado sin gluten", "Perejil"))
            )),
            Subcategory(name = "Al Horno", recipes = listOf(
                Recipe(name = "Lasagna", ingredients = listOf("Pasta", "Carne molida", "Tomate", "Queso", "Ajo")),
                Recipe(name = "Pollo Rostizado", ingredients = listOf("Pollo", "Ajo", "Limón", "Pimienta", "Romero")),
                Recipe(name = "Pan de Plátano", ingredients = listOf("Plátano", "Harina", "Huevos", "Mantequilla", "Azúcar")),
                Recipe(name = "Pizza Casera", ingredients = listOf("Masa de pizza", "Tomate", "Mozzarella", "Albahaca", "Aceite de oliva")),
                Recipe(name = "Pescado en Papillote", ingredients = listOf("Pescado", "Verduras", "Limón", "Aceite de oliva", "Papel de hornear"))
            )),
            Subcategory(name = "A la Parrilla", recipes = listOf(
                Recipe(name = "Costillas BBQ", ingredients = listOf("Costillas", "Salsa BBQ", "Sal", "Pimienta", "Ajo")),
                Recipe(name = "Espárragos a la Parrilla", ingredients = listOf("Espárragos", "Aceite de oliva", "Ajo", "Sal", "Pimienta")),
                Recipe(name = "Hamburguesas", ingredients = listOf("Carne molida", "Pan", "Queso", "Lechuga", "Tomate")),
                Recipe(name = "Brochetas de Pollo", ingredients = listOf("Pollo", "Pimientos", "Cebolla", "Aceite de oliva", "Especias")),
                Recipe(name = "Piña a la Parrilla", ingredients = listOf("Piña", "Miel", "Canela"))
            )),
            Subcategory(name = "Frituras", recipes = listOf(
                Recipe(name = "Croquetas de Jamón", ingredients = listOf("Jamón", "Harina", "Leche", "Huevo", "Pan rallado")),
                Recipe(name = "Papas Fritas", ingredients = listOf("Papas", "Aceite", "Sal")),
                Recipe(name = "Tempura de Verduras", ingredients = listOf("Verduras", "Harina", "Agua fría", "Aceite", "Salsa de soja")),
                Recipe(name = "Pollo Frito", ingredients = listOf("Pollo", "Harina", "Huevo", "Pan rallado", "Aceite")),
                Recipe(name = "Donas", ingredients = listOf("Harina", "Leche", "Azúcar", "Levadura", "Aceite"))
            )),
            Subcategory(name = "En Crudo", recipes = listOf(
                Recipe(name = "Ceviche", ingredients = listOf("Pescado", "Limón", "Cebolla", "Cilantro", "Tomate")),
                Recipe(name = "Tartare de Salmón", ingredients = listOf("Salmón", "Cebollín", "Aguacate", "Limón", "Aceite de oliva")),
                Recipe(name = "Carpaccio de Res", ingredients = listOf("Res", "Alcaparras", "Aceite de oliva", "Limón", "Pimienta")),
                Recipe(name = "Ensalada Waldorf", ingredients = listOf("Manzana", "Apio", "Nueces", "Yogur", "Uvas")),
                Recipe(name = "Gazpacho", ingredients = listOf("Tomate", "Pepino", "Pimiento", "Cebolla", "Aceite de oliva"))
            )),
            Subcategory(name = "Sopa", recipes = listOf(
                Recipe(name = "Chili con Carne", ingredients = listOf("Carne molida", "Frijoles", "Tomate", "Pimienta", "Cebolla")),
                Recipe(name = "Sopa Minestrone", ingredients = listOf("Verduras", "Pasta", "Caldo de verduras", "Tomate", "Albahaca")),
                Recipe(name = "Pho", ingredients = listOf("Fideos de arroz", "Carne de res", "Caldo", "Jengibre", "Limón")),
                Recipe(name = "Estofado de Ternera", ingredients = listOf("Ternera", "Zanahorias", "Cebolla", "Caldo de carne", "Papas")),
                Recipe(name = "Sopa de Pollo", ingredients = listOf("Pollo", "Zanahorias", "Apio", "Fideos", "Caldo de pollo"))
            )),
            Subcategory(name = "Cumpleaños", recipes = listOf(
                Recipe(name = "Pastel de Chocolate", ingredients = listOf("Harina", "Chocolate", "Huevo", "Azúcar", "Mantequilla")),
                Recipe(name = "Cupcakes Decorados", ingredients = listOf("Harina", "Azúcar", "Huevo", "Mantequilla", "Colorante")),
                Recipe(name = "Croissants Rellenos", ingredients = listOf("Masa de hojaldre", "Chocolate", "Azúcar", "Mantequilla")),
                Recipe(name = "Mini Pizzas", ingredients = listOf("Masa de pizza", "Tomate", "Mozzarella", "Aceitunas", "Pimiento")),
                Recipe(name = "Sangría", ingredients = listOf("Vino tinto", "Frutas", "Azúcar", "Brandy", "Agua con gas"))
            )),
            Subcategory(name = "Navidad", recipes = listOf(
                Recipe(name = "Pavo Relleno", ingredients = listOf("Pavo", "Pan rallado", "Cebolla", "Ajo", "Especias")),
                Recipe(name = "Ensalada Waldorf", ingredients = listOf("Manzana", "Apio", "Nueces", "Yogur", "Uvas")),
                Recipe(name = "Galletas de Jengibre", ingredients = listOf("Harina", "Jengibre", "Mantequilla", "Azúcar", "Canela")),
                Recipe(name = "Ponche Navideño", ingredients = listOf("Leche", "Azúcar", "Nuez moscada", "Huevo", "Ron")),
                Recipe(name = "Tronco de Navidad", ingredients = listOf("Bizcocho", "Crema de chocolate", "Mantequilla", "Azúcar", "Cacao"))
            )),
            Subcategory(name = "San Valentín", recipes = listOf(
                Recipe(name = "Fresas Cubiertas de Chocolate", ingredients = listOf("Fresas", "Chocolate", "Azúcar")),
                Recipe(name = "Fondue de Queso", ingredients = listOf("Queso", "Vino blanco", "Ajo", "Pan", "Pimienta")),
                Recipe(name = "Raviolis en Salsa de Queso", ingredients = listOf("Pasta", "Queso", "Crema", "Ajo", "Pimienta")),
                Recipe(name = "Tarta de Fresas", ingredients = listOf("Fresas", "Harina", "Azúcar", "Mantequilla", "Crema")),
                Recipe(name = "Martini de Frambuesa", ingredients = listOf("Vodka", "Frambuesas", "Lima", "Azúcar"))
            )),
            Subcategory(name = "Picnic", recipes = listOf(
                Recipe(name = "Sandwiches Club", ingredients = listOf("Pan", "Pavo", "Lechuga", "Tomate", "Mayonesa")),
                Recipe(name = "Ensalada César", ingredients = listOf("Lechuga", "Pollo", "Croutons", "Queso parmesano", "Salsa César")),
                Recipe(name = "Tarta de Manzana", ingredients = listOf("Manzana", "Harina", "Azúcar", "Mantequilla", "Canela")),
                Recipe(name = "Wraps de Pollo", ingredients = listOf("Pollo", "Tortillas", "Lechuga", "Tomate", "Salsa")),
                Recipe(name = "Lemonade", ingredients = listOf("Limón", "Azúcar", "Agua", "Hielo"))
            )),
            Subcategory(name = "Fiesta de Año Nuevo", recipes = listOf(
                Recipe(name = "Cocteles Variados", ingredients = listOf("Vodka", "Ron", "Gin", "Jugo de frutas", "Soda")),
                Recipe(name = "Canapés", ingredients = listOf("Pan", "Queso", "Jamon", "Aceitunas", "Pimientos")),
                Recipe(name = "Mini Quiches", ingredients = listOf("Masa", "Huevo", "Queso", "Verduras", "Bacon")),
                Recipe(name = "Tarta de Santiago", ingredients = listOf("Almendra", "Huevo", "Azúcar", "Harina", "Limón")),
                Recipe(name = "Fondue de Chocolate", ingredients = listOf("Chocolate", "Crema", "Frutas", "Galletas"))
            )),
        )

        recyclerSubcategoryAdapter.addDataToList(listaDatos)

        // Configuración del primer RecyclerView
        binding.recyclerViewSubcategories.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerSubcategoryAdapter
        }

        binding.categoryIcon.setOnClickListener {
            val intentCategory = Intent(this, CategoriesActivity::class.java)
            startActivity(intentCategory)
        }

    }
}