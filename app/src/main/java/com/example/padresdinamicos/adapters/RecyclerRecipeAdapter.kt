package com.example.padresdinamicos.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.padresdinamicos.MenuActivity
import com.example.padresdinamicos.MenuActivity.Companion.ID_PASO_RECETA
import com.example.padresdinamicos.RecipeActivity
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
    interface OnRecipeClickListener {
        fun onRecipeClick(recipe: Recipe)
    }
    inner class EjemploViewHolder(private val binding: ItemRecipesViewBinding) :
        RecyclerView.ViewHolder(binding.root)  {
        fun binding(data: Recipe) {
            binding.buttonViewRecipe.setOnClickListener{
                val intentRecipe = Intent(context, RecipeActivity::class.java)
                intentRecipe.putExtra(ID_PASO_RECETA, data)
                context?.startActivity(intentRecipe)
            }
            binding.buttonViewRecipe.setImageResource(data.image)
            binding.textViewName.text = data.name
        }
    }

    fun addDataToList(list: List<Recipe>) {
        listaDatos.clear()
        listaDatos.addAll(list)
    }
}