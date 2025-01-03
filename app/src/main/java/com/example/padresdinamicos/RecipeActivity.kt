package com.example.padresdinamicos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.padresdinamicos.MenuActivity.Companion.ID_PASO_RECETA
import com.example.padresdinamicos.adapters.RecyclerIngredientsAdapter
import com.example.padresdinamicos.adapters.RecyclerStepAdapter
import com.example.padresdinamicos.databinding.ActivityRecipeBinding
import com.example.padresdinamicos.dataclasses.Ingredient
import com.example.padresdinamicos.dataclasses.Recipe
import com.example.padresdinamicos.dataclasses.Step
import com.example.padresdinamicos.room.RecipeDatabase
import com.example.padresdinamicos.room.RecipeDatabase.Companion.getDatabase
import kotlinx.coroutines.launch
import kotlin.random.Random

class RecipeActivity : BaseActivity() {
    private lateinit var  binding: ActivityRecipeBinding
    private val recyclerIngredientAdapter by lazy { RecyclerIngredientsAdapter() }
    private val recyclerStepAdapter by lazy { RecyclerStepAdapter() }
    private var currentRecipe: Recipe? = null
    private lateinit var dbAccess: RecipeDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        setUpRecyclerView()
        dbAccess = getDatabase(this)
        toggleFavorite2()
        binding.buttonBack.setOnClickListener {
            finish()
        }
        binding.buttonFavorite.setOnClickListener {
            toggleFavorite()
        }


