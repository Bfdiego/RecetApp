package com.example.padresdinamicos

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.padresdinamicos.databinding.ActivityGuardadoBinding
import com.example.padresdinamicos.databinding.ActivityUserScreenBinding

class UserScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserScreenBinding
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var sharedPreferences: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)
        editor = sharedPreferences.edit()


        val savedUserName = sharedPreferences.getString("username", "")
        val savedEmail = sharedPreferences.getString("email", "")
        val savedPassword = sharedPreferences.getString("password", "")


        binding.editTextUser.setText(savedUserName)
        binding.editTextCorreo.setText(savedEmail)
        binding.editTextPassword.setText(savedPassword)


        binding.editTextPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD



        binding.buttonBack.setOnClickListener {
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


        binding.editTextUser.addTextChangedListener {
            val newUserName = it.toString()
            if (newUserName.isNotEmpty()) {
                editor.putString("username", newUserName).apply()
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
        binding.menuIcon.setOnClickListener {
            val intentMenuActivity = Intent(this, MenuActivity::class.java)
            startActivity(intentMenuActivity)
        }

        binding.buttonCreateRecipe.setOnClickListener{
            val intentCreateRecipe = Intent(this, CreateRecipeActivity::class.java)
            startActivity(intentCreateRecipe)
        }

        binding.categoryIcon.setOnClickListener {
            val intentCategory = Intent(this, CategoriesActivity::class.java)
            startActivity(intentCategory)
        }

        binding.userIcon.setOnClickListener {
            val intentUser = Intent(this, UserScreenActivity::class.java)
            startActivity(intentUser)
        }
        binding.recetIcon.setOnClickListener {
            val intentGuardado = Intent(this, GuardadoActivity::class.java)
            startActivity(intentGuardado)
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
