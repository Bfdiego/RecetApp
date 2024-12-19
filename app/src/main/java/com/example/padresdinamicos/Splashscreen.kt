package com.example.padresdinamicos

import android.content.Intent
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class Splashscreen : BaseActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        val imageView = findViewById<ImageView>(R.id.gifSplash)
        Glide.with(this)
            .asGif()
            .load(R.drawable.splashscreengif)
            .into(imageView)


        // Obtener SharedPreferences para verificar la sesión
        sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)

        Handler(Looper.getMainLooper()).postDelayed( {
            // Verificar si la sesión está activa
            if (sharedPreferences.getBoolean("isLoggedIn", false)) {
                // Si está logueado, ir a MenuActivity
                val intentMenu = Intent(this, MenuActivity::class.java)
                startActivity(intentMenu)
            } else {
                // Si no está logueado, ir a LoginActivity
                val intentBienvenidaActivity = Intent(this,BienvenidaActivity::class.java)
                startActivity(intentBienvenidaActivity)
            }
            finish()
        }, 4000)
    }
}