package com.example.padresdinamicos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.padresdinamicos.databinding.ItemIngredientBinding
import com.example.padresdinamicos.dataclasses.Ingredient

class RecyclerIngredientsAdapter: RecyclerView.Adapter<RecyclerIngredientsAdapter.EjemploViewHolder>() {
    private val listaDatos = mutableListOf<Ingredient>()
    private var context: Context? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EjemploViewHolder {
        context = parent.context
        return EjemploViewHolder(
            ItemIngredientBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EjemploViewHolder, position: Int) {
        holder.binding(listaDatos[position])
    }

    override fun getItemCount(): Int = listaDatos.size

    inner class EjemploViewHolder(private val binding: ItemIngredientBinding) :
            RecyclerView.ViewHolder(binding.root)  {
        fun binding(data: Ingredient) {
            binding.textAmount.text = data.amount
            binding.textViewName.text = data.name
        }
    }

    fun addDataToList(data: List<Ingredient>) {
        listaDatos.clear()
        listaDatos.addAll(data)
    }

}