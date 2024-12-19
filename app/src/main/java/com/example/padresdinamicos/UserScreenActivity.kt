package com.example.padresdinamicos

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.padresdinamicos.databinding.ActivityUserScreenBinding

class UserScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserScreenBinding
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val options = arrayOf("Foto de perfil 1", "Foto de perfil 2")
        val images = arrayOf(R.drawable.chefsita, R.drawable.chefsito)



        sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)
        editor = sharedPreferences.edit()


        val savedUserName = sharedPreferences.getString("username", "")
        val savedEmail = sharedPreferences.getString("email", "")
        val savedPassword = sharedPreferences.getString("password", "")

        binding.editTextUser.setText(savedUserName)
        binding.editTextCorreo.setText(savedEmail)
        binding.editTextPassword.setText(savedPassword)


        binding.editTextPassword.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD



        binding.menuIcon.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.atras.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.edituser.setOnClickListener {
            binding.editTextUser.isEnabled = true
            binding.edituser.isEnabled = false
        }


        binding.editcorreo.setOnClickListener {
            binding.editTextCorreo.isEnabled = true
            binding.editcorreo.isEnabled = false
        }


        binding.editpassword.setOnClickListener {
            binding.editTextPassword.isEnabled = true
            binding.editpassword.isEnabled = false
        }


        binding.confirmUser.setOnClickListener {
            val newUserName = binding.editTextUser.text.toString()
            if (newUserName.isNotEmpty() && newUserName != savedUserName) {
                editor.putString("username", newUserName).apply()

                binding.editTextUser.setText(newUserName)
                showToast("Nombre de usuario actualizado")
            }
            binding.editTextUser.isEnabled = false
            binding.edituser.isEnabled = true  // Rehabilitar el botón de edición
        }


        binding.confirmCorreo.setOnClickListener {
            val newUserEmail = binding.editTextCorreo.text.toString()
            if (isValidEmail(newUserEmail) && newUserEmail != savedEmail) {
                editor.putString("email", newUserEmail).apply()
                binding.editTextCorreo.setText(newUserEmail)
                showToast("Correo electrónico actualizado")
            } else {
                showToast("Correo electrónico inválido o no modificado")
            }
            binding.editTextCorreo.isEnabled = false
            binding.editcorreo.isEnabled = true  // Rehabilitar el botón de edición
        }


        binding.confirmPassword.setOnClickListener {
            val newUserPassword = binding.editTextPassword.text.toString()
            if (newUserPassword.length >= 6 && newUserPassword != savedPassword) {
                editor.putString("password", newUserPassword).apply()
                binding.editTextPassword.setText(newUserPassword)
                showToast("Contraseña actualizada")
            } else {
                showToast("La contraseña debe tener al menos 6 caracteres o no fue modificada")
            }
            binding.editTextPassword.isEnabled = false
            binding.editpassword.isEnabled = true
        }


        binding.editTextUser.addTextChangedListener {
            val newUserName = it.toString()
            if (newUserName.isNotEmpty()) {
                editor.putString("username", newUserName).apply()
            }
        }

        binding.editTextCorreo.addTextChangedListener {
            val newUserEmail = it.toString()
            editor.putString("email", newUserEmail).apply()
        }

        binding.editTextPassword.addTextChangedListener {
            val newUserPassword = it.toString()
            if (newUserPassword.length >= 6) {
                editor.putString("password", newUserPassword).apply()
            } else {
                showToast("La contraseña debe tener al menos 6 caracteres")
            }

            binding.perfilselect.setOnClickListener {
                val spinner = Spinner(this)
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Selecciona una imagen")
                builder.setView(spinner)

                builder.setPositiveButton("Seleccionar") { dialog, _ ->

                    val selectedPosition = spinner.selectedItemPosition

                    binding.perfilpicture.setImageResource(images[selectedPosition])
                    dialog.dismiss()
                }

                builder.setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.dismiss()
                }

                builder.create().show()
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

