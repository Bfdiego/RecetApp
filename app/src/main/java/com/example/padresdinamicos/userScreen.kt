package com.example.padresdinamicos

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.padresdinamicos.databinding.ActivityUserScreenBinding

class userScreen : AppCompatActivity() {

        private lateinit var binding: ActivityUserScreenBinding
        private lateinit var editor: SharedPreferences.Editor
        private lateinit var sharedPreferences: SharedPreferences

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityUserScreenBinding.inflate(layoutInflater)
            setContentView(binding.root)


            sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
            editor = sharedPreferences.edit()


            binding.edituser.setOnClickListener {
                binding.editTextUser.isEnabled = true
                val userName = sharedPreferences.getString("username", "")
                binding.editTextUser.setText(userName)
                binding.edituser.isEnabled = false
            }

            binding.editcorreo.setOnClickListener {
                binding.editTextCorreo.isEnabled = true
                val userEmail = sharedPreferences.getString("email", "")
                binding.editTextCorreo.setText(userEmail)
                binding.editcorreo.isEnabled = false
            }

            binding.editpassword.setOnClickListener {
                binding.editTextPassword.isEnabled = true
                val userPassword = sharedPreferences.getString("password", "")
                binding.editTextPassword.setText(userPassword)
                binding.editpassword.isEnabled = false
            }


            binding.editTextUser.addTextChangedListener {
                val newUserName = it.toString()
                if (newUserName.isNotEmpty()) {
                    editor.putString("username", newUserName).apply()
                } else {
                    showToast("El nombre de usuario no puede estar vacío")
                }
            }

            binding.editTextCorreo.addTextChangedListener {
                val newUserEmail = it.toString()
                if (newUserEmail.isNotEmpty() && isValidEmail(newUserEmail)) {
                    editor.putString("email", newUserEmail).apply()
                } else {
                    showToast("Correo electrónico inválido")
                }
            }

            binding.editTextPassword.addTextChangedListener {
                val newUserPassword = it.toString()
                if (newUserPassword.length >= 6) {
                    editor.putString("password", newUserPassword).apply()
                } else {
                    showToast("La contraseña debe tener al menos 6 caracteres")
                }
            }
        }

        private fun showToast(message: String) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        private fun isValidEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }

