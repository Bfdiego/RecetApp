package com.example.padresdinamicos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.padresdinamicos.databinding.ActivityCategoriesBinding

class CategoriesActivity : AppCompatActivity() {
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
    }
}