package com.example.padresdinamicos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.padresdinamicos.databinding.ItemRecipesByCategoryViewBinding
import com.example.padresdinamicos.dataclasses.Subcategory

class RecyclerSubcategoryAdapter : RecyclerView.Adapter<RecyclerSubcategoryAdapter.EjemploViewHolder>() {

    private val listaDatos = mutableListOf<Subcategory>()
    private var context: Context? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EjemploViewHolder {
        context = parent.context
        return EjemploViewHolder(
            ItemRecipesByCategoryViewBinding.inflate(
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

    inner class EjemploViewHolder(private val binding: ItemRecipesByCategoryViewBinding) :
        RecyclerView.ViewHolder(binding.root)  {
        fun binding(data: Subcategory) {

            binding.textViewName.text = data.name

            val recipeAdapter = RecyclerRecipeAdapter()
            binding.recyclerViewRecipes.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = recipeAdapter
            }

            recipeAdapter.addDataToList(data.recipes)
        }
    }

    fun addDataToList(list: List<Subcategory>) {
        listaDatos.clear()
        listaDatos.addAll(list)
    }


}