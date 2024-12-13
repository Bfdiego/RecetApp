package com.example.padresdinamicos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.padresdinamicos.databinding.ItemRecipesByCategoryViewBinding
import com.example.padresdinamicos.dataclasses.Subcategory

class RecyclerCategoryAdapter :
    RecyclerView.Adapter<RecyclerCategoryAdapter.EjemploViewHolder>() {

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

                binding.textViewNombre.text = data.nombre
                binding.textViewFecha.text = data.fechaDeVencimiento
                binding.textViewCantidad.text = data.cantidad.toString()

            }
        }

        fun addDataToList(list: List<Producto>) {
            listaDatos.clear()
            listaDatos.addAll(list)
        }
}