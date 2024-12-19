package com.example.padresdinamicos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.padresdinamicos.dataclasses.Intro

class ViewPagerAdapter(
    private val introlist: List<Intro>,
    private val listener: OnItemClickListener
): RecyclerView.Adapter<ViewPagerAdapter.IntroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.intro_item, parent, false)
        return IntroViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return introlist.size
    }

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) {
        holder.bind(introlist[position])
    }

    inner class IntroViewHolder(
        itemView: View,
        private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView) {

        private val contenedor = itemView.findViewById<ViewGroup>(R.id.container)
        private val imagen = itemView.findViewById<ImageView>(R.id.imageView)
        private val titulo = itemView.findViewById<TextView>(R.id.textView_titulo)
        private val descripcion = itemView.findViewById<TextView>(R.id.textView_descripcion)
        private val boton = itemView.findViewById<Button>(R.id.button_start)

        fun bind(intro: Intro) {
            // Establecer el fondo del contenedor
            contenedor.background = ContextCompat.getDrawable(contenedor.context, intro.fondo)

            // Configurar la imagen
            if (intro.image != null) {
                imagen.setImageResource(intro.image)
                imagen.visibility = View.VISIBLE
            } else {
                imagen.visibility = View.GONE
            }

            // Configurar el título y la descripción
            titulo.text = intro.titulo
            descripcion.text = intro.descripcion

            // Mostrar el botón solo en la última posición
            if (adapterPosition == introlist.size - 1) {
                boton.text = intro.botonText ?: "Empezar" // Texto por defecto si es nulo
                boton.visibility = View.VISIBLE
            } else {
                boton.visibility = View.GONE
            }

            // Configurar el clic del botón
            boton.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
