package com.example.padresdinamicos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.padresdinamicos.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(R.layout.activity_login)
        auth = Firebase.auth
    }

    fun loginUser() {
        val password: String = binding.editTextPassword.text.toString()
        val email: String = binding.editTextEmail.text.toString()
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
            answer -> if(answer.isSuccessful){
                } else {

                }
        }
    }
}