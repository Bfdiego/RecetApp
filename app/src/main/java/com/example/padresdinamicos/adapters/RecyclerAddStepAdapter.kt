package com.example.padresdinamicos.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.padresdinamicos.databinding.ItemAddStepBinding
import com.example.padresdinamicos.dataclasses.Step

class RecyclerAddStepAdapter : RecyclerView.Adapter<RecyclerAddStepAdapter.StepViewHolder>() {
    private val listaSteps = mutableListOf<Step>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val binding = ItemAddStepBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StepViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        holder.bind(listaSteps[position], position + 1) // Paso de número comienza desde 1
    }

    override fun getItemCount(): Int = listaSteps.size

    fun addEmptyItem() {
        // Añadir un paso vacío
        listaSteps.add(Step(1, "", "", "", ""))
        notifyItemInserted(listaSteps.size - 1) // Asegúrate de notificar el adaptador
    }

    fun getAllSteps(): List<Step> {
        return listaSteps
    }

    inner class StepViewHolder(private val binding: ItemAddStepBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(step: Step, stepNumber: Int) {
            // Actualiza el número del paso
            binding.textNumberStep.text = stepNumber.toString()

            // Rellenar los EditText con los datos actuales del Step
            binding.editTextNameStep.setText(step.name)
            binding.editTextDescriptionStep.setText(step.description)

            // Actualizar el objeto Step cuando los valores cambian en los EditText
            binding.editTextNameStep.addTextChangedListener {
                step.name = it.toString()
            }

            binding.editTextDescriptionStep.addTextChangedListener {
                step.description = it.toString()
            }
        }
    }
}

