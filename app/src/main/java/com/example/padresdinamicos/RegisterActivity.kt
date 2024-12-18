package com.example.padresdinamicos


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.padresdinamicos.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)

        // Enlazar vistas
        editTextEmail = findViewById(R.id.edit_text_email)
        editTextPassword = findViewById(R.id.edit_text_password)
        editTextConfirmPassword = findViewById(R.id.edit_text_confirm_password)
        buttonRegister = findViewById(R.id.button_login)

        // Acción al hacer clic en el botón de registro
        buttonRegister.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val confirmPassword = editTextConfirmPassword.text.toString().trim()

            // Validación de campos vacíos
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validación de contraseña
            if (password != confirmPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Guardar las credenciales en SharedPreferences
            sharedPreferences.edit()
                .putString("email", email)
                .putString("password", password)
                .apply()

            // Confirmación de registro exitoso
            Toast.makeText(this, "¡Registro exitoso! Ahora inicie sesión.", Toast.LENGTH_SHORT).show()

            // Navegar de regreso a LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}