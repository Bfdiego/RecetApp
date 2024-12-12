package com.example.padresdinamicos

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2

class BienvenidaActivity : AppCompatActivity() {
private lateinit var introList: List<Intro>
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)


        introList = listOf(
            Intro(
                fondo = R.drawable.acercaderecetapp,
                image = R.color.backgroundrecetapp,
                titulo = "¡Bienvenid@ a RecetApp!",
                descripcion = "Tu compañera ideal en la cocina. \n" +
                        "Descubre recetas y guarda\n" +
                        " tus favoritas, todo sin conexión"
            ),
            Intro(
                fondo = R.drawable.acercaderecetapp2,
                titulo = "Recetas para Cada Momento",
                descripcion = "Desde desayunos rápidos hasta \n" +
                        "cenas especiales, encuentra las\n" +
                        " recetas perfectas para tu día a día"
            ),
            Intro(
                fondo = R.drawable.acercaderecetapp3,
                titulo = "¡Comencemos!",
                descripcion = "Desde desayunos rápidos hasta \n" +
                        "cenas especiales, encuentra las\n" +
                        " recetas perfectas para tu día a día",
                botonText = "¡Empieza!"
            )
        )
        val adapter = ViewPagerAdapter(introList)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = adapter

    }
}