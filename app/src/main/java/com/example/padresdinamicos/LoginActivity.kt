package com.example.padresdinamicos


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.padresdinamicos.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonsignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)

        editTextEmail = findViewById(R.id.edit_text_email)
        editTextPassword = findViewById(R.id.edit_text_password)
        buttonLogin = findViewById(R.id.button_login)
        buttonsignUp = findViewById(R.id.sign_up)


        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            val savedEmail = sharedPreferences.getString("email", null)
            val savedPassword = sharedPreferences.getString("password", null)

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (email == savedEmail && password == savedPassword) {

                Toast.makeText(this, "¡Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show()


                val editor = sharedPreferences.edit()
                editor.putBoolean("isLoggedIn", true) // Usuario loggeado
                editor.apply()


                val intent = Intent(this, MenuActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()

            } else {

                Toast.makeText(this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        
        buttonsignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}