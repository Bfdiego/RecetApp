package com.example.padresdinamicos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.padresdinamicos.dataclasses.Data
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {

    private lateinit var datosAdapter: DatosAdapter
    private val datosList = mutableListOf<Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estilo_formulario)

        val editTextName = findViewById<EditText>(R.id.name)
        val editTextTag = findViewById<EditText>(R.id.tag)
        val editTextCuantity = findViewById<EditText>(R.id.cuantity)
        val editTextCategory = findViewById<EditText>(R.id.category)
        val buttonGuardar = findViewById<Button>(R.id.button_guardar)
        val recyclerView = findViewById<RecyclerView>(R.id.loguardado)


        datosAdapter = DatosAdapter(datosList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = datosAdapter


        cargarDatos()


        buttonGuardar.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val tag = editTextTag.text.toString().trim()
            val quantity = editTextCuantity.text.toString().trim()
            val category = editTextCategory.text.toString().trim()

            if (name.isNotEmpty() && tag.isNotEmpty() && quantity.isNotEmpty() && category.isNotEmpty()) {
                guardarDatos(name, tag, quantity, category)
                datosList.add(Data(name, tag, quantity, category))
                datosAdapter.notifyItemInserted(datosList.size - 1)
                limpiarCampos(editTextName, editTextTag, editTextCuantity, editTextCategory)
            }
        }
    }

    private fun guardarDatos(name: String, tag: String, quantity: String, category: String) {
        val sharedPreferences = getSharedPreferences("activiti_Formulario", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        datosList.add(Data(name, tag, quantity, category))
        val datosJson = Gson().toJson(datosList)
        editor.putString("datos", datosJson)
        editor.apply()
    }

    private fun cargarDatos() {
        val sharedPreferences = getSharedPreferences("activiti_Formulario", MODE_PRIVATE)
        val datosJson = sharedPreferences.getString("datos", null)
        if (!datosJson.isNullOrEmpty()) {
            val type = object : TypeToken<MutableList<Data>>() {}.type
            val loadedDatos = Gson().fromJson<MutableList<Data>>(datosJson, type)
            datosList.addAll(loadedDatos)
            datosAdapter.notifyDataSetChanged()
        }
    }

    private fun limpiarCampos(vararg campos: EditText) {
        campos.forEach { it.text.clear() }
    }
}
