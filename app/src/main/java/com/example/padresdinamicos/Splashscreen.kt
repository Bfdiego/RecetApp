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

class Splashscreen : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        val imageView = findViewById<ImageView>(R.id.gifSplash)
        Glide.with(this)
            .asGif()
            .load(R.drawable.splashscreengif)
            .into(imageView)



        sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)

        Handler(Looper.getMainLooper()).postDelayed( {

            if (sharedPreferences.getBoolean("isLoggedIn", false)) {

                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            } else {

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, 4000)
    }
}