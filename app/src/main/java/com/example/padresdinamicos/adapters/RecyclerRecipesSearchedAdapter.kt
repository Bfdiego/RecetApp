package com.example.padresdinamicos.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.padresdinamicos.MenuActivity.Companion.ID_PASO_RECETA
import com.example.padresdinamicos.RecipeActivity
import com.example.padresdinamicos.databinding.ItemRecipesSearchedBinding
import com.example.padresdinamicos.databinding.ItemRecipesViewBinding
import com.example.padresdinamicos.dataclasses.Recipe

class RecyclerRecipesSearchedAdapter: RecyclerView.Adapter<RecyclerRecipesSearchedAdapter.EjemploViewHolder>() {

    private val listaDatos = mutableListOf<Recipe>()
    private var context: Context? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EjemploViewHolder {
        context = parent.context
        return EjemploViewHolder(
            ItemRecipesSearchedBinding.inflate(
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

    inner class EjemploViewHolder(private val binding: ItemRecipesSearchedBinding) :
        RecyclerView.ViewHolder(binding.root)  {
        fun binding(data: Recipe) {
            binding.imageButtonRecipe.setOnClickListener{
                val intentRecipe = Intent(context, RecipeActivity::class.java)
                intentRecipe.putExtra(ID_PASO_RECETA, data)
                context?.startActivity(intentRecipe)
            }
            binding.imageButtonRecipe.setImageResource(data.image)
            binding.textViewName.text = data.name

        }
    }

    fun updateRecipes(newRecipes: List<Recipe>) {
        val recipes = newRecipes
        notifyDataSetChanged()  // Esto actualiza el RecyclerView
    }

    fun addDataToList(list: List<Recipe>) {
        listaDatos.clear()
        listaDatos.addAll(list)
    }

}