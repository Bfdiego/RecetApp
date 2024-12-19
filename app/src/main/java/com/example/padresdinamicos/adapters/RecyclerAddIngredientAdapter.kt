package com.example.padresdinamicos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.padresdinamicos.databinding.ItemAddIngredientBinding
import com.example.padresdinamicos.databinding.ItemIngredientBinding
import com.example.padresdinamicos.dataclasses.Ingredient

class RecyclerAddIngredientAdapter :
    RecyclerView.Adapter<RecyclerAddIngredientAdapter.IngredientViewHolder>() {

    private val listaIngredientes = mutableListOf<Ingredient>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding = ItemAddIngredientBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(listaIngredientes[position])
    }

    override fun getItemCount(): Int = listaIngredientes.size

    fun addEmptyItem() {
        listaIngredientes.add(Ingredient("", "")) // Añadir un ingrediente vacío
        notifyItemInserted(listaIngredientes.size - 1)
    }

    fun getAllIngredients(): List<Ingredient> {
        return listaIngredientes
    }

    inner class IngredientViewHolder(private val binding: ItemAddIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: Ingredient) {
            // Actualizar los EditText si ya existe un valor
            binding.editTextAmount.setText(ingredient.amount)
            binding.editTextIngredient.setText(ingredient.name)

            // Detectar cambios en los EditText y actualizar la lista
            binding.editTextAmount.addTextChangedListener {
                ingredient.amount = it.toString()
            }
            binding.editTextIngredient.addTextChangedListener {
                ingredient.name = it.toString()
            }
        }
    }
}
