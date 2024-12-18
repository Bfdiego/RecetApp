package com.example.padresdinamicos


import android.content.Intent
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.padresdinamicos.dataclasses.Intro

class BienvenidaActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)

        // Lista de intros
        val introList = listOf(
            Intro(
                image = R.drawable.recetapplogo2,
                fondo = R.drawable.acercaderecetapp,
                titulo = "Descubre recetas y guarda\n" +
                        "tus favoritas, todo sin conexión",
                descripcion = ""
            ),
            Intro(
                fondo = R.drawable.acercaderecetapp2,
                titulo = "Recetas para Cada Momento",
                descripcion = "Desde desayunos rápidos hasta \n" +
                        "cenas especiales, encuentra las\n" +
                        "recetas perfectas para tu día a día"
            ),
            Intro(
                fondo = R.drawable.acercaderecetapp3,
                titulo = "¡Comencemos!",
                descripcion = "Desde desayunos rápidos hasta \n" +
                        "cenas especiales, encuentra las\n" +
                        "recetas perfectas para tu día a día",
                botonText = "¡Empieza!"

            )
        )

        // Referencia al ViewPager2
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        // Adapter con implementación del listener
        val adapter = ViewPagerAdapter(introList, object : ViewPagerAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                if (position == introList.size - 1) { // Verifica si es la última posición
                    val intent = Intent(this@BienvenidaActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        })

        // Asignar el adaptador al ViewPager
        viewPager.adapter = adapter
    }
}
