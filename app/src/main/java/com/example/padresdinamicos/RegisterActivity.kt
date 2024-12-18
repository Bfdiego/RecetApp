package com.example.padresdinamicos

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.padresdinamicos.databinding.ActivityRegisterBinding



class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding // Usamos View Binding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)

       binding.buttonLogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()


            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showToast("Por favor, complete todos los campos")
                return@setOnClickListener
            }


            if (password != confirmPassword) {
                showToast("Las contraseñas no coinciden")
                return@setOnClickListener
            }


            if (password.length < 6) {
                showToast("La contraseña debe tener al menos 6 caracteres")
                return@setOnClickListener
            }


            if (!isValidEmail(email)) {
                showToast("El correo electrónico no es válido")
                return@setOnClickListener
            }


            sharedPreferences.edit()
                .putString("email", email)
                .putString("password", password)
                .apply()


            showToast("¡Registro exitoso! Ahora inicie sesión.")


            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
