package com.example.padresdinamicos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.padresdinamicos.R
import com.example.padresdinamicos.databinding.ItemRecipesViewBinding
import com.example.padresdinamicos.dataclasses.Recipe

class RecyclerRecipeAdapter :
    RecyclerView.Adapter<RecyclerRecipeAdapter.EjemploViewHolder>() {

    private val listaDatos = mutableListOf<Recipe>()
    private var context: Context? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EjemploViewHolder {
        context = parent.context
        return EjemploViewHolder(
            ItemRecipesViewBinding.inflate(
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

    inner class EjemploViewHolder(private val binding: ItemRecipesViewBinding) :
        RecyclerView.ViewHolder(binding.root)  {
        fun binding(data: Recipe) {

            //binding.imageViewRecipe.setImageResource(data.image)
            binding.textViewName.text = data.name

        }
    }

    fun addDataToList(list: List<Recipe>) {
        listaDatos.clear()
        listaDatos.addAll(list)
    }
}