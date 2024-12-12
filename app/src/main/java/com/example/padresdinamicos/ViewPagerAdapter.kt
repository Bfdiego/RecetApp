package com.example.padresdinamicos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ViewPagerAdapter(
    private val introlist: List<Intro>
): RecyclerView.Adapter<ViewPagerAdapter.IntroViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.intro_item, parent, false)
        return IntroViewHolder(view)
    }

    override fun getItemCount(): Int {
        return introlist.size
    }

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) {
        holder.bind(introlist[position])
    }
    inner class IntroViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val contenedor = itemView.findViewById<ViewGroup>(R.id.container)
        private val imagen = itemView.findViewById<ImageView>(R.id.imageView)
        private val titulo = itemView.findViewById<TextView>(R.id.textView_titulo)
        private val descripcion = itemView.findViewById<TextView>(R.id.textView_descripcion)
        private val boton = itemView.findViewById<Button>(R.id.button_start)


        fun bind(intro: Intro) {
            contenedor.background = ContextCompat.getDrawable(contenedor.context, intro.fondo)

            if (intro.image != null) {
                imagen.setImageResource(intro.image)
                imagen.visibility = View.VISIBLE
            } else {
                imagen.visibility = View.GONE
            }

            titulo.text = intro.titulo
            descripcion.text = intro.descripcion

            if (intro.botonText != null) {
                boton.text = intro.botonText
                boton.visibility = View.VISIBLE
            } else {
                boton.visibility = View.GONE
            }
        }
    }
}