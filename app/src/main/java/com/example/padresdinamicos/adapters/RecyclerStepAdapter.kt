package com.example.padresdinamicos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.padresdinamicos.databinding.ItemIngredientBinding
import com.example.padresdinamicos.databinding.ItemStepBinding
import com.example.padresdinamicos.dataclasses.Step

class RecyclerStepAdapter: RecyclerView.Adapter<RecyclerStepAdapter.EjemploViewHolder>() {
    private val listaDatos = mutableListOf<Step>()
    private var context: Context? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EjemploViewHolder {
        context = parent.context
        return EjemploViewHolder(
            ItemStepBinding.inflate(
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

    inner class EjemploViewHolder(private val binding: ItemStepBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun binding(data: Step) {
            binding.textNumberStep.text = data.number
            binding.textViewNameStep.text = data.name
            binding.textViewDescriptionStep.text = data.description
        }
    }

    fun addDataToList(data: List<Step>) {
        listaDatos.clear()
        listaDatos.addAll(data)
    }
}