        lifecycleScope.launch {
            if (dbAccess.stepDao().obtenerTodosLosPasos().isEmpty()){
                val steps = listOf(
                    Step(number = "1", name = "Preparar los ingredientes", description = "Reúne todos los ingredientes necesarios para la receta: pasta, panceta o bacon, huevos, queso parmesano o pecorino, sal y pimienta. Corta la panceta en cubos pequeños y ralla el queso para que esté listo al momento de cocinar. Además, separa las claras de los huevos, ya que solo necesitarás las yemas para esta receta.", recipe = "Pasta Carbonara"),
                    Step(number = "2", name = "Cocinar la pasta", description = "Hierve una olla grande con agua y añade una buena cantidad de sal. Una vez que el agua esté en ebullición, agrega la pasta y cocínala al dente siguiendo las instrucciones del paquete. Mientras la pasta se cocina, guarda una taza del agua de cocción para usarla después en la salsa.", recipe = "Pasta Carbonara"),
                    Step(number = "3", name = "Preparar la salsa", description = "En un tazón grande, mezcla las yemas de huevo con el queso rallado, batiendo hasta obtener una mezcla cremosa y uniforme. Ajusta con una pizca de pimienta negra molida. Este será el corazón de tu salsa carbonara, que se cocinará con el calor de la pasta caliente.", recipe = "Pasta Carbonara"),
                    Step(number = "4", name = "Combinar y servir", description = "En una sartén grande, cocina la panceta a fuego medio hasta que esté dorada y crujiente. Añade la pasta recién escurrida directamente a la sartén y remueve para mezclar. Retira del fuego y añade la mezcla de huevo y queso, revolviendo rápidamente para que la salsa cubra la pasta sin que se cuajen los huevos. Si la mezcla está seca, añade un poco del agua de cocción reservada hasta alcanzar la consistencia deseada. Sirve de inmediato y espolvorea más queso y pimienta al gusto.", recipe = "Pasta Carbonara"),
                    Step(number = "1", name = "Preparar la masa", description = "Mezcla harina, agua tibia, levadura seca, sal y aceite de oliva en un bol grande. Amasa durante 10 minutos hasta obtener una masa elástica. Déjala reposar cubierta con un paño húmedo en un lugar cálido durante al menos 1 hora o hasta que doble su tamaño.", recipe = "Pizza Margarita"),
                    Step(number = "2", name = "Preparar la salsa", description = "Tritura tomates maduros junto con una pizca de sal, aceite de oliva y hojas frescas de albahaca. Cocina la mezcla a fuego lento durante 10-15 minutos para concentrar los sabores.", recipe = "Pizza Margarita"),
                    Step(number = "3", name = "Montar la pizza", description = "Estira la masa sobre una superficie enharinada y colócala en una bandeja para pizza. Cubre con la salsa de tomate preparada, rodajas de mozzarella fresca y algunas hojas de albahaca.", recipe = "Pizza Margarita"),
                    Step(number = "4", name = "Hornear y servir", description = "Hornea en un horno precalentado a 250°C durante 10-12 minutos o hasta que los bordes estén dorados y el queso burbujeante. Retira del horno y sirve inmediatamente con un chorrito de aceite de oliva extra virgen.", recipe = "Pizza Margarita"),
                    Step(number = "1", name = "Preparar la carne y la salsa", description = "En una sartén grande, cocina carne molida junto con cebolla picada, ajo y zanahorias ralladas. Agrega tomate triturado, pasta de tomate y condimentos como albahaca, orégano, sal y pimienta. Cocina a fuego lento durante 30 minutos para que los sabores se integren.", recipe = "Lasagna"),
                    Step(number = "2", name = "Preparar la bechamel", description = "Derrite mantequilla en una cacerola y añade harina. Cocina la mezcla durante 2 minutos y luego incorpora lentamente leche tibia mientras remueves. Cocina hasta obtener una salsa espesa y sin grumos. Ajusta con sal, pimienta y nuez moscada al gusto.", recipe = "Lasagna"),
                    Step(number = "3", name = "Montar las capas", description = "En un molde para horno, coloca una capa de salsa de carne, seguida de láminas de pasta, una capa de bechamel y una capa de queso rallado. Repite el proceso hasta llenar el molde, terminando con bechamel y queso.", recipe = "Lasagna"),
                    Step(number = "4", name = "Hornear y servir", description = "Hornea la lasagna en un horno precalentado a 180°C durante 40-50 minutos o hasta que la parte superior esté dorada y burbujeante. Deja reposar 10 minutos antes de cortar y servir.", recipe = "Lasagna"),
                    Step(number = "1", name = "Preparar el caldo", description = "Calienta caldo de pollo o verduras en una olla a fuego lento, manteniéndolo caliente durante todo el proceso de cocción del risotto.", recipe = "Risotto al Pesto"),
                    Step(number = "2", name = "Cocinar el arroz", description = "En una sartén grande, sofríe cebolla picada en mantequilla y aceite de oliva hasta que esté transparente. Agrega arroz arborio y remueve durante 2 minutos para que se impregne del aceite.", recipe = "Risotto al Pesto"),
                    Step(number = "3", name = "Incorporar el caldo", description = "Añade caldo caliente al arroz, un cucharón a la vez, removiendo constantemente y dejando que el arroz absorba el líquido antes de añadir más. Repite el proceso durante 20 minutos hasta que el arroz esté cremoso y al dente.", recipe = "Risotto al Pesto"),
                    Step(number = "4", name = "Incorporar el pesto y servir", description = "Una vez que el arroz esté cocido, retíralo del fuego y mezcla con una generosa cantidad de pesto casero o comprado. Ajusta con queso parmesano rallado, sal y pimienta. Sirve caliente.", recipe = "Risotto al Pesto"),
                    Step(number = "1", name = "Preparar el café", description = "Prepara una taza grande de café espresso fuerte y deja que se enfríe. Agrega un poco de licor como amaretto o marsala si lo deseas.", recipe = "Tiramisu"),
                    Step(number = "2", name = "Hacer la crema", description = "Bate yemas de huevo con azúcar hasta obtener una mezcla cremosa y pálida. Incorpora mascarpone suavemente y mezcla hasta obtener una crema homogénea.", recipe = "Tiramisu"),
                    Step(number = "3", name = "Montar las capas", description = "Sumerge rápidamente los bizcochos de soletilla en el café enfriado y colócalos en una capa uniforme en un molde. Cubre con una capa de la crema de mascarpone y repite el proceso hasta terminar con crema.", recipe = "Tiramisu"),
                    Step(number = "4", name = "Refrigerar y servir", description = "Espolvorea cacao en polvo sobre la última capa de crema y refrigera el tiramisú durante al menos 4 horas (mejor si es toda la noche). Sirve frío y disfruta.", recipe = "Tiramisu"),
                    Step(number = "1", name = "Preparar la marinada", description = "En un tazón, mezcla achiote, jugo de naranja, vinagre de manzana, ajo, orégano, comino y una pizca de sal. Licúa todo hasta obtener una mezcla homogénea.", recipe = "Tacos al Pastor"),
                    Step(number = "2", name = "Marinar la carne", description = "Corta carne de cerdo en filetes delgados y colócala en la marinada. Asegúrate de que toda la carne esté cubierta. Deja reposar en el refrigerador por al menos 2 horas o toda la noche.", recipe = "Tacos al Pastor"),
                    Step(number = "3", name = "Cocinar la carne", description = "Cocina los filetes marinados en una parrilla o sartén caliente hasta que estén dorados. Corta la carne en trozos pequeños o tiras para los tacos.", recipe = "Tacos al Pastor"),
                    Step(number = "4", name = "Montar los tacos", description = "Calienta tortillas de maíz y rellénalas con la carne. Añade piña, cebolla picada, cilantro fresco y salsa al gusto. Sirve caliente.", recipe = "Tacos al Pastor"),
                    Step(number = "1", name = "Preparar la salsa verde", description = "Hierve tomates verdes, chiles serranos y ajo hasta que estén suaves. Licúa con cebolla, cilantro y sal al gusto. Cocina la mezcla en una sartén con un poco de aceite hasta que espese ligeramente.", recipe = "Enchiladas Verdes"),
                    Step(number = "2", name = "Preparar las tortillas", description = "Calienta tortillas de maíz en una sartén o pásalas ligeramente por aceite caliente para que sean más flexibles y no se rompan al enrollarlas.", recipe = "Enchiladas Verdes"),
                    Step(number = "3", name = "Rellenar y enrollar", description = "Rellena cada tortilla con pollo deshebrado, enróllalas y colócalas en un recipiente para hornear con la abertura hacia abajo.", recipe = "Enchiladas Verdes"),
                    Step(number = "4", name = "Hornear y servir", description = "Cubre las enchiladas con la salsa verde y espolvorea queso rallado. Hornea a 180°C durante 10-15 minutos o hasta que el queso se derrita. Sirve con crema, cebolla y cilantro al gusto.", recipe = "Enchiladas Verdes"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Corta aguacates maduros por la mitad, retira la semilla y saca la pulpa con una cuchara. Pica finamente jitomate, cebolla, chile serrano y cilantro fresco.", recipe = "Guacamole"),
                    Step(number = "2", name = "Machacar el aguacate", description = "Coloca la pulpa de aguacate en un tazón y machaca con un tenedor hasta obtener una textura cremosa pero con algunos trozos.", recipe = "Guacamole"),
                    Step(number = "3", name = "Mezclar los ingredientes", description = "Añade el jitomate, cebolla, chile y cilantro al aguacate. Exprime jugo de limón fresco y ajusta con sal al gusto. Mezcla suavemente para combinar.", recipe = "Guacamole"),
                    Step(number = "4", name = "Servir", description = "Sirve el guacamole en un tazón, acompañado de totopos de maíz o como guarnición para otros platillos mexicanos.", recipe = "Guacamole"),
                    Step(number = "1", name = "Preparar los chiles", description = "Asa los chiles poblanos directamente sobre la flama hasta que su piel esté quemada. Colócalos en una bolsa de plástico para sudarlos durante 10 minutos, luego retira la piel, abre con cuidado y retira las semillas.", recipe = "Chiles Rellenos"),
                    Step(number = "2", name = "Preparar el relleno", description = "Rellena los chiles con queso fresco, carne molida o picadillo, asegurándote de no romperlos. Usa palillos para cerrar la abertura si es necesario.", recipe = "Chiles Rellenos"),
                    Step(number = "3", name = "Empanizar", description = "Bate claras de huevo a punto de nieve y luego incorpora suavemente las yemas. Pasa los chiles por harina y luego por el huevo batido.", recipe = "Chiles Rellenos"),
                    Step(number = "4", name = "Freír y servir", description = "Fríe los chiles en aceite caliente hasta que estén dorados. Escurre en papel absorbente y sirve con salsa de jitomate casera.", recipe = "Chiles Rellenos"),
                    Step(number = "1", name = "Cocinar el maíz", description = "En una olla grande, cocina maíz pozolero precocido con agua, sal y una cabeza de ajo. Cocina a fuego medio hasta que los granos revienten y estén suaves.", recipe = "Pozole"),
                    Step(number = "2", name = "Preparar el caldo", description = "En una cacerola, hierve carne de cerdo (costillas o cabeza) con ajo, cebolla y sal. Una vez cocida, desmenuza la carne y reserva el caldo.", recipe = "Pozole"),
                    Step(number = "3", name = "Preparar la salsa", description = "Licúa chiles guajillo y ancho hidratados con ajo y especias. Cuela la mezcla y agrégala al caldo junto con el maíz. Cocina a fuego lento para integrar los sabores.", recipe = "Pozole"),
                    Step(number = "4", name = "Servir", description = "Sirve el pozole caliente con carne, rábanos, lechuga picada, cebolla, orégano y un toque de limón. Acompaña con tostadas al gusto.", recipe = "Pozole"),
                    Step(number = "1", name = "Preparar el arroz", description = "Lava el arroz para sushi varias veces hasta que el agua salga clara. Cocina el arroz en una olla con agua siguiendo las proporciones indicadas. Mezcla el arroz cocido con vinagre de arroz, azúcar y sal mientras aún está caliente, y deja enfriar.", recipe = "Sushi (Maki Rolls)"),
                    Step(number = "2", name = "Preparar los ingredientes", description = "Corta en tiras finas los rellenos que prefieras, como salmón, atún, pepino, aguacate o zanahoria. Asegúrate de que todo esté listo para ensamblar.", recipe = "Sushi (Maki Rolls)"),
                    Step(number = "3", name = "Enrollar los makis", description = "Coloca una hoja de alga nori sobre una esterilla de bambú. Extiende una capa delgada de arroz sobre el nori, dejando un borde libre. Añade los ingredientes en el centro y enrolla firmemente usando la esterilla.", recipe = "Sushi (Maki Rolls)"),
                    Step(number = "4", name = "Cortar y servir", description = "Usa un cuchillo afilado mojado para cortar el rollo en piezas de igual tamaño. Sirve con salsa de soya, wasabi y jengibre encurtido al gusto.", recipe = "Sushi (Maki Rolls)"),
                    Step(number = "1", name = "Preparar el caldo", description = "Hierve huesos de cerdo o pollo con cebolla, ajo, jengibre y algas kombu durante varias horas hasta obtener un caldo concentrado y sabroso. Cuela y reserva.", recipe = "Ramen"),
                    Step(number = "2", name = "Cocinar los fideos", description = "Cocina los fideos ramen en agua hirviendo según las instrucciones del paquete. Escúrrelos y resérvalos para servir.", recipe = "Ramen"),
                    Step(number = "3", name = "Preparar los toppings", description = "Corta chashu (cerdo marinado), huevos cocidos marinados en soya, cebolletas picadas, brotes de bambú, y prepara algas nori.", recipe = "Ramen"),
                    Step(number = "4", name = "Montar y servir", description = "Coloca los fideos en un tazón, vierte el caldo caliente y añade los toppings. Ajusta con salsa de soya o miso según el tipo de ramen. Sirve inmediatamente.", recipe = "Ramen"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Corta vegetales (como zanahoria, camote y brócoli) y mariscos (como camarones) en porciones adecuadas para freír.", recipe = "Tempura"),
                    Step(number = "2", name = "Hacer la masa", description = "En un tazón, mezcla harina para tempura con agua helada y un poco de huevo batido. La masa debe ser ligera y fría para obtener un buen resultado.", recipe = "Tempura"),
                    Step(number = "3", name = "Freír los ingredientes", description = "Sumerge los ingredientes en la masa y fríelos en aceite caliente (180°C) hasta que estén dorados y crujientes. Retira el exceso de aceite colocando las piezas sobre papel absorbente.", recipe = "Tempura"),
                    Step(number = "4", name = "Servir", description = "Sirve el tempura con salsa tentsuyu (mezcla de dashi, soya y mirin) para mojar. Acompaña con arroz blanco o como guarnición.", recipe = "Tempura"),
                    Step(number = "1", name = "Preparar la masa", description = "En un tazón grande, mezcla harina, huevo, agua y repollo finamente picado. Opcionalmente, añade cebollín, zanahoria rallada o trozos de mariscos.", recipe = "Okonomiyaki"),
                    Step(number = "2", name = "Cocinar el okonomiyaki", description = "Calienta una sartén antiadherente con un poco de aceite. Vierte la mezcla de masa y extiéndela en forma de círculo. Cocina a fuego medio durante 5-7 minutos por cada lado hasta que esté dorado y cocido.", recipe = "Okonomiyaki"),
                    Step(number = "3", name = "Añadir los toppings", description = "Cubre el okonomiyaki cocido con salsa para okonomiyaki, mayonesa japonesa, hojuelas de bonito (katsuobushi) y alga nori en polvo.", recipe = "Okonomiyaki"),
                    Step(number = "4", name = "Servir", description = "Sirve el okonomiyaki caliente, cortado en porciones individuales. Acompaña con más salsa y mayonesa si lo deseas.", recipe = "Okonomiyaki"),
                    Step(number = "1", name = "Preparar la masa", description = "En un tazón, bate huevos con azúcar hasta que estén espumosos. Añade harina, miel y una pizca de bicarbonato de sodio, mezclando hasta obtener una masa suave.", recipe = "Dorayaki"),
                    Step(number = "2", name = "Cocinar los panqueques", description = "Calienta una sartén antiadherente a fuego bajo y vierte pequeñas cantidades de masa para formar discos. Cocina hasta que aparezcan burbujas en la superficie y voltea. Cocina unos segundos más.", recipe = "Dorayaki"),
                    Step(number = "3", name = "Rellenar", description = "Deja enfriar los panqueques y luego únelos de dos en dos con una capa de pasta de frijol dulce (anko) entre ellos.", recipe = "Dorayaki"),
                    Step(number = "4", name = "Servir", description = "Sirve los dorayaki como postre o merienda. Acompáñalos con té verde para una experiencia japonesa auténtica.", recipe = "Dorayaki"),
                    Step(number = "1", name = "Preparar el arroz y las papas", description = "Cocina arroz blanco con agua y sal hasta que esté suave. Hierve las papas enteras hasta que estén cocidas, luego pélalas y resérvalas.", recipe = "Silpancho"),
                    Step(number = "2", name = "Preparar la carne", description = "Aplasta filetes de carne de res hasta que estén muy delgados. Sazona con sal y pimienta, pásalos por huevo batido y empanízalos con pan molido.", recipe = "Silpancho"),
                    Step(number = "3", name = "Freír y acompañar", description = "Fríe la carne empanizada en aceite caliente hasta que esté dorada. Fríe las papas hervidas en rodajas para darles un toque crujiente.", recipe = "Silpancho"),
                    Step(number = "4", name = "Montar y servir", description = "En un plato, coloca una base de arroz, una cama de papas, y encima la carne frita. Corona con huevo frito, tomate picado, cebolla y locoto. Sirve caliente.", recipe = "Silpancho"),
                    Step(number = "1", name = "Cocinar los ingredientes", description = "Hierve choclo, habas y papas en agua con sal hasta que estén cocidos pero firmes. Resérvalos.", recipe = "Plato Paceño"),
                    Step(number = "2", name = "Preparar el queso", description = "Fríe queso fresco en una sartén hasta que esté dorado por fuera y suave por dentro. Ten cuidado de no quemarlo.", recipe = "Plato Paceño"),
                    Step(number = "3", name = "Preparar la llajwa", description = "Licúa locoto, tomate, cebolla y sal hasta obtener una salsa espesa. Ajusta la sazón al gusto.", recipe = "Plato Paceño"),
                    Step(number = "4", name = "Servir", description = "Coloca en el plato una porción de choclo, habas, papas y el queso frito. Acompaña con la llajwa como salsa.", recipe = "Plato Paceño"),
                    Step(number = "1", name = "Preparar la carne", description = "Corta carne de charque o pollo en trozos pequeños y cocínalos en agua con sal hasta que estén suaves. Si usas charque, desmenúzalo.", recipe = "Majadito"),
                    Step(number = "2", name = "Cocinar el arroz", description = "En una olla grande, sofríe cebolla, tomate y pimentón picados en aceite. Añade el arroz, mezcla bien, y agrega agua o caldo caliente.", recipe = "Majadito"),
                    Step(number = "3", name = "Combinar los ingredientes", description = "Incorpora la carne cocida al arroz junto con plátano frito en rodajas. Cocina a fuego lento hasta que el arroz esté suave y haya absorbido todo el líquido.", recipe = "Majadito"),
                    Step(number = "4", name = "Servir", description = "Sirve el majadito caliente, acompañado de huevo frito y plátano frito adicional si lo deseas.", recipe = "Majadito"),
                    Step(number = "1", name = "Preparar la base", description = "Sofríe cebolla, ajo y zanahoria rallada en aceite. Añade maní molido y cocina hasta que el aroma sea intenso.", recipe = "Sopa de Maní"),
                    Step(number = "2", name = "Hacer el caldo", description = "Agrega agua o caldo de res a la base de maní y lleva a ebullición. Añade carne de res cortada en trozos y cocina a fuego lento hasta que esté suave.", recipe = "Sopa de Maní"),
                    Step(number = "3", name = "Añadir verduras y arroz", description = "Incorpora papas cortadas, arvejas y arroz. Cocina hasta que todo esté tierno y la sopa tenga una consistencia espesa.", recipe = "Sopa de Maní"),
                    Step(number = "4", name = "Servir", description = "Sirve la sopa caliente, decorada con papas fritas en tiras y perejil fresco picado.", recipe = "Sopa de Maní"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Corta carne de res en tiras y sazónala con sal, pimienta y ajo. Corta papas en bastones y fríelas hasta que estén doradas.", recipe = "Pique a lo Macho"),
                    Step(number = "2", name = "Cocinar la carne", description = "Sofríe la carne en una sartén caliente con cebolla, tomate y locoto en tiras. Cocina hasta que los vegetales estén suaves y la carne bien dorada.", recipe = "Pique a lo Macho"),
                    Step(number = "3", name = "Preparar la base", description = "Coloca una base de papas fritas en el plato y cúbrelas con la mezcla de carne y vegetales. Asegúrate de que todo esté bien caliente.", recipe = "Pique a lo Macho"),
                    Step(number = "4", name = "Servir", description = "Decora con huevo cocido o frito y rodajas de salchicha frita. Sirve con llajwa para un toque picante adicional.", recipe = "Pique a lo Macho"),
                    Step(number = "1", name = "Preparar la masa", description = "En un tazón, mezcla harina, mantequilla fría y un poco de agua. Amasa hasta formar una masa suave. Deja reposar en la nevera durante 30 minutos.", recipe = "Quiche Lorraine"),
                    Step(number = "2", name = "Cocinar el bacon", description = "Fría el bacon en una sartén hasta que esté crujiente. Corta en trozos pequeños y resérvalos.", recipe = "Quiche Lorraine"),
                    Step(number = "3", name = "Preparar el relleno", description = "En un tazón, bate huevos y crema de leche. Añade sal, pimienta y nuez moscada al gusto. Incorpora el bacon y el queso rallado al relleno.", recipe = "Quiche Lorraine"),
                    Step(number = "4", name = "Hornear y servir", description = "Coloca la masa en un molde para tarta, vierte el relleno y hornea a 180°C durante 35-40 minutos hasta que esté dorado. Sirve caliente o a temperatura ambiente.", recipe = "Quiche Lorraine"),
                    Step(number = "1", name = "Preparar las verduras", description = "Corta berenjenas, calabacines, pimientos y tomates en rodajas finas. Pica cebolla y ajo.", recipe = "Ratatouille"),
                    Step(number = "2", name = "Sofreír las verduras", description = "En una sartén grande, sofríe la cebolla y el ajo picado en aceite de oliva. Añade las verduras y cocina hasta que se ablanden, pero sin que se deshagan.", recipe = "Ratatouille"),
                    Step(number = "3", name = "Cocinar a fuego lento", description = "Añade tomates picados y un poco de hierbas provenzales. Cocina todo a fuego bajo durante 20-30 minutos, removiendo ocasionalmente.", recipe = "Ratatouille"),
                    Step(number = "4", name = "Servir", description = "Sirve caliente como acompañamiento o plato principal. Decora con hojas de albahaca fresca o un toque de aceite de oliva.", recipe = "Ratatouille"),
                    Step(number = "1", name = "Preparar las cebollas", description = "Corta las cebollas en rodajas finas. En una olla grande, derrite mantequilla y sofríe las cebollas a fuego medio-bajo hasta que estén caramelizadas, aproximadamente 30-40 minutos.", recipe = "Soupe à l'Oignon"),
                    Step(number = "2", name = "Añadir el caldo", description = "Agrega caldo de res caliente y vino blanco a las cebollas caramelizadas. Sazona con sal, pimienta y hierbas como tomillo. Cocina a fuego lento durante 20-30 minutos.", recipe = "Soupe à l'Oignon"),
                    Step(number = "3", name = "Preparar el pan", description = "Corta pan de baguette en rebanadas y tuéstalas en el horno hasta que estén crujientes. Ralla queso Gruyère.", recipe = "Soupe à l'Oignon"),
                    Step(number = "4", name = "Servir", description = "Coloca las rebanadas de pan tostado en tazones individuales, vierte la sopa caliente sobre el pan y cubre con queso. Gratina en el horno hasta que el queso se derrita y burbujee.", recipe = "Soupe à l'Oignon"),
                    Step(number = "1", name = "Preparar la masa", description = "Mezcla harina, agua, mantequilla derretida, azúcar, sal y levadura para formar una masa. Amasa bien y deja reposar durante 1 hora.", recipe = "Croissant"),
                    Step(number = "2", name = "Incorporar la mantequilla", description = "Estira la masa en un rectángulo grande y coloca mantequilla fría en el centro. Dobla la masa sobre la mantequilla, sellando los bordes. Refrigera la masa durante 30 minutos.", recipe = "Croissant"),
                    Step(number = "3", name = "Formar los croissants", description = "Estira la masa en una lámina delgada y córtala en triángulos. Enrolla cada triángulo desde la base hasta la punta para formar los croissants.", recipe = "Croissant"),
                    Step(number = "4", name = "Hornear", description = "Coloca los croissants en una bandeja de horno y deja reposar durante 30 minutos. Hornea a 200°C durante 12-15 minutos o hasta que estén dorados y crujientes. Sirve calientes.", recipe = "Croissant"),
                    Step(number = "1", name = "Preparar la crema", description = "En un tazón, bate yemas de huevo con azúcar hasta que estén espumosas. Calienta crema de leche con vainilla hasta que esté casi hirviendo, luego mezcla con las yemas batidas.", recipe = "Crème Brûlée"),
                    Step(number = "2", name = "Cocinar al baño maría", description = "Vierte la mezcla en ramequines y colócalos en una bandeja para hornear con agua caliente. Hornea a 150°C durante 40-45 minutos hasta que la crema esté cuajada.", recipe = "Crème Brûlée"),
                    Step(number = "3", name = "Enfriar", description = "Deja enfriar los ramequines a temperatura ambiente y luego refrigéralos durante al menos 2 horas para que la crema se asiente.", recipe = "Crème Brûlée"),
                    Step(number = "4", name = "Caramelizar", description = "Antes de servir, espolvorea azúcar sobre la crema y, con un soplete de cocina, carameliza la capa superior hasta que quede dorada y crujiente. Sirve inmediatamente.", recipe = "Crème Brûlée"),
                    Step(number = "1", name = "Preparar la mezcla", description = "En un tazón grande, mezcla harina, azúcar, polvo de hornear, sal y leche. Añade un huevo y un poco de mantequilla derretida y mezcla hasta obtener una masa homogénea.", recipe = "Pancakes"),
                    Step(number = "2", name = "Cocinar los pancakes", description = "Calienta una sartén a fuego medio y unta ligeramente con mantequilla. Vierte porciones de la mezcla en la sartén y cocina durante 2-3 minutos por cada lado hasta que estén dorados.", recipe = "Pancakes"),
                    Step(number = "3", name = "Preparar los acompañamientos", description = "Corta frutas como fresas, plátanos y arándanos. Prepara miel, sirope de arce o crema batida para acompañar.", recipe = "Pancakes"),
                    Step(number = "4", name = "Servir", description = "Apila los pancakes en un plato, añade las frutas y acompaña con sirope de arce, miel o crema batida. Sirve caliente.", recipe = "Pancakes"),
                    Step(number = "1", name = "Preparar las frutas", description = "Corta frutas frescas como plátano, fresas y mango en trozos pequeños. Puedes congelar las frutas para un resultado más espeso.", recipe = "Smoothie Bowl"),
                    Step(number = "2", name = "Hacer el batido", description = "En una licuadora, mezcla las frutas con yogur natural o leche de almendra hasta obtener una consistencia espesa. Agrega un poco de miel o agave al gusto.", recipe = "Smoothie Bowl"),
                    Step(number = "3", name = "Preparar los toppings", description = "Corta más frutas frescas, añade granola, semillas de chía, nueces o coco rallado para decorar.", recipe = "Smoothie Bowl"),
                    Step(number = "4", name = "Servir", description = "Vierte el batido espeso en un tazón y agrega los toppings encima. Disfruta inmediatamente como desayuno o merienda.", recipe = "Smoothie Bowl"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Hierve agua con vinagre para escalfar los huevos. Tosta los muffins ingleses y, si lo prefieres, fríe unas rebanadas de jamón o bacon.", recipe = "Huevos Benedictinos"),
                    Step(number = "2", name = "Escalfar los huevos", description = "Rompe un huevo en un tazón pequeño y, con cuidado, vierte en el agua hirviendo. Cocina durante 3-4 minutos, luego retira el huevo con una espumadera.", recipe = "Huevos Benedictinos"),
                    Step(number = "3", name = "Preparar la salsa holandesa", description = "Bate yemas de huevo con mantequilla derretida, jugo de limón, sal y pimienta hasta que espese y tenga una textura cremosa.", recipe = "Huevos Benedictinos"),
                    Step(number = "4", name = "Montar y servir", description = "Coloca una mitad de muffin en un plato, agrega una rebanada de jamón o bacon, pon el huevo escalfado encima y cubre con la salsa holandesa. Sirve inmediatamente.", recipe = "Huevos Benedictinos"),
                    Step(number = "1", name = "Cocinar la avena", description = "En una cacerola, hierve leche (o agua) con avena. Cocina a fuego medio-bajo, removiendo ocasionalmente hasta que la avena esté suave y tenga una textura cremosa.", recipe = "Avena con Frutas"),
                    Step(number = "2", name = "Preparar las frutas", description = "Corta frutas frescas como plátano, fresas, arándanos y manzanas en trozos pequeños para decorar la avena.", recipe = "Avena con Frutas"),
                    Step(number = "3", name = "Añadir los ingredientes opcionales", description = "Añade miel, canela, almendras o nueces a la avena para darle más sabor y textura.", recipe = "Avena con Frutas"),
                    Step(number = "4", name = "Servir", description = "Sirve la avena caliente en un tazón y decora con las frutas preparadas y tus toppings favoritos. Disfruta de un desayuno nutritivo.", recipe = "Avena con Frutas"),
                    Step(number = "1", name = "Tostar el pan", description = "Tuesta rebanadas de pan integral o de tu preferencia hasta que estén doradas y crujientes. Puedes hacerlo en una tostadora o en una sartén.", recipe = "Tostadas de Aguacate"),
                    Step(number = "2", name = "Preparar el aguacate", description = "Corta un aguacate por la mitad, quita el hueso y saca la pulpa. Tritura el aguacate con un tenedor hasta obtener un puré suave.", recipe = "Tostadas de Aguacate"),
                    Step(number = "3", name = "Condimentar el aguacate", description = "Agrega sal, pimienta, jugo de limón y un toque de aceite de oliva al aguacate triturado. Mezcla bien.", recipe = "Tostadas de Aguacate"),
                    Step(number = "4", name = "Montar y servir", description = "Coloca el aguacate preparado sobre las tostadas calientes. Añade ingredientes opcionales como tomate, huevo pochado, semillas o albahaca fresca. Sirve inmediatamente.", recipe = "Tostadas de Aguacate"),
                    Step(number = "1", name = "Preparar el pan", description = "Corta una baguette o pan italiano en rebanadas finas. Tuesta las rebanadas en una sartén o en el horno hasta que estén doradas y crujientes.", recipe = "Bruschettas"),
                    Step(number = "2", name = "Preparar los ingredientes para el topping", description = "Pica finamente tomates, ajo y albahaca. Mezcla con aceite de oliva, sal y pimienta al gusto.", recipe = "Bruschettas"),
                    Step(number = "3", name = "Montar las bruschettas", description = "Coloca una cucharada de la mezcla de tomate sobre cada rebanada de pan tostado. Si lo deseas, añade un poco de queso parmesano rallado por encima.", recipe = "Bruschettas"),
                    Step(number = "4", name = "Servir", description = "Sirve las bruschettas como aperitivo o entrada, decoradas con un poco más de albahaca fresca o un chorrito de aceite de oliva.", recipe = "Bruschettas"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Escurre y enjuaga los garbanzos. En un procesador de alimentos, mezcla los garbanzos con tahini, jugo de limón, ajo, aceite de oliva y sal hasta obtener una mezcla suave.", recipe = "Hummus con Pita"),
                    Step(number = "2", name = "Ajustar la consistencia", description = "Si la mezcla está muy espesa, agrega un poco de agua o más aceite de oliva para lograr una consistencia cremosa y suave.", recipe = "Hummus con Pita"),
                    Step(number = "3", name = "Preparar el pan pita", description = "Corta el pan pita en triángulos y tuéstalo en el horno o en una sartén hasta que esté crujiente.", recipe = "Hummus con Pita"),
                    Step(number = "4", name = "Servir", description = "Sirve el hummus en un tazón, espolvorea con pimentón o perejil fresco picado y acompaña con los triángulos de pan pita tostado.", recipe = "Hummus con Pita"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Pica finamente cebolla y ajo. Lava y corta los tomates en trozos grandes. Si prefieres, pela los tomates antes de cortarlos.", recipe = "Sopa de Tomate"),
                    Step(number = "2", name = "Cocinar los tomates", description = "En una olla grande, sofríe la cebolla y el ajo en aceite de oliva hasta que estén dorados. Agrega los tomates y cocina a fuego lento hasta que se ablanden.", recipe = "Sopa de Tomate"),
                    Step(number = "3", name = "Agregar caldo y cocinar", description = "Añade caldo de verduras o de pollo, sal, pimienta y un toque de azúcar. Cocina a fuego medio durante 20-30 minutos para que los sabores se mezclen.", recipe = "Sopa de Tomate"),
                    Step(number = "4", name = "Licuar y servir", description = "Licúa la sopa hasta obtener una textura suave y homogénea. Sirve caliente con un chorrito de crema y albahaca fresca o crujientes trozos de pan.", recipe = "Sopa de Tomate"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Pica finamente cebolla y ajo. Lava y corta los tomates en trozos grandes. Si prefieres, pela los tomates antes de cortarlos.", recipe = "Sopa de Tomate"),
                    Step(number = "2", name = "Cocinar los tomates", description = "En una olla grande, sofríe la cebolla y el ajo en aceite de oliva hasta que estén dorados. Agrega los tomates y cocina a fuego lento hasta que se ablanden.", recipe = "Sopa de Tomate"),
                    Step(number = "3", name = "Agregar caldo y cocinar", description = "Añade caldo de verduras o de pollo, sal, pimienta y un toque de azúcar. Cocina a fuego medio durante 20-30 minutos para que los sabores se mezclen.", recipe = "Sopa de Tomate"),
                    Step(number = "4", name = "Licuar y servir", description = "Licúa la sopa hasta obtener una textura suave y homogénea. Sirve caliente con un chorrito de crema y albahaca fresca o crujientes trozos de pan.", recipe = "Sopa de Tomate"),
                    Step(number = "1", name = "Preparar los calamares", description = "Limpia los calamares, retirando la piel, los tentáculos y la pluma. Corta los calamares en anillos o tiras, según prefieras.", recipe = "Calamares Fritos"),
                    Step(number = "2", name = "Empanizar los calamares", description = "En un tazón, mezcla harina, sal, pimienta y un poco de pimentón. Pasa los anillos de calamar por la mezcla de harina, cubriéndolos completamente.", recipe = "Calamares Fritos"),
                    Step(number = "3", name = "Freír los calamares", description = "Calienta aceite en una sartén profunda o freidora. Fría los calamares en tandas, cocinándolos hasta que estén dorados y crujientes, alrededor de 2-3 minutos.", recipe = "Calamares Fritos"),
                    Step(number = "4", name = "Servir", description = "Coloca los calamares fritos sobre papel absorbente para eliminar el exceso de aceite. Sirve con rodajas de limón y una salsa de mayonesa o alioli.", recipe = "Calamares Fritos"),
                    Step(number = "1", name = "Preparar el cuscús", description = "Cocina el cuscús según las instrucciones del paquete. Generalmente, solo es necesario hidratarlo con agua caliente. Deja enfriar.", recipe = "Tabule"),
                    Step(number = "2", name = "Preparar las verduras", description = "Pica finamente tomates, pepino, cebolla y hierbas frescas como perejil y menta. Añade el jugo de un limón.", recipe = "Tabule"),
                    Step(number = "3", name = "Mezclar los ingredientes", description = "En un tazón grande, mezcla el cuscús enfriado con las verduras picadas. Añade aceite de oliva, sal y pimienta al gusto.", recipe = "Tabule"),
                    Step(number = "4", name = "Servir", description = "Deja reposar el tabulé en la nevera durante al menos 30 minutos antes de servir para que los sabores se mezclen. Sirve frío como ensalada o acompañamiento.", recipe = "Tabule"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Corta el pollo en trozos pequeños y sazona con sal, pimienta y curry en polvo. Pica cebolla, ajo, jengibre y tomate.", recipe = "Pollo al Curry"),
                    Step(number = "2", name = "Cocinar el pollo", description = "En una sartén grande, calienta aceite y cocina el pollo hasta que esté dorado y cocido por completo. Retira y reserva.", recipe = "Pollo al Curry"),
                    Step(number = "3", name = "Preparar la salsa de curry", description = "En la misma sartén, sofríe la cebolla, el ajo y el jengibre hasta que estén suaves. Agrega el tomate picado, curry en polvo, leche de coco y un poco de caldo.", recipe = "Pollo al Curry"),
                    Step(number = "4", name = "Unir todo y cocinar", description = "Vuelve a añadir el pollo a la sartén y cocina todo junto durante 10-15 minutos hasta que los sabores se integren. Sirve con arroz o pan naan.", recipe = "Pollo al Curry"),
                    Step(number = "1", name = "Preparar la salsa", description = "En una sartén grande, sofríe cebolla, ajo, zanahoria y apio picados hasta que estén tiernos. Añade carne molida y cocina hasta que esté dorada.", recipe = "Espagueti Boloñesa"),
                    Step(number = "2", name = "Cocinar la salsa", description = "Agrega tomate triturado, pasta de tomate, vino tinto y hierbas italianas como orégano y albahaca. Cocina a fuego lento durante 30-40 minutos.", recipe = "Espagueti Boloñesa"),
                    Step(number = "3", name = "Cocinar los espaguetis", description = "Mientras se cocina la salsa, hierve agua con sal en una olla grande. Cocina los espaguetis según las indicaciones del paquete. Escurre y reserva.", recipe = "Espagueti Boloñesa"),
                    Step(number = "4", name = "Servir", description = "Sirve los espaguetis en platos individuales, cubre con la salsa boloñesa caliente y espolvorea queso parmesano rallado por encima.", recipe = "Espagueti Boloñesa"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Corta la carne de res en trozos grandes. Pica cebolla, ajo, zanahorias, papas y apio. Sazona la carne con sal y pimienta.", recipe = "Estofado de Res"),
                    Step(number = "2", name = "Sellar la carne", description = "En una olla grande, calienta aceite y dora los trozos de carne por todos lados. Retira la carne y reserva.", recipe = "Estofado de Res"),
                    Step(number = "3", name = "Cocinar las verduras", description = "En la misma olla, sofríe la cebolla, ajo, zanahorias, apio y papas. Añade caldo de res, hierbas aromáticas y vuelve a poner la carne en la olla.", recipe = "Estofado de Res"),
                    Step(number = "4", name = "Cocinar a fuego lento", description = "Cubre la olla y cocina a fuego lento durante 2-3 horas hasta que la carne esté tierna y los sabores se hayan mezclado. Sirve caliente.", recipe = "Estofado de Res"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Corta pollo, conejo, mariscos y verduras (pimientos, cebolla, tomate) en trozos pequeños. Lava el arroz y ten caldo de pollo o mariscos listo.", recipe = "Paella"),
                    Step(number = "2", name = "Sofreír la carne y verduras", description = "En una paellera, calienta aceite y sofríe el pollo y el conejo hasta que estén dorados. Añade las verduras y cocina por unos minutos.", recipe = "Paella"),
                    Step(number = "3", name = "Cocinar el arroz", description = "Agrega el arroz a la paellera y sofríe por 1-2 minutos. Añade caldo caliente y azafrán, y cocina a fuego medio durante 20-25 minutos hasta que el arroz esté cocido.", recipe = "Paella"),
                    Step(number = "4", name = "Agregar los mariscos", description = "Cuando el arroz esté casi listo, agrega los mariscos y cocina durante otros 5-7 minutos. Sirve la paella caliente y disfruta.", recipe = "Paella"),
                    Step(number = "1", name = "Preparar el salmón", description = "Sazona los filetes de salmón con sal, pimienta y jugo de limón. Deja reposar durante unos minutos para que absorban los sabores.", recipe = "Salmón a la Plancha"),
                    Step(number = "2", name = "Cocinar el salmón", description = "Calienta una sartén con un poco de aceite de oliva. Cocina los filetes de salmón durante 3-4 minutos por cada lado, hasta que estén dorados y cocidos por dentro.", recipe = "Salmón a la Plancha"),
                    Step(number = "3", name = "Preparar los acompañamientos", description = "Mientras se cocina el salmón, prepara una ensalada fresca o unas papas al vapor como acompañamiento.", recipe = "Salmón a la Plancha"),
                    Step(number = "4", name = "Servir", description = "Sirve el salmón a la plancha con el acompañamiento elegido, adorna con rodajas de limón y hierbas frescas.", recipe = "Salmón a la Plancha"),
                    Step(number = "1", name = "Preparar la base", description = "Tritura galletas de graham o galletas digestivas y mezcla con mantequilla derretida. Presiona esta mezcla en el fondo de un molde para cheesecake.", recipe = "Cheesecake"),
                    Step(number = "2", name = "Preparar el relleno", description = "Bate queso crema, azúcar, huevos, crema agria, esencia de vainilla y un poco de harina hasta obtener una mezcla suave y cremosa.", recipe = "Cheesecake"),
                    Step(number = "3", name = "Hornear el cheesecake", description = "Vierte el relleno sobre la base de galletas y hornea a 160°C durante 50-60 minutos o hasta que el centro esté firme pero ligeramente tembloroso.", recipe = "Cheesecake"),
                    Step(number = "4", name = "Enfriar y servir", description = "Deja enfriar el cheesecake en el molde a temperatura ambiente, luego refrigéralo durante al menos 4 horas antes de desmoldarlo. Sirve con frutas o mermelada de tu elección.", recipe = "Cheesecake"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Derrite mantequilla y mezcla con azúcar, huevos y esencia de vainilla. Agrega harina, cacao en polvo y sal, y mezcla hasta obtener una masa homogénea.", recipe = "Brownies"),
                    Step(number = "2", name = "Hornear los brownies", description = "Vierte la masa en un molde engrasado y hornea a 180°C durante 25-30 minutos. Los brownies deben estar cocidos por fuera pero ligeramente húmedos por dentro.", recipe = "Brownies"),
                    Step(number = "3", name = "Dejar enfriar", description = "Deja enfriar los brownies en el molde antes de cortarlos en cuadros. Si lo deseas, añade nueces o chocolate extra en la masa antes de hornear.", recipe = "Brownies"),
                    Step(number = "4", name = "Servir", description = "Sirve los brownies con un poco de helado de vainilla o crema batida. Disfruta de un delicioso postre.", recipe = "Brownies"),
                    Step(number = "1", name = "Cocer el arroz", description = "Lava el arroz y ponlo a cocer en una olla con agua. Cocina durante 15 minutos o hasta que el arroz esté parcialmente cocido.", recipe = "Arroz con Leche"),
                    Step(number = "2", name = "Preparar la mezcla de leche", description = "Añade leche, azúcar, canela y cáscara de limón al arroz. Cocina a fuego lento mientras remueves para que el arroz absorba la leche.", recipe = "Arroz con Leche"),
                    Step(number = "3", name = "Cocinar hasta obtener la consistencia deseada", description = "Continúa cocinando a fuego lento durante 20-30 minutos hasta que el arroz esté completamente cocido y la mezcla tenga una textura cremosa.", recipe = "Arroz con Leche"),
                    Step(number = "4", name = "Servir", description = "Sirve el arroz con leche caliente o frío, espolvoreado con canela en polvo. Puedes decorarlo con pasas si lo deseas.", recipe = "Arroz con Leche"),
                    Step(number = "1", name = "Preparar el caramelo", description = "En una sartén, calienta azúcar a fuego medio hasta que se derrita y se convierta en un caramelo dorado. Vierte el caramelo en el fondo de un molde para flan.", recipe = "Flan"),
                    Step(number = "2", name = "Preparar la mezcla de flan", description = "Bate huevos, leche condensada, leche entera y esencia de vainilla. Mezcla bien hasta que quede suave.", recipe = "Flan"),
                    Step(number = "3", name = "Cocinar al baño maría", description = "Vierte la mezcla de flan sobre el caramelo en el molde y cocina al baño maría en el horno a 160°C durante 50-60 minutos hasta que esté firme.", recipe = "Flan"),
                    Step(number = "4", name = "Enfriar y servir", description = "Deja enfriar el flan en el molde y refrigéralo durante 2-3 horas. Desmóldalo con cuidado y sirve frío.", recipe = "Flan"),
                    Step(number = "1", name = "Preparar la masa", description = "En una olla, hierve agua con mantequilla, azúcar y sal. Agrega la harina de golpe, mezcla bien y cocina durante unos minutos hasta que la masa se despegue de los bordes.", recipe = "Churros"),
                    Step(number = "2", name = "Formar los churros", description = "Coloca la masa en una manga pastelera con boquilla de estrella. Calienta aceite en una sartén profunda y forma los churros presionando la manga.", recipe = "Churros"),
                    Step(number = "3", name = "Freír los churros", description = "Fría los churros hasta que estén dorados y crujientes. Retíralos y escúrrelos en papel absorbente.", recipe = "Churros"),
                    Step(number = "4", name = "Servir", description = "Espolvorea los churros con azúcar y canela. Sirve con chocolate caliente para mojar.", recipe = "Churros"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Lava las hojas de menta, lima y hielo. Corta la lima en rodajas finas.", recipe = "Mojito"),
                    Step(number = "2", name = "Mezclar los ingredientes", description = "En un vaso, machaca las hojas de menta con azúcar. Añade hielo, ron blanco y jugo de lima.", recipe = "Mojito"),
                    Step(number = "3", name = "Agregar agua con gas", description = "Rellena el vaso con agua con gas y revuelve suavemente para mezclar todos los ingredientes.", recipe = "Mojito"),
                    Step(number = "4", name = "Servir", description = "Adorna con una rodaja de lima y hojas de menta fresca. Sirve inmediatamente.", recipe = "Mojito"),
                    Step(number = "1", name = "Preparar la sal", description = "Humedece el borde de un vaso con jugo de lima y sumerge en sal. Deja reposar el vaso.", recipe = "Margarita"),
                    Step(number = "2", name = "Mezclar la bebida", description = "En una coctelera, combina tequila, triple sec y jugo de lima. Agrega hielo y agita bien.", recipe = "Margarita"),
                    Step(number = "3", name = "Servir", description = "Coloca la mezcla en el vaso con borde de sal. Adorna con una rodaja de lima o una cereza.", recipe = "Margarita"),
                    Step(number = "4", name = "Disfrutar", description = "Sirve la Margarita fría y disfruta del balance entre el sabor fuerte del tequila y el refrescante jugo de lima.", recipe = "Margarita"),
                    Step(number = "1", name = "Calentar el agua", description = "Hierve agua y deja enfriar un poco hasta que alcance una temperatura adecuada para infusionar el té matcha.", recipe = "Té Matcha"),
                    Step(number = "2", name = "Mezclar el matcha", description = "En un tazón, tamiza el té matcha y añade agua caliente. Mezcla con un batidor de bambú hasta obtener una espuma suave.", recipe = "Té Matcha"),
                    Step(number = "3", name = "Servir", description = "Vierte el té matcha en una taza. Puedes agregar un poco de leche o endulzante si lo deseas.", recipe = "Té Matcha"),
                    Step(number = "4", name = "Disfrutar", description = "Disfruta del sabor suave y terroso del té matcha, ideal para un momento de relajación.", recipe = "Té Matcha"),
                    Step(number = "1", name = "Preparar las fresas", description = "Lava y quita las hojas de las fresas frescas. Colócalas en una licuadora con yogurt natural y leche.", recipe = "Batido de Fresa"),
                    Step(number = "2", name = "Licuar", description = "Agrega hielo y miel si lo deseas. Licua todo hasta obtener una mezcla suave y espumosa.", recipe = "Batido de Fresa"),
                    Step(number = "3", name = "Servir", description = "Sirve el batido en un vaso y decora con una fresa fresca en la orilla del vaso.", recipe = "Batido de Fresa"),
                    Step(number = "4", name = "Disfrutar", description = "Disfruta de un batido refrescante y lleno de sabor natural de fresa.", recipe = "Batido de Fresa"),
                    Step(number = "1", name = "Remojar los ingredientes", description = "Remoja arroz, canela, vainilla y azúcar en agua durante varias horas o toda la noche.", recipe = "Horchata"),
                    Step(number = "2", name = "Licuar", description = "Licúa todo hasta obtener una mezcla suave. Cuela el líquido para eliminar cualquier residuo sólido.", recipe = "Horchata"),
                    Step(number = "3", name = "Refriar", description = "Refrigera la horchata durante al menos 2 horas antes de servir para que esté bien fría.", recipe = "Horchata"),
                    Step(number = "4", name = "Servir", description = "Sirve en vasos con hielo y canela en polvo por encima. Disfruta de la bebida refrescante.", recipe = "Horchata"),
                    Step(number = "1", name = "Cortar los ingredientes", description = "Corta tomates maduros, queso mozzarella y hojas de albahaca fresca en rodajas.", recipe = "Ensalada Caprese"),
                    Step(number = "2", name = "Preparar el aderezo", description = "En un tazón, mezcla aceite de oliva, sal, pimienta y un poco de vinagre balsámico.", recipe = "Ensalada Caprese"),
                    Step(number = "3", name = "Mezclar y servir", description = "Coloca las rodajas de tomate, queso y albahaca en un plato. Rocía el aderezo por encima y sirve inmediatamente.", recipe = "Ensalada Caprese"),
                    Step(number = "4", name = "Disfrutar", description = "Una ensalada fresca y sabrosa, perfecta como entrada o acompañamiento ligero.", recipe = "Ensalada Caprese"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Corta cebolla, ajo, tomate y especias como cúrcuma, comino y coriandro. Escurre los garbanzos enlatados.", recipe = "Curry de Garbanzos"),
                    Step(number = "2", name = "Sofrito", description = "Sofríe la cebolla, ajo y tomate en una sartén con aceite. Añade las especias y cocina hasta que las verduras estén suaves.", recipe = "Curry de Garbanzos"),
                    Step(number = "3", name = "Incorporar garbanzos", description = "Agrega los garbanzos a la sartén, mezcla bien y cocina durante unos 10-15 minutos hasta que todo esté bien integrado.", recipe = "Curry de Garbanzos"),
                    Step(number = "4", name = "Servir", description = "Sirve el curry caliente, acompañado de arroz blanco o pan naan. Espolvorea cilantro fresco por encima.", recipe = "Curry de Garbanzos"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Cocina las lentejas y mezcla con cebolla, ajo, huevo, pan rallado y especias como comino y pimentón.", recipe = "Hamburguesa de Lentejas"),
                    Step(number = "2", name = "Formar las hamburguesas", description = "Forma las hamburguesas con la mezcla y refrigera por 30 minutos para que se asienten.", recipe = "Hamburguesa de Lentejas"),
                    Step(number = "3", name = "Cocinar las hamburguesas", description = "En una sartén, cocina las hamburguesas a fuego medio con un poco de aceite hasta que estén doradas por ambos lados.", recipe = "Hamburguesa de Lentejas"),
                    Step(number = "4", name = "Servir", description = "Sirve las hamburguesas en pan con lechuga, tomate, queso y salsas al gusto.", recipe = "Hamburguesa de Lentejas"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Lava y corta champiñones en rodajas finas. Pica cebolla y ajo.", recipe = "Risotto de Champiñones"),
                    Step(number = "2", name = "Sofrito", description = "Sofríe cebolla y ajo en una sartén con mantequilla. Añade los champiñones y cocina hasta que estén dorados.", recipe = "Risotto de Champiñones"),
                    Step(number = "3", name = "Cocinar el arroz", description = "Añade arroz al sartén y sofríe durante un par de minutos. Añade caldo de verduras poco a poco, cocinando hasta que el arroz esté cremoso.", recipe = "Risotto de Champiñones"),
                    Step(number = "4", name = "Servir", description = "Sirve el risotto caliente, espolvoreado con queso parmesano y perejil fresco picado.", recipe = "Risotto de Champiñones"),
                    Step(number = "1", name = "Preparar la soya", description = "Hidrata la soya texturizada en agua caliente durante 10 minutos. Luego, escúrrela y resérvala.", recipe = "Tacos de Soya"),
                    Step(number = "2", name = "Sofreír la soya", description = "En una sartén, sofríe cebolla, ajo y pimientos hasta que estén dorados. Agrega la soya y sazona con comino, paprika, sal y pimienta.", recipe = "Tacos de Soya"),
                    Step(number = "3", name = "Calentar las tortillas", description = "Calienta las tortillas de maíz en un sartén o comal hasta que estén suaves y calientes.", recipe = "Tacos de Soya"),
                    Step(number = "4", name = "Montar los tacos", description = "Coloca la mezcla de soya sobre las tortillas y añade toppings como cilantro fresco, cebolla morada y salsa al gusto.", recipe = "Tacos de Soya"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Lava y corta espinaca, pepino y manzana. Coloca todo en una licuadora junto con jugo de limón y agua.", recipe = "Smoothie Verde"),
                    Step(number = "2", name = "Licuar", description = "Licúa los ingredientes hasta obtener una mezcla suave. Si deseas, agrega un poco de miel o jengibre para un toque extra de sabor.", recipe = "Smoothie Verde"),
                    Step(number = "3", name = "Servir", description = "Sirve el smoothie verde en un vaso alto y decora con unas rodajas de pepino o manzana si lo prefieres.", recipe = "Smoothie Verde"),
                    Step(number = "4", name = "Disfrutar", description = "Bebe el smoothie verde inmediatamente para aprovechar todos los nutrientes y refrescarte.", recipe = "Smoothie Verde"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Lava y corta espinaca, pepino y manzana. Coloca todo en una licuadora junto con jugo de limón y agua.", recipe = "Smoothie Verde"),
                    Step(number = "2", name = "Licuar", description = "Licúa los ingredientes hasta obtener una mezcla suave. Si deseas, agrega un poco de miel o jengibre para un toque extra de sabor.", recipe = "Smoothie Verde"),
                    Step(number = "3", name = "Servir", description = "Sirve el smoothie verde en un vaso alto y decora con unas rodajas de pepino o manzana si lo prefieres.", recipe = "Smoothie Verde"),
                    Step(number = "4", name = "Disfrutar", description = "Bebe el smoothie verde inmediatamente para aprovechar todos los nutrientes y refrescarte.", recipe = "Smoothie Verde"),
                    Step(number = "1", name = "Cocinar la pasta", description = "Hierve agua con sal en una olla grande y cocina la pasta según las instrucciones del paquete. Escurre y reserva.", recipe = "Pasta con Salsa de Anacardos"),
                    Step(number = "2", name = "Preparar la salsa de anacardos", description = "Licúa anacardos remojados, ajo, agua, jugo de limón, levadura nutricional y sal hasta obtener una salsa cremosa.", recipe = "Pasta con Salsa de Anacardos"),
                    Step(number = "3", name = "Mezclar la pasta con la salsa", description = "Agrega la pasta cocida a la sartén con la salsa de anacardos. Cocina a fuego bajo, mezclando hasta que la pasta esté bien cubierta.", recipe = "Pasta con Salsa de Anacardos"),
                    Step(number = "4", name = "Servir", description = "Sirve la pasta en platos y adorna con albahaca fresca o nueces troceadas. Disfruta de este platillo cremoso y delicioso.", recipe = "Pasta con Salsa de Anacardos"),
                    Step(number = "1", name = "Congelar los plátanos", description = "Pela los plátanos maduros y córtalos en rodajas. Congélalos durante al menos 3 horas o hasta que estén completamente congelados.", recipe = "Helado de Plátano"),
                    Step(number = "2", name = "Licuar los plátanos", description = "Licúa los plátanos congelados hasta obtener una consistencia cremosa. Puedes añadir un poco de leche vegetal o vainilla para darle más sabor.", recipe = "Helado de Plátano"),
                    Step(number = "3", name = "Servir", description = "Sirve el helado de plátano en copas o tazones. Puedes agregar frutas frescas o nueces como topping.", recipe = "Helado de Plátano"),
                    Step(number = "4", name = "Disfrutar", description = "Disfruta de un delicioso helado de plátano natural y refrescante, perfecto para un postre saludable.", recipe = "Helado de Plátano"),
                    Step(number = "1", name = "Preparar la base de coliflor", description = "Corta la coliflor en pequeños trozos y procésalos hasta obtener una textura similar al arroz. Cocina en el microondas o en el fuego hasta que esté suave.", recipe = "Pizza de Coliflor"),
                    Step(number = "2", name = "Formar la base", description = "Mezcla la coliflor cocida con huevo, queso rallado y especias. Forma una masa y colócala en una bandeja para hornear. Hornea durante 15-20 minutos a 180°C.", recipe = "Pizza de Coliflor"),
                    Step(number = "3", name = "Añadir los toppings", description = "Añade salsa de tomate, queso y tus toppings favoritos, como champiñones, pimientos y aceitunas.", recipe = "Pizza de Coliflor"),
                    Step(number = "4", name = "Hornear", description = "Hornea la pizza durante 10-15 minutos adicionales hasta que el queso se derrita y los bordes estén dorados. Sirve caliente.", recipe = "Pizza de Coliflor"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Corta tomates, pepinos, cebolla roja y queso feta en trozos pequeños. Corta aceitunas Kalamata por la mitad.", recipe = "Ensalada Griega"),
                    Step(number = "2", name = "Mezclar la ensalada", description = "Coloca todos los ingredientes en un tazón grande. Añade orégano seco y sal al gusto.", recipe = "Ensalada Griega"),
                    Step(number = "3", name = "Añadir el aderezo", description = "En un tazón pequeño, mezcla aceite de oliva, vinagre, limón y un poco de sal. Vierte sobre la ensalada y mezcla bien.", recipe = "Ensalada Griega"),
                    Step(number = "4", name = "Servir", description = "Sirve la ensalada fría y disfruta de este platillo fresco y saludable, ideal como acompañante o entrada.", recipe = "Ensalada Griega"),
                    Step(number = "1", name = "Marinar el pollo", description = "Marina el pollo con aceite de oliva, limón, ajo, romero, sal y pimienta durante al menos 30 minutos.", recipe = "Pollo a la Parrilla con Vegetales"),
                    Step(number = "2", name = "Preparar los vegetales", description = "Corta los vegetales, como pimientos, calabacines y cebollas, en trozos grandes. Rocíalos con aceite de oliva y sal.", recipe = "Pollo a la Parrilla con Vegetales"),
                    Step(number = "3", name = "Asar el pollo y los vegetales", description = "Coloca el pollo y los vegetales en la parrilla o sartén. Cocina el pollo hasta que esté bien dorado y los vegetales hasta que estén tiernos.", recipe = "Pollo a la Parrilla con Vegetales"),
                    Step(number = "4", name = "Servir", description = "Sirve el pollo y los vegetales asados acompañados de una salsa ligera o ensalada fresca.", recipe = "Pollo a la Parrilla con Vegetales"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Pica cebolla, ajo, zanahoria y apio. Lava las lentejas y escúrrelas.", recipe = "Sopa de Lentejas"),
                    Step(number = "2", name = "Sofreír las verduras", description = "En una olla grande, sofríe la cebolla, ajo, zanahoria y apio hasta que estén tiernos.", recipe = "Sopa de Lentejas"),
                    Step(number = "3", name = "Cocinar las lentejas", description = "Agrega las lentejas, caldo de verduras, laurel y tomate triturado. Cocina a fuego lento durante 30-40 minutos hasta que las lentejas estén blandas.", recipe = "Sopa de Lentejas"),
                    Step(number = "4", name = "Servir", description = "Sirve la sopa caliente, y si lo deseas, puedes agregar un chorrito de aceite de oliva por encima.", recipe = "Sopa de Lentejas"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Precalienta el horno a 180°C. En un tazón, mezcla harina de almendra, polvo de hornear, sal y azúcar.", recipe = "Bizcocho de Almendra"),
                    Step(number = "2", name = "Mezclar la masa", description = "Agrega los huevos, la leche de almendra, aceite de coco y esencia de vainilla. Mezcla hasta que la masa esté suave y homogénea.", recipe = "Bizcocho de Almendra"),
                    Step(number = "3", name = "Hornear", description = "Vierte la masa en un molde engrasado y hornea durante 25-30 minutos hasta que al insertar un palillo, salga limpio.", recipe = "Bizcocho de Almendra"),
                    Step(number = "4", name = "Servir", description = "Deja enfriar el bizcocho antes de cortarlo. Puedes espolvorear con azúcar glas o decorar con almendras troceadas.", recipe = "Bizcocho de Almendra"),
                    Step(number = "1", name = "Preparar los zoodles", description = "Usa un espiralizador para convertir los calabacines en fideos (zoodles). Reserva.", recipe = "Zoodles con Pesto"),
                    Step(number = "2", name = "Hacer el pesto", description = "En una licuadora, mezcla albahaca, piñones, ajo, aceite de oliva y queso parmesano hasta obtener una pasta suave.", recipe = "Zoodles con Pesto"),
                    Step(number = "3", name = "Cocinar los zoodles", description = "Sofríe los zoodles en una sartén con un poco de aceite de oliva durante 2-3 minutos hasta que estén tiernos pero firmes.", recipe = "Zoodles con Pesto"),
                    Step(number = "4", name = "Mezclar con pesto", description = "Agrega el pesto preparado a los zoodles y mezcla bien para cubrirlos uniformemente. Sirve caliente.", recipe = "Zoodles con Pesto"),
                    Step(number = "1", name = "Marinar el pollo", description = "Mezcla mostaza, miel, ajo picado, aceite de oliva, sal y pimienta. Marina el pollo en esta mezcla por al menos 30 minutos.", recipe = "Pollo a la Mostaza"),
                    Step(number = "2", name = "Cocinar el pollo", description = "En una sartén, cocina el pollo a fuego medio-alto hasta que esté dorado y cocido por completo, aproximadamente 6-7 minutos por cada lado.", recipe = "Pollo a la Mostaza"),
                    Step(number = "3", name = "Preparar la salsa", description = "En la misma sartén, agrega un poco de agua o caldo y cocina la salsa hasta que espese ligeramente.", recipe = "Pollo a la Mostaza"),
                    Step(number = "4", name = "Servir", description = "Sirve el pollo con la salsa de mostaza por encima. Acompáñalo con verduras al vapor o arroz integral.", recipe = "Pollo a la Mostaza"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Lava y corta la lechuga romana. Ralla el queso parmesano y prepara los ingredientes del aderezo (yogurt griego, ajo, mostaza, limón, aceite de oliva y sal).", recipe = "Ensalada César sin Croutons"),
                    Step(number = "2", name = "Preparar el aderezo", description = "Mezcla el yogurt griego con mostaza, ajo picado, jugo de limón, aceite de oliva, sal y pimienta hasta obtener una salsa cremosa.", recipe = "Ensalada César sin Croutons"),
                    Step(number = "3", name = "Mezclar la ensalada", description = "Coloca la lechuga en un tazón grande, agrega el queso parmesano rallado y vierte el aderezo. Mezcla bien hasta que todo esté cubierto.", recipe = "Ensalada César sin Croutons"),
                    Step(number = "4", name = "Servir", description = "Sirve la ensalada inmediatamente para disfrutar de su frescura, perfecta como acompañante o plato principal ligero.", recipe = "Ensalada César sin Croutons"),
                    Step(number = "1", name = "Preparar el relleno", description = "Cocina carne molida, pollo o tofu con cebolla, ajo, comino, paprika, sal y pimienta. Cocina hasta que esté bien dorado.", recipe = "Tacos de Lechuga"),
                    Step(number = "2", name = "Preparar las hojas de lechuga", description = "Lava las hojas de lechuga y sécalas bien. Usa las hojas grandes como 'tortillas' para los tacos.", recipe = "Tacos de Lechuga"),
                    Step(number = "3", name = "Montar los tacos", description = "Coloca el relleno cocinado en el centro de cada hoja de lechuga. Agrega toppings como cebolla, cilantro, salsa y aguacate.", recipe = "Tacos de Lechuga"),
                    Step(number = "4", name = "Servir", description = "Sirve los tacos de lechuga inmediatamente, disfrutando de un platillo fresco y ligero.", recipe = "Tacos de Lechuga"),
                    Step(number = "1", name = "Preparar la base", description = "Mezcla almendra molida, mantequilla derretida y edulcorante en un tazón. Presiona la mezcla en el fondo de un molde para cheesecake.", recipe = "Cheesecake Keto"),
                    Step(number = "2", name = "Preparar el relleno", description = "Bate queso crema, crema agria, edulcorante, vainilla y huevos hasta obtener una mezcla suave y homogénea.", recipe = "Cheesecake Keto"),
                    Step(number = "3", name = "Hornear el cheesecake", description = "Vierte la mezcla de queso crema sobre la base de almendras y hornea durante 45-50 minutos a 160°C. Deja enfriar completamente.", recipe = "Cheesecake Keto"),
                    Step(number = "4", name = "Servir", description = "Enfría el cheesecake en el refrigerador durante al menos 4 horas antes de servir. Adorna con frutas frescas o crema batida si lo deseas.", recipe = "Cheesecake Keto"),
                    Step(number = "1", name = "Marinar el pollo", description = "Marina las pechugas de pollo con jugo de limón, ajo picado, aceite de oliva, sal y pimienta durante al menos 30 minutos.", recipe = "Pechuga de Pollo al Limón"),
                    Step(number = "2", name = "Cocinar el pollo", description = "En una sartén, cocina las pechugas de pollo a fuego medio-alto durante 6-7 minutos por cada lado, hasta que estén doradas y cocidas por dentro.", recipe = "Pechuga de Pollo al Limón"),
                    Step(number = "3", name = "Preparar la salsa", description = "En la misma sartén, agrega jugo de limón adicional, un poco de caldo y hierbas aromáticas. Cocina hasta que la salsa espese ligeramente.", recipe = "Pechuga de Pollo al Limón"),
                    Step(number = "4", name = "Servir", description = "Sirve las pechugas de pollo con la salsa de limón por encima. Acompáñalo con vegetales o arroz.", recipe = "Pechuga de Pollo al Limón"),
                    Step(number = "1", name = "Preparar las claras", description = "Separa las claras de huevo y bate hasta que estén bien mezcladas. Sazona con sal y pimienta.", recipe = "Tortilla de Claras con Espinaca"),
                    Step(number = "2", name = "Cocinar las espinacas", description = "En una sartén, saltea las espinacas con un poco de aceite de oliva hasta que se marchiten.", recipe = "Tortilla de Claras con Espinaca"),
                    Step(number = "3", name = "Cocinar la tortilla", description = "Vierte las claras batidas sobre las espinacas en la sartén. Cocina a fuego lento hasta que la tortilla esté completamente cocida.", recipe = "Tortilla de Claras con Espinaca"),
                    Step(number = "4", name = "Servir", description = "Sirve la tortilla de claras con espinacas caliente. Puedes acompañarla con una ensalada o pan integral.", recipe = "Tortilla de Claras con Espinaca"),
                    Step(number = "1", name = "Marinar el salmón", description = "Marina los filetes de salmón en salsa teriyaki, jengibre rallado y ajo picado por al menos 30 minutos.", recipe = "Salmón con Salsa Teriyaki"),
                    Step(number = "2", name = "Cocinar el salmón", description = "En una sartén, cocina el salmón a fuego medio-alto durante 4-5 minutos por cada lado hasta que esté bien cocido.", recipe = "Salmón con Salsa Teriyaki"),
                    Step(number = "3", name = "Preparar la salsa", description = "En una sartén pequeña, calienta más salsa teriyaki hasta que espese ligeramente. Agrega un poco de miel si deseas un toque más dulce.", recipe = "Salmón con Salsa Teriyaki"),
                    Step(number = "4", name = "Servir", description = "Sirve el salmón con la salsa teriyaki por encima. Acompáñalo con arroz o vegetales al vapor.", recipe = "Salmón con Salsa Teriyaki"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "En un vaso o licuadora, agrega proteína en polvo, agua o leche vegetal, y si deseas, algunos ingredientes extras como plátano o espinacas.", recipe = "Batido de Proteína"),
                    Step(number = "2", name = "Licuar", description = "Licúa hasta obtener una mezcla suave y homogénea. Asegúrate de que todos los ingredientes estén bien mezclados.", recipe = "Batido de Proteína"),
                    Step(number = "3", name = "Servir", description = "Vierte el batido de proteína en un vaso y disfruta inmediatamente después de tu entrenamiento o como merienda.", recipe = "Batido de Proteína"),
                    Step(number = "4", name = "Disfrutar", description = "Bebe el batido de proteína para recargar energías y recuperar músculos de manera eficiente.", recipe = "Batido de Proteína"),
                    Step(number = "1", name = "Preparar las albóndigas", description = "Mezcla carne molida, pan rallado, huevo, ajo picado, perejil, sal y pimienta. Forma pequeñas bolitas con la mezcla.", recipe = "Albóndigas al Horno"),
                    Step(number = "1", name = "Preparar las albóndigas", description = "Mezcla carne molida, pan rallado, huevo, ajo picado, perejil, sal y pimienta. Forma pequeñas bolitas con la mezcla.", recipe = "Albóndigas al Horno"),
                    Step(number = "2", name = "Hornear las albóndigas", description = "Coloca las albóndigas en una bandeja para hornear y hornéalas a 180°C durante 20-25 minutos hasta que estén doradas y cocidas por dentro.", recipe = "Albóndigas al Horno"),
                    Step(number = "3", name = "Preparar la salsa", description = "En una sartén, calienta salsa de tomate, ajo, cebolla y especias. Cocina a fuego lento durante 10 minutos.", recipe = "Albóndigas al Horno"),
                    Step(number = "4", name = "Servir", description = "Sirve las albóndigas con la salsa de tomate por encima, acompañadas de arroz o pasta integral.", recipe = "Albóndigas al Horno"),
                    Step(number = "1", name = "Preparar el pollo", description = "Sazona el pollo con sal, pimienta, ajo picado, hierbas secas y un poco de aceite de oliva. Deja marinar por al menos 1 hora.", recipe = "Pollo Rostizado"),
                    Step(number = "2", name = "Precalentar el horno", description = "Precalienta el horno a 200°C (400°F). Coloca el pollo en una bandeja para hornear, con la pechuga hacia arriba.", recipe = "Pollo Rostizado"),
                    Step(number = "3", name = "Cocinar el pollo", description = "Hornea el pollo durante 1 hora o hasta que la piel esté dorada y crujiente y los jugos salgan claros al pincharlo.", recipe = "Pollo Rostizado"),
                    Step(number = "4", name = "Servir", description = "Deja reposar el pollo durante 10 minutos antes de cortarlo. Sirve con tus guarniciones favoritas como papas o ensaladas.", recipe = "Pollo Rostizado"),
                    Step(number = "1", name = "Preparar el pollo", description = "Sazona el pollo con sal, pimienta, ajo picado, hierbas secas y un poco de aceite de oliva. Deja marinar por al menos 1 hora.", recipe = "Pollo Rostizado"),
                    Step(number = "2", name = "Precalentar el horno", description = "Precalienta el horno a 200°C (400°F). Coloca el pollo en una bandeja para hornear, con la pechuga hacia arriba.", recipe = "Pollo Rostizado"),
                    Step(number = "3", name = "Cocinar el pollo", description = "Hornea el pollo durante 1 hora o hasta que la piel esté dorada y crujiente y los jugos salgan claros al pincharlo.", recipe = "Pollo Rostizado"),
                    Step(number = "4", name = "Servir", description = "Deja reposar el pollo durante 10 minutos antes de cortarlo. Sirve con tus guarniciones favoritas como papas o ensaladas.", recipe = "Pollo Rostizado"),
                    Step(number = "1", name = "Preparar la masa", description = "Mezcla harina, levadura, sal, agua tibia y aceite de oliva hasta formar una masa. Amasa durante 10 minutos y deja reposar durante 1 hora para que fermente.", recipe = "Pizza Casera"),
                    Step(number = "2", name = "Preparar los ingredientes", description = "Prepara tus ingredientes: salsa de tomate, queso mozzarella, peperoni, verduras o lo que desees como topping.", recipe = "Pizza Casera"),
                    Step(number = "3", name = "Formar la pizza", description = "Extiende la masa sobre una superficie enharinada hasta obtener el tamaño deseado. Agrega una capa de salsa de tomate y distribuye el queso y los toppings.", recipe = "Pizza Casera"),
                    Step(number = "4", name = "Hornear", description = "Hornea la pizza a 220°C (425°F) durante 12-15 minutos o hasta que la masa esté dorada y el queso burbujeante. Sirve caliente.", recipe = "Pizza Casera"),
                    Step(number = "1", name = "Preparar el pescado", description = "Coloca el filete de pescado en un trozo de papel de aluminio. Sazona con sal, pimienta, ajo, hierbas frescas y jugo de limón.", recipe = "Pescado en Papillote"),
                    Step(number = "2", name = "Cerrar el papillote", description = "Dobla el papel de aluminio para envolver completamente el pescado, sellando los bordes para crear un paquete hermético.", recipe = "Pescado en Papillote"),
                    Step(number = "3", name = "Hornear", description = "Hornea el papillote en un horno precalentado a 180°C (350°F) durante 15-20 minutos, dependiendo del grosor del pescado.", recipe = "Pescado en Papillote"),
                    Step(number = "4", name = "Servir", description = "Abre el papillote con cuidado y sirve el pescado con su jugo de cocción. Acompaña con arroz o ensalada.", recipe = "Pescado en Papillote"),
                    Step(number = "1", name = "Preparar las costillas", description = "Sazona las costillas con sal, pimienta y un poco de especias. Deja reposar durante 30 minutos para que absorban los sabores.", recipe = "Costillas BBQ"),
                    Step(number = "2", name = "Cocinar las costillas", description = "Hornea las costillas a 150°C (300°F) durante 2-3 horas, cubriéndolas con papel de aluminio para que queden tiernas.", recipe = "Costillas BBQ"),
                    Step(number = "3", name = "Agregar la salsa BBQ", description = "Durante los últimos 30 minutos, pinta las costillas con salsa BBQ y hornea sin cubrir para que la salsa se caramelice.", recipe = "Costillas BBQ"),
                    Step(number = "4", name = "Servir", description = "Retira las costillas del horno, deja reposar durante 10 minutos y sirve con papas fritas o ensalada de col.", recipe = "Costillas BBQ"),
                    Step(number = "1", name = "Preparar los espárragos", description = "Lava los espárragos y corta las partes duras de los tallos. Sazona con aceite de oliva, sal, pimienta y ajo picado.", recipe = "Espárragos a la Parrilla"),
                    Step(number = "2", name = "Precalentar la parrilla", description = "Precalienta la parrilla a temperatura media-alta.", recipe = "Espárragos a la Parrilla"),
                    Step(number = "3", name = "Cocinar los espárragos", description = "Coloca los espárragos en la parrilla y cocina durante 5-7 minutos, girándolos ocasionalmente, hasta que estén tiernos y ligeramente dorados.", recipe = "Espárragos a la Parrilla"),
                    Step(number = "4", name = "Servir", description = "Sirve los espárragos a la parrilla como acompañante de carnes o pasta. Espolvorea un poco de queso parmesano rallado si lo deseas.", recipe = "Espárragos a la Parrilla"),
                    Step(number = "1", name = "Preparar la carne", description = "Mezcla carne molida con sal, pimienta y otros condimentos de tu elección. Forma 4 hamburguesas de tamaño uniforme.", recipe = "Hamburguesas"),
                    Step(number = "2", name = "Cocinar las hamburguesas", description = "Cocina las hamburguesas en una sartén caliente o parrilla durante 4-5 minutos por cada lado, hasta que estén cocidas a tu gusto.", recipe = "Hamburguesas"),
                    Step(number = "3", name = "Montar la hamburguesa", description = "Coloca las hamburguesas cocidas en panecillos de hamburguesa y añade los ingredientes que prefieras, como lechuga, tomate, cebolla y queso.", recipe = "Hamburguesas"),
                    Step(number = "4", name = "Servir", description = "Sirve las hamburguesas con papas fritas o ensalada. Disfruta de un delicioso platillo clásico.", recipe = "Hamburguesas"),
                    Step(number = "1", name = "Marinar el pollo", description = "Corta el pollo en trozos y marina con aceite de oliva, limón, ajo picado, sal, pimienta y especias durante al menos 30 minutos.", recipe = "Brochetas de Pollo"),
                    Step(number = "2", name = "Ensartar el pollo", description = "Ensarta los trozos de pollo en brochetas, alternándolos con trozos de verduras como pimientos, cebolla o calabacín.", recipe = "Brochetas de Pollo"),
                    Step(number = "3", name = "Cocinar las brochetas", description = "Cocina las brochetas en una parrilla precalentada durante 5-7 minutos por cada lado hasta que el pollo esté completamente cocido.", recipe = "Brochetas de Pollo"),
                    Step(number = "4", name = "Servir", description = "Sirve las brochetas de pollo con arroz o ensalada fresca para una comida ligera y deliciosa.", recipe = "Brochetas de Pollo"),
                    Step(number = "1", name = "Preparar la piña", description = "Pela la piña y córtala en rodajas gruesas. Elimina el corazón de las rodajas.", recipe = "Piña a la Parrilla"),
                    Step(number = "2", name = "Sazonar la piña", description = "Unta las rodajas de piña con un poco de aceite de oliva y espolvorea con canela o azúcar moreno, si lo deseas.", recipe = "Piña a la Parrilla"),
                    Step(number = "3", name = "Cocinar la piña", description = "Coloca las rodajas de piña en la parrilla y cocina durante 3-4 minutos por cada lado hasta que estén doradas y con marcas de la parrilla.", recipe = "Piña a la Parrilla"),
                    Step(number = "4", name = "Servir", description = "Sirve la piña a la parrilla como postre o acompañamiento de carnes. Puedes añadir un poco de miel o yogurt para darle un toque especial.", recipe = "Piña a la Parrilla"),
                    Step(number = "1", name = "Preparar el relleno", description = "En una sartén, derrite mantequilla y sofríe cebolla picada. Agrega jamón picado, harina y leche. Cocina hasta obtener una masa espesa.", recipe = "Croquetas de Jamón"),
                    Step(number = "2", name = "Formar las croquetas", description = "Deja enfriar la masa y luego forma pequeñas bolitas o cilindros con las manos.", recipe = "Croquetas de Jamón"),
                    Step(number = "3", name = "Empanizar las croquetas", description = "Pasa las croquetas por huevo batido y luego por pan rallado. Fríelas en aceite caliente hasta que estén doradas y crujientes.", recipe = "Croquetas de Jamón"),
                    Step(number = "4", name = "Servir", description = "Deja escurrir el exceso de aceite sobre papel absorbente y sirve las croquetas acompañadas de una salsa de tu elección.", recipe = "Croquetas de Jamón"),
                    Step(number = "1", name = "Preparar el relleno", description = "En una sartén, derrite mantequilla y sofríe cebolla picada. Agrega jamón picado, harina y leche. Cocina hasta obtener una masa espesa.", recipe = "Croquetas de Jamón"),
                    Step(number = "2", name = "Formar las croquetas", description = "Deja enfriar la masa y luego forma pequeñas bolitas o cilindros con las manos.", recipe = "Croquetas de Jamón"),
                    Step(number = "3", name = "Empanizar las croquetas", description = "Pasa las croquetas por huevo batido y luego por pan rallado. Fríelas en aceite caliente hasta que estén doradas y crujientes.", recipe = "Croquetas de Jamón"),
                    Step(number = "4", name = "Servir", description = "Deja escurrir el exceso de aceite sobre papel absorbente y sirve las croquetas acompañadas de una salsa de tu elección.", recipe = "Croquetas de Jamón"),
                    Step(number = "1", name = "Preparar las verduras", description = "Corta las verduras (como zanahorias, calabacín, berenjenas y pimientos) en trozos uniformes para que se cocinen de manera uniforme.", recipe = "Tempura de Verduras"),
                    Step(number = "2", name = "Preparar la masa de tempura", description = "Mezcla harina de tempura con agua fría hasta obtener una masa ligera y suave. No debes mezclar demasiado para evitar que se pierda la textura crujiente.", recipe = "Tempura de Verduras"),
                    Step(number = "3", name = "Freír las verduras", description = "Sumérge las verduras en la masa de tempura y fríelas en aceite caliente durante 2-3 minutos o hasta que estén doradas y crujientes.", recipe = "Tempura de Verduras"),
                    Step(number = "4", name = "Servir", description = "Escurre las verduras en papel absorbente y sirve calientes con una salsa de soya para acompañar.", recipe = "Tempura de Verduras"),
                    Step(number = "1", name = "Marinar el pollo", description = "Sazona el pollo con sal, pimienta, ajo, cebolla y tus especias favoritas. Deja marinar en el refrigerador durante al menos 1 hora.", recipe = "Pollo Frito"),
                    Step(number = "2", name = "Empanizar el pollo", description = "Pasa el pollo por harina, luego por huevo batido y finalmente por pan rallado o mezcla de harina y maicena. Asegúrate de cubrirlo bien.", recipe = "Pollo Frito"),
                    Step(number = "3", name = "Freír el pollo", description = "Calienta aceite en una sartén o freidora. Fría el pollo en lotes pequeños durante 12-15 minutos o hasta que esté dorado y cocido por dentro.", recipe = "Pollo Frito"),
                    Step(number = "4", name = "Servir", description = "Coloca el pollo frito sobre papel absorbente para eliminar el exceso de aceite y sirve acompañado de papas o ensalada.", recipe = "Pollo Frito"),
                    Step(number = "1", name = "Preparar la masa", description = "Mezcla harina, azúcar, levadura, leche, huevos y mantequilla. Amasa hasta obtener una masa suave y deja reposar durante 1 hora para que suba.", recipe = "Donas"),
                    Step(number = "2", name = "Formar las donas", description = "Estira la masa con un rodillo y corta círculos con un cortador. Haz un agujero en el centro de cada uno para darles la forma de dona.", recipe = "Donas"),
                    Step(number = "3", name = "Freír las donas", description = "Fría las donas en aceite caliente durante 2-3 minutos o hasta que estén doradas. Luego, colócalas en papel absorbente para eliminar el exceso de aceite.", recipe = "Donas"),
                    Step(number = "4", name = "Decorarlas", description = "Deja que las donas se enfríen un poco y decóralas con glaseado, azúcar glas o chocolate derretido.", recipe = "Donas"),
                    Step(number = "1", name = "Preparar el pescado", description = "Corta el pescado fresco (como el lenguado o tilapia) en trozos pequeños y ponlos en un tazón. Agrega el jugo de limón y deja reposar durante 30 minutos para que se 'cocine' en el ácido.", recipe = "Ceviche"),
                    Step(number = "2", name = "Añadir los ingredientes", description = "Agrega cebolla morada picada, tomate, cilantro, chile y pepino al pescado, y mezcla bien.", recipe = "Ceviche"),
                    Step(number = "3", name = "Sazonar", description = "Sazona con sal, pimienta y unas gotas de salsa picante si deseas un toque extra de sabor.", recipe = "Ceviche"),
                    Step(number = "4", name = "Servir", description = "Sirve el ceviche frío, acompañado de tostadas de maíz o galletas saladas. Puedes decorarlo con rodajas de aguacate.", recipe = "Ceviche"),
                    Step(number = "1", name = "Preparar el salmón", description = "Corta el salmón fresco en cubos pequeños, asegurándote de quitarle las espinas y la piel. Colócalo en un tazón.", recipe = "Tartare de Salmón"),
                    Step(number = "2", name = "Añadir los ingredientes", description = "Añade cebollín picado, alcaparras, mostaza Dijon, jugo de limón, aceite de oliva y salsa de soya al salmón. Mezcla bien.", recipe = "Tartare de Salmón"),
                    Step(number = "3", name = "Sazonar", description = "Sazona con sal y pimienta al gusto. Si lo deseas, agrega un toque de eneldo o aguacate para darle más sabor.", recipe = "Tartare de Salmón"),
                    Step(number = "4", name = "Servir", description = "Sirve el tartare de salmón fresco con pan tostado o galletas saladas como aperitivo.", recipe = "Tartare de Salmón"),
                    Step(number = "1", name = "Preparar la carne", description = "Corta la carne de res en lonjas finas con un cuchillo bien afilado o usa una máquina para cortar fiambres. Coloca las lonjas en un plato plano.", recipe = "Carpaccio de Res"),
                    Step(number = "2", name = "Sazonar", description = "Sazona las lonjas de carne con sal, pimienta, aceite de oliva, jugo de limón y unas hojas de albahaca fresca.", recipe = "Carpaccio de Res"),
                    Step(number = "3", name = "Añadir los ingredientes", description = "Añade las alcaparras y las virutas de queso parmesano por encima, y rocía con un poco más de aceite de oliva.", recipe = "Carpaccio de Res"),
                    Step(number = "4", name = "Servir", description = "Sirve el carpaccio de res como entrada acompañado de una ensalada ligera o pan tostado.", recipe = "Carpaccio de Res"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Corta manzanas, apio y nueces en trozos pequeños. Colócalos en un tazón grande.", recipe = "Ensalada Waldorf"),
                    Step(number = "2", name = "Preparar el aderezo", description = "Mezcla mayonesa con un poco de yogur natural y jugo de limón. Agrega sal y pimienta al gusto.", recipe = "Ensalada Waldorf"),
                    Step(number = "3", name = "Mezclar", description = "Agrega el aderezo a los ingredientes de la ensalada y mezcla bien para que todo quede cubierto.", recipe = "Ensalada Waldorf"),
                    Step(number = "4", name = "Servir", description = "Sirve la ensalada Waldorf fría como acompañamiento de carnes o como plato principal para una comida ligera.", recipe = "Ensalada Waldorf"),
                    Step(number = "1", name = "Cocinar la carne", description = "En una olla grande, sofríe carne molida con cebolla, ajo, pimientos, comino, paprika y chile en polvo. Cocina hasta que la carne esté bien dorada.", recipe = "Chili con Carne"),
                    Step(number = "2", name = "Agregar los tomates y frijoles", description = "Añade tomates triturados, frijoles rojos y caldo de carne. Mezcla bien y deja cocinar a fuego lento durante 30 minutos.", recipe = "Chili con Carne"),
                    Step(number = "3", name = "Sazonar", description = "Ajusta la sazón con sal, pimienta y un poco de azúcar si el chili está muy ácido. Cocina por 10 minutos más.", recipe = "Chili con Carne"),
                    Step(number = "4", name = "Servir", description = "Sirve el chili caliente, acompañado de arroz, crema agria, queso rallado y tortillas o pan.", recipe = "Chili con Carne"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Corta zanahorias, apio, cebolla, calabacín y tomates en trozos pequeños. Cocina en una olla con aceite hasta que estén tiernos.", recipe = "Sopa Minestrone"),
                    Step(number = "2", name = "Añadir caldo y pasta", description = "Agrega caldo de verduras, hojas de laurel, y pasta pequeña. Cocina hasta que la pasta esté al dente.", recipe = "Sopa Minestrone"),
                    Step(number = "3", name = "Incorporar los frijoles", description = "Añade frijoles blancos cocidos y mezcla bien. Cocina durante 5-10 minutos más.", recipe = "Sopa Minestrone"),
                    Step(number = "4", name = "Servir", description = "Sirve la sopa caliente con un toque de queso parmesano rallado y un chorrito de aceite de oliva.", recipe = "Sopa Minestrone"),
                    Step(number = "1", name = "Preparar el caldo", description = "Hierve huesos de res o pollo con cebolla, jengibre, canela, anís estrellado y clavos. Cocina a fuego lento durante 3-4 horas.", recipe = "Pho"),
                    Step(number = "2", name = "Cocer los fideos", description = "Cocina los fideos de arroz según las instrucciones del paquete. Escurre y divide entre los tazones.", recipe = "Pho"),
                    Step(number = "3", name = "Añadir los ingredientes", description = "Sirve el caldo caliente sobre los fideos, y agrega carne en rodajas finas, hierbas frescas, cebollas, brotes de soja y chile.", recipe = "Pho"),
                    Step(number = "4", name = "Servir", description = "Añade salsa de pescado, limón y salsa sriracha al gusto. Sirve caliente y disfruta.", recipe = "Pho"),
                    Step(number = "1", name = "Dorar la carne", description = "En una olla grande, dora los trozos de ternera con aceite caliente. Sazona con sal y pimienta.", recipe = "Estofado de Ternera"),
                    Step(number = "2", name = "Agregar vegetales", description = "Añade cebolla, zanahorias, apio y ajo picado. Sofríe durante unos minutos hasta que los vegetales estén ligeramente dorados.", recipe = "Estofado de Ternera"),
                    Step(number = "3", name = "Cocinar el estofado", description = "Agrega caldo de res, vino tinto y hierbas como tomillo y laurel. Cocina a fuego lento durante 2-3 horas hasta que la carne esté tierna.", recipe = "Estofado de Ternera"),
                    Step(number = "4", name = "Servir", description = "Sirve el estofado caliente, acompañado de puré de papas o pan crujiente para mojar en el caldo.", recipe = "Estofado de Ternera"),
                    Step(number = "1", name = "Cocinar el pollo", description = "En una olla grande, hierve pollo con cebolla, zanahorias, apio, ajo y hierbas como el laurel. Cocina durante 1-2 horas.", recipe = "Sopa de Pollo"),
                    Step(number = "2", name = "Desmenuzar el pollo", description = "Retira el pollo de la olla, desmenúzalo y regresa la carne al caldo.", recipe = "Sopa de Pollo"),
                    Step(number = "3", name = "Añadir fideos", description = "Agrega fideos de sopa o arroz al caldo y cocina hasta que estén tiernos.", recipe = "Sopa de Pollo"),
                    Step(number = "4", name = "Servir", description = "Sirve la sopa caliente, decorada con cilantro fresco o cebollín. Acompáñala con pan o galletas saladas.", recipe = "Sopa de Pollo"),
                    Step(number = "1", name = "Mezclar los ingredientes secos", description = "En un tazón grande, mezcla harina, cacao en polvo, polvo de hornear, bicarbonato y sal.", recipe = "Pastel de Chocolate"),
                    Step(number = "2", name = "Mezclar los ingredientes líquidos", description = "En otro tazón, bate huevos, azúcar, leche, aceite y vainilla. Agrega la mezcla líquida a los ingredientes secos y mezcla bien.", recipe = "Pastel de Chocolate"),
                    Step(number = "3", name = "Hornear el pastel", description = "Vierte la masa en un molde engrasado y hornea a 180°C durante 30-35 minutos, o hasta que al insertar un palillo, salga limpio.", recipe = "Pastel de Chocolate"),
                    Step(number = "4", name = "Servir", description = "Deja enfriar el pastel antes de decorarlo con glaseado de chocolate o azúcar glas. Sirve y disfruta.", recipe = "Pastel de Chocolate"),
                    Step(number = "1", name = "Preparar la masa", description = "Mezcla harina, azúcar, polvo de hornear, huevos, leche, mantequilla derretida y esencia de vainilla. Vierte en moldes para cupcakes.", recipe = "Cupcakes Decorados"),
                    Step(number = "2", name = "Hornear los cupcakes", description = "Hornea los cupcakes a 180°C durante 15-20 minutos, o hasta que al insertar un palillo, salga limpio.", recipe = "Cupcakes Decorados"),
                    Step(number = "3", name = "Decorar los cupcakes", description = "Deja enfriar los cupcakes y decóralos con crema de mantequilla, glaseado de chocolate o azúcar glas. Puedes agregar chispas de chocolate o frutas.", recipe = "Cupcakes Decorados"),
                    Step(number = "4", name = "Servir", description = "Sirve los cupcakes decorados como postre o merienda para ocasiones especiales.", recipe = "Cupcakes Decorados"),
                    Step(number = "1", name = "Preparar la masa", description = "Desenrolla la masa de croissant y corta en triángulos. Coloca un poco de relleno, como chocolate o jamón y queso, en la base de cada triángulo.", recipe = "Croissants Rellenos"),
                    Step(number = "2", name = "Enrollar los croissants", description = "Enrolla los triángulos de masa con el relleno, formando la forma característica del croissant. Colócalos en una bandeja para hornear.", recipe = "Croissants Rellenos"),
                    Step(number = "3", name = "Hornear los croissants", description = "Hornea los croissants a 190°C durante 15-20 minutos o hasta que estén dorados y crujientes.", recipe = "Croissants Rellenos"),
                    Step(number = "4", name = "Servir", description = "Sirve los croissants rellenos calientes, ideales para el desayuno o merienda.", recipe = "Croissants Rellenos"),
                    Step(number = "1", name = "Preparar la masa", description = "Amasa la masa para pizza y córtala en pequeños círculos, adecuados para mini pizzas. Colócalos en una bandeja para hornear.", recipe = "Mini Pizzas"),
                    Step(number = "2", name = "Añadir los ingredientes", description = "Cubre cada base de pizza con salsa de tomate, queso y tus ingredientes favoritos, como jamón, champiñones y pimientos.", recipe = "Mini Pizzas"),
                    Step(number = "3", name = "Hornear las mini pizzas", description = "Hornea las mini pizzas a 200°C durante 10-15 minutos, o hasta que el queso esté derretido y dorado.", recipe = "Mini Pizzas"),
                    Step(number = "4", name = "Servir", description = "Sirve las mini pizzas calientes, ideales para una merienda o como aperitivo en una fiesta.", recipe = "Mini Pizzas"),
                    Step(number = "1", name = "Mezclar los ingredientes", description = "En una jarra grande, combina vino tinto, brandy, jugo de naranja, rodajas de naranja y limón. Añade azúcar al gusto.", recipe = "Sangría"),
                    Step(number = "2", name = "Refrigerar", description = "Deja reposar la mezcla en el refrigerador durante al menos 2 horas para que los sabores se integren.", recipe = "Sangría"),
                    Step(number = "3", name = "Añadir frutas", description = "Agrega frutas frescas, como manzanas, peras y frutos rojos, para dar más sabor a la sangría.", recipe = "Sangría"),
                    Step(number = "4", name = "Servir", description = "Sirve la sangría bien fría, ideal para disfrutar en una reunión o celebración.", recipe = "Sangría"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Corta tomates, pepinos, pimientos y cebolla en trozos pequeños. Pela los dientes de ajo y prepara vinagre y aceite de oliva.", recipe = "Gazpacho"),
                    Step(number = "2", name = "Mezclar los ingredientes", description = "En una licuadora o procesador de alimentos, mezcla todos los ingredientes con agua, sal y pimienta al gusto. Tritura hasta obtener una textura suave.", recipe = "Gazpacho"),
                    Step(number = "3", name = "Refrigerar", description = "Deja reposar el gazpacho en el refrigerador durante al menos 1 hora para que se enfríe bien y los sabores se integren.", recipe = "Gazpacho"),
                    Step(number = "4", name = "Servir", description = "Sirve el gazpacho bien frío, acompañado de trozos de pan tostado o un toque de aceite de oliva y cebollín picado.", recipe = "Gazpacho"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Corta tomates, pepinos, pimientos y cebolla en trozos pequeños. Pela los dientes de ajo y prepara vinagre y aceite de oliva.", recipe = "Gazpacho"),
                    Step(number = "2", name = "Mezclar los ingredientes", description = "En una licuadora o procesador de alimentos, mezcla todos los ingredientes con agua, sal y pimienta al gusto. Tritura hasta obtener una textura suave.", recipe = "Gazpacho"),
                    Step(number = "3", name = "Refrigerar", description = "Deja reposar el gazpacho en el refrigerador durante al menos 1 hora para que se enfríe bien y los sabores se integren.", recipe = "Gazpacho"),
                    Step(number = "4", name = "Servir", description = "Sirve el gazpacho bien frío, acompañado de trozos de pan tostado o un toque de aceite de oliva y cebollín picado.", recipe = "Gazpacho"),
                    Step(number = "1", name = "Preparar el pavo", description = "Rellena el pavo con una mezcla de pan rallado, cebolla, ajo, manzana y especias. Ata las piernas y cubre el pavo con mantequilla.", recipe = "Pavo Relleno"),
                    Step(number = "2", name = "Hornear el pavo", description = "Coloca el pavo en una bandeja para hornear y hornéalo a 180°C durante 3-4 horas, bañándolo con sus jugos cada 30 minutos.", recipe = "Pavo Relleno"),
                    Step(number = "3", name = "Verificar la cocción", description = "Usa un termómetro de cocina para asegurarte de que el pavo alcance los 75°C en su parte más gruesa.", recipe = "Pavo Relleno"),
                    Step(number = "4", name = "Servir", description = "Deja reposar el pavo durante 15 minutos antes de cortarlo. Sirve con su relleno y salsa de su propio jugo.", recipe = "Pavo Relleno"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Corta manzanas, apio y nueces en trozos pequeños. Lava las uvas y ponlas en un tazón grande.", recipe = "Ensalada Waldorf"),
                    Step(number = "2", name = "Mezclar los ingredientes", description = "En un tazón grande, mezcla las frutas, el apio y las nueces. En un tazón pequeño, combina mayonesa, yogur y un toque de mostaza.", recipe = "Ensalada Waldorf"),
                    Step(number = "3", name = "Añadir la salsa", description = "Vierte la mezcla de mayonesa sobre las frutas y mezcle bien hasta que todo quede cubierto de manera uniforme.", recipe = "Ensalada Waldorf"),
                    Step(number = "4", name = "Servir", description = "Sirve la ensalada fría, como aperitivo o acompañante para platos principales. Puedes decorarla con más nueces si lo deseas.", recipe = "Ensalada Waldorf"),
                    Step(number = "1", name = "Preparar la masa", description = "Mezcla harina, jengibre, canela, bicarbonato, azúcar moreno y mantequilla en un tazón. Agrega un huevo y miel, amasa hasta obtener una masa suave.", recipe = "Galletas de Jengibre"),
                    Step(number = "2", name = "Formar las galletas", description = "Extiende la masa con un rodillo y corta las galletas en formas de tu elección. Coloca las galletas en una bandeja para hornear.", recipe = "Galletas de Jengibre"),
                    Step(number = "3", name = "Hornear las galletas", description = "Hornea las galletas a 180°C durante 8-10 minutos o hasta que estén doradas y crujientes.", recipe = "Galletas de Jengibre"),
                    Step(number = "4", name = "Servir", description = "Deja enfriar las galletas y decóralas con glaseado si lo deseas. Sirve como un delicioso dulce navideño.", recipe = "Galletas de Jengibre"),
                    Step(number = "1", name = "Preparar la base", description = "En una olla grande, mezcla jugo de piña, jugo de naranja, canela, clavos de olor, y cáscara de naranja. Cocina a fuego lento durante 20 minutos.", recipe = "Ponche Navideño"),
                    Step(number = "2", name = "Añadir frutas", description = "Añade trozos de frutas como manzana, pera y guayaba al ponche. Deja que se cocinen junto con la mezcla durante otros 10 minutos.", recipe = "Ponche Navideño"),
                    Step(number = "3", name = "Incorporar el alcohol", description = "Si deseas, añade ron o tequila al ponche, y mezcla bien. Cocina por otros 5 minutos.", recipe = "Ponche Navideño"),
                    Step(number = "4", name = "Servir", description = "Sirve el ponche caliente, ideal para las celebraciones navideñas, en tazas decoradas con rodajas de frutas.", recipe = "Ponche Navideño"),
                    Step(number = "1", name = "Preparar el bizcocho", description = "Bate huevos, azúcar y harina para hacer un bizcocho esponjoso. Vierte la mezcla en un molde y hornea a 180°C durante 10-12 minutos.", recipe = "Tronco de Navidad"),
                    Step(number = "2", name = "Enrollar el bizcocho", description = "Una vez que el bizcocho se haya enfriado, unta una capa de crema de chocolate o mermelada y enrolla el bizcocho cuidadosamente.", recipe = "Tronco de Navidad"),
                    Step(number = "3", name = "Cubrir con glaseado", description = "Cubre el tronco con un glaseado de chocolate y decora con azúcar glas, como si fuera nieve.", recipe = "Tronco de Navidad"),
                    Step(number = "4", name = "Servir", description = "Corta el tronco en rebanadas y sirve como postre en la cena navideña. Puedes decorar con ramitas de romero para darle un toque festivo.", recipe = "Tronco de Navidad"),
                    Step(number = "1", name = "Derretir el chocolate", description = "En un tazón, derrite chocolate negro o blanco a baño maría, asegurándote de que no se queme.", recipe = "Fresas Cubiertas de Chocolate"),
                    Step(number = "2", name = "Cubrir las fresas", description = "Sumerge las fresas limpias y secas en el chocolate derretido, cubriéndolas completamente o parcialmente según tu preferencia.", recipe = "Fresas Cubiertas de Chocolate"),
                    Step(number = "3", name = "Dejar que se enfríen", description = "Coloca las fresas cubiertas en una bandeja y deja que se enfríen a temperatura ambiente o refrigéralas para que el chocolate se endurezca.", recipe = "Fresas Cubiertas de Chocolate"),
                    Step(number = "4", name = "Servir", description = "Sirve las fresas cubiertas de chocolate como un delicioso postre o en una mesa de dulces para una celebración especial.", recipe = "Fresas Cubiertas de Chocolate"),
                    Step(number = "1", name = "Preparar el queso", description = "Ralla una mezcla de quesos suizos como Gruyère y Emmental. En una olla, calienta vino blanco con ajo picado.", recipe = "Fondue de Queso"),
                    Step(number = "2", name = "Derretir el queso", description = "Añade los quesos rallados al vino caliente y remueve constantemente hasta que se derritan y la mezcla sea suave.", recipe = "Fondue de Queso"),
                    Step(number = "3", name = "Servir la fondue", description = "Coloca la mezcla de queso derretido en un recipiente de fondue. Sirve con pan en cubos, verduras o frutas para sumergir.", recipe = "Fondue de Queso"),
                    Step(number = "4", name = "Disfrutar", description = "Disfruta de la fondue caliente, ideal para compartir en una velada especial o fiesta.", recipe = "Fondue de Queso"),
                    Step(number = "1", name = "Cocer los raviolis", description = "Cocina los raviolis en agua con sal según las instrucciones del paquete. Escúrrelos y resérvalos.", recipe = "Raviolis en Salsa de Queso"),
                    Step(number = "2", name = "Preparar la salsa", description = "En una sartén, derrite mantequilla, añade crema de leche y queso rallado. Cocina a fuego lento hasta que la salsa esté espesa y suave.", recipe = "Raviolis en Salsa de Queso"),
                    Step(number = "3", name = "Mezclar los raviolis con la salsa", description = "Añade los raviolis cocidos a la sartén con la salsa de queso y mezcla bien para cubrirlos completamente.", recipe = "Raviolis en Salsa de Queso"),
                    Step(number = "4", name = "Servir", description = "Sirve los raviolis con un toque de queso rallado y perejil fresco por encima para decorar.", recipe = "Raviolis en Salsa de Queso"),
                    Step(number = "1", name = "Preparar la base", description = "Mezcla harina, azúcar, mantequilla y agua fría para hacer una masa. Extiende la masa en un molde y hornea a 180°C durante 10-12 minutos.", recipe = "Tarta de Fresas"),
                    Step(number = "2", name = "Preparar el relleno", description = "Lava y corta las fresas en rodajas. En un tazón, mezcla las fresas con azúcar y un poco de almidón de maíz para espesar.", recipe = "Tarta de Fresas"),
                    Step(number = "3", name = "Montar la tarta", description = "Coloca el relleno de fresas sobre la base ya horneada y hornea nuevamente a 180°C durante 20-25 minutos, hasta que las fresas estén cocidas.", recipe = "Tarta de Fresas"),
                    Step(number = "4", name = "Servir", description = "Deja enfriar la tarta a temperatura ambiente antes de servir. Puedes decorar con crema batida o más fresas si lo deseas.", recipe = "Tarta de Fresas"),
                    Step(number = "1", name = "Preparar la base", description = "Mezcla harina, azúcar, mantequilla y agua fría para hacer una masa. Extiende la masa en un molde y hornea a 180°C durante 10-12 minutos.", recipe = "Tarta de Fresas"),
                    Step(number = "2", name = "Preparar el relleno", description = "Lava y corta las fresas en rodajas. En un tazón, mezcla las fresas con azúcar y un poco de almidón de maíz para espesar.", recipe = "Tarta de Fresas"),
                    Step(number = "3", name = "Montar la tarta", description = "Coloca el relleno de fresas sobre la base ya horneada y hornea nuevamente a 180°C durante 20-25 minutos, hasta que las fresas estén cocidas.", recipe = "Tarta de Fresas"),
                    Step(number = "4", name = "Servir", description = "Deja enfriar la tarta a temperatura ambiente antes de servir. Puedes decorar con crema batida o más fresas si lo deseas.", recipe = "Tarta de Fresas"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Tuesta tres rebanadas de pan y prepara los ingredientes: pechuga de pollo, bacon crujiente, lechuga, tomate y mayonesa.", recipe = "Sandwiches Club"),
                    Step(number = "2", name = "Montar el sandwich", description = "En la primera rebanada de pan, coloca una capa de mayonesa, seguido de lechuga, tomate y pechuga de pollo. Coloca la segunda rebanada de pan encima.", recipe = "Sandwiches Club"),
                    Step(number = "3", name = "Añadir bacon", description = "En la segunda capa de pan, agrega bacon crujiente y más mayonesa. Coloca la tercera rebanada de pan encima.", recipe = "Sandwiches Club"),
                    Step(number = "4", name = "Servir", description = "Corta el sandwich en mitades y en diagonal. Sirve con una guarnición de papas fritas o una ensalada.", recipe = "Sandwiches Club"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Lava y corta la lechuga romana. Prepara croutones y queso parmesano rallado.", recipe = "Ensalada César"),
                    Step(number = "2", name = "Hacer el aderezo", description = "En un tazón, mezcla mayonesa, jugo de limón, mostaza, ajo picado, aceite de oliva, salsa Worcestershire, y queso parmesano.", recipe = "Ensalada César"),
                    Step(number = "3", name = "Mezclar la ensalada", description = "Coloca la lechuga en un tazón grande y añade los croutones y el aderezo. Mezcla bien hasta que la lechuga esté cubierta de manera uniforme.", recipe = "Ensalada César"),
                    Step(number = "4", name = "Servir", description = "Sirve la ensalada en platos individuales y espolvorea un poco más de queso parmesano por encima. Puedes añadir pollo a la parrilla si lo deseas.", recipe = "Ensalada César"),
                    Step(number = "1", name = "Preparar la masa", description = "Mezcla harina, azúcar, mantequilla y agua fría para hacer la masa. Extiende la masa en un molde para tarta y hornea a 180°C durante 10 minutos.", recipe = "Tarta de Manzana"),
                    Step(number = "2", name = "Preparar las manzanas", description = "Pela y corta las manzanas en rodajas finas. Mezcla con azúcar, canela y jugo de limón para que se impregnen bien.", recipe = "Tarta de Manzana"),
                    Step(number = "3", name = "Montar la tarta", description = "Coloca las manzanas sobre la base de masa, formando capas. Cubre con una capa de masa adicional o trenza la masa para cubrir la tarta.", recipe = "Tarta de Manzana"),
                    Step(number = "4", name = "Hornear y servir", description = "Hornea la tarta a 180°C durante 40-45 minutos, hasta que la masa esté dorada. Sirve caliente o a temperatura ambiente, acompañada de helado o crema.", recipe = "Tarta de Manzana"),
                    Step(number = "1", name = "Preparar el pollo", description = "Cocina pechugas de pollo a la parrilla o en sartén, sazonando con sal, pimienta y especias. Luego, córtalas en tiras finas.", recipe = "Wraps de Pollo"),
                    Step(number = "2", name = "Preparar los ingredientes adicionales", description = "Lava y corta en tiras lechuga, tomate y cebolla. Prepara salsa de yogurt o mayonesa como aderezo.", recipe = "Wraps de Pollo"),
                    Step(number = "3", name = "Montar los wraps", description = "Coloca una tortilla de trigo en una superficie plana, agrega una capa de lechuga, pollo, tomate, cebolla y aderezo.", recipe = "Wraps de Pollo"),
                    Step(number = "4", name = "Enrollar y servir", description = "Enrolla el wrap firmemente y córtalo por la mitad. Sirve como un almuerzo ligero o cena acompañada de papas fritas o ensalada.", recipe = "Wraps de Pollo"),
                    Step(number = "1", name = "Preparar el jugo de limón", description = "Exprime los limones hasta obtener suficiente jugo. Reserva en un tazón grande.", recipe = "Lemonade"),
                    Step(number = "2", name = "Mezclar con agua y azúcar", description = "En una jarra, mezcla el jugo de limón con agua fría y azúcar al gusto. Remueve bien hasta que el azúcar se disuelva completamente.", recipe = "Lemonade"),
                    Step(number = "3", name = "Añadir hielo", description = "Añade cubos de hielo a la jarra para enfriar la limonada y hacerlo más refrescante.", recipe = "Lemonade"),
                    Step(number = "4", name = "Servir", description = "Sirve la limonada en vasos con hielo adicional. Decora con rodajas de limón o menta fresca para un toque especial.", recipe = "Lemonade"),
                    Step(number = "1", name = "Preparar los ingredientes", description = "Reúne los ingredientes básicos para los cócteles, como vodka, ron, ginebra, licor de frutas, jugos, jarabe y hielo.", recipe = "Cocteles Variados"),
                    Step(number = "2", name = "Mezclar los cócteles", description = "En una coctelera, combina los ingredientes según la receta de cada cóctel. Agita bien con hielo para enfriar y mezclar los sabores.", recipe = "Cocteles Variados"),
                    Step(number = "3", name = "Colar y servir", description = "Cuela la mezcla en copas elegantes, asegurándote de eliminar los trozos de hielo. Decora con frutas, hierbas o especias según el tipo de cóctel.", recipe = "Cocteles Variados"),
                    Step(number = "4", name = "Presentación", description = "Asegúrate de presentar los cócteles con una decoración atractiva y sirve inmediatamente mientras están fríos.", recipe = "Cocteles Variados"),
                    Step(number = "1", name = "Preparar las bases", description = "Usa pan de molde, galletas saladas o rodajas de pepino como base para los canapés. Corta las bases en tamaños pequeños y uniformes.", recipe = "Canapés"),
                    Step(number = "2", name = "Preparar los rellenos", description = "Crea varios rellenos utilizando ingredientes como queso crema, salmón ahumado, jamón, guacamole o paté. Sazona con hierbas y especias al gusto.", recipe = "Canapés"),
                    Step(number = "3", name = "Montar los canapés", description = "Coloca una capa de relleno sobre cada base y adorna con ingredientes adicionales como aceitunas, cebollín o alcaparras.", recipe = "Canapés"),
                    Step(number = "4", name = "Servir", description = "Sirve los canapés en una bandeja, decorados de manera atractiva para una presentación elegante. Perfectos para aperitivos en eventos o reuniones.", recipe = "Canapés"),
                    Step(number = "1", name = "Preparar la masa", description = "Haz una masa para quiche combinando harina, mantequilla y agua fría. Estírala y colócala en moldes pequeños para hornear.", recipe = "Mini Quiches"),
                    Step(number = "2", name = "Hacer el relleno", description = "En un tazón, bate huevos y mezcla con nata, queso rallado y los ingredientes que desees, como espinacas, bacon o champiñones.", recipe = "Mini Quiches"),
                    Step(number = "3", name = "Rellenar las bases", description = "Vierte el relleno sobre la base de masa ya preparada, asegurándote de llenar cada molde de manera uniforme.", recipe = "Mini Quiches"),
                    Step(number = "4", name = "Hornear y servir", description = "Hornea los mini quiches a 180°C durante 20-25 minutos hasta que estén dorados y firmes. Deja enfriar un poco antes de servir.", recipe = "Mini Quiches"),
                    Step(number = "1", name = "Preparar la masa", description = "En un tazón, mezcla almendra molida, azúcar, huevos y ralladura de limón. Agrega una pizca de canela y una cucharadita de licor de anís para dar sabor.", recipe = "Tarta de Santiago"),
                    Step(number = "2", name = "Montar la tarta", description = "Vierte la mezcla sobre un molde engrasado y enharinado. Alisa la superficie con una espátula para que quede uniforme.", recipe = "Tarta de Santiago"),
                    Step(number = "3", name = "Hornear", description = "Hornea la tarta a 180°C durante unos 25-30 minutos, hasta que esté dorada y firme al tacto. Deja enfriar antes de desmoldar.", recipe = "Tarta de Santiago"),
                    Step(number = "4", name = "Decorar y servir", description = "Decora la tarta con azúcar glas y una cruz de Santiago hecha con plantilla. Sirve a temperatura ambiente o ligeramente fría para un sabor más intenso.", recipe = "Tarta de Santiago"),
                    Step(number = "1", name = "Derretir el chocolate", description = "Coloca trozos de chocolate oscuro o con leche en una cazuela. Derrítelo a fuego lento, agregando un poco de crema para que quede suave.", recipe = "Fondue de Chocolate"),
                    Step(number = "2", name = "Preparar los acompañamientos", description = "Corta frutas como fresas, plátanos, manzanas y peras en trozos pequeños. También puedes preparar malvaviscos o galletas para sumergir.", recipe = "Fondue de Chocolate"),
                    Step(number = "3", name = "Montar la fondue", description = "Vierte el chocolate derretido en una fuente de fondue y coloca los acompañamientos alrededor para que todos puedan sumergirlos en el chocolate.", recipe = "Fondue de Chocolate"),
                    Step(number = "4", name = "Disfrutar", description = "Sirve inmediatamente mientras el chocolate sigue caliente. Los invitados pueden sumergir sus frutas y dulces en el chocolate derretido.", recipe = "Fondue de Chocolate")
                )
                dbAccess.stepDao().insertarRecetas(steps)
                setUpRecyclerView2()
            } else {
                setUpRecyclerView2()
            }
        }
    }


    fun setUpRecyclerView() {
        val recipe: Recipe? = intent.getSerializableExtra(ID_PASO_RECETA) as? Recipe
        currentRecipe = recipe
        val nameRecipe = recipe?.name

        binding.recipeName.text = nameRecipe
        binding.textCategory.text = recipe?.category
        binding.textSubcategory1.text = recipe?.subcategory1
        binding.textSubcategory2.text = recipe?.subcategory2
        binding.textTime.text = recipe?.time
        binding.textDifficulty.text = recipe?.dificulty
        binding.textPeople.text = recipe?.quantity
        binding.imageRecipe.setImageResource(recipe?.image ?: R.drawable.lasagna)
        binding.recyclerViewIngredients.visibility = View.VISIBLE
        setFavoriteIcon(recipe?.isFavorite ?: false)

        val listaIngredientes: ArrayList<String> = recipe?.ingredients ?: arrayListOf()
        val listaIngredientes2: MutableList<Ingredient> = mutableListOf()
        for (i in 0 .. listaIngredientes.size-1) {
            val random = Random
            listaIngredientes2.add(Ingredient(name = listaIngredientes[i], amount = "${random.nextInt(1, 10)}"))
        }

        recyclerIngredientAdapter.addDataToList(listaIngredientes2)

        binding.recyclerViewIngredients.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerIngredientAdapter
        }
    }

    fun setUpRecyclerView2() {
        val currentRecipe = intent.getSerializableExtra(ID_PASO_RECETA) as Recipe
        val name = currentRecipe.name
        lifecycleScope.launch {
            val listaDatos = dbAccess.stepDao().obtenerPasosPorReceta(name)
            recyclerStepAdapter.addDataToList(listaDatos)

            binding.recyclerViewProcess.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = recyclerStepAdapter
            }
        }
    }

    private fun toggleFavorite() {
        val currentRecipe = intent.getSerializableExtra(ID_PASO_RECETA) as Recipe

        val newFavoriteStatus = !currentRecipe.isFavorite
        currentRecipe.isFavorite = newFavoriteStatus

        lifecycleScope.launch {
            dbAccess.recipeDao().actualizarFavorito(currentRecipe.id, newFavoriteStatus)
            setFavoriteIcon(newFavoriteStatus)
        }
    }

    private fun setFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.buttonFavorite.setImageResource(R.drawable.favoriterojo)  // Ícono marcado
        } else {
            binding.buttonFavorite.setImageResource(R.drawable.favorite) // Ícono sin marcar
        }
    }

    private fun toggleFavorite2() {
        val currentRecipe = intent.getSerializableExtra(ID_PASO_RECETA) as Recipe

        lifecycleScope.launch {
            val updatedRecipe = dbAccess.recipeDao().obtenerPorId(currentRecipe.id)
            setFavoriteIcon(updatedRecipe.isFavorite)
        }
    }


}