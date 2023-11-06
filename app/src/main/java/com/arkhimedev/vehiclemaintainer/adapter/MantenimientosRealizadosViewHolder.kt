package com.arkhimedev.vehiclemaintainer.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arkhimedev.vehiclemaintainer.Mantenimiento
import com.arkhimedev.vehiclemaintainer.R

class MantenimientosRealizadosViewHolder(view:View):RecyclerView.ViewHolder(view) {

    val fecha = view.findViewById<TextView>(R.id.tvFecha)
    val descripcion = view.findViewById<TextView>(R.id.tvDescripcion)
    val kilometros = view.findViewById<TextView>(R.id.tvKilometros)
    val importe = view.findViewById<TextView>(R.id.tvImporte)

    fun renderizado(mantenimiento:Mantenimiento){
        fecha.text=mantenimiento.fecha
        descripcion.text=mantenimiento.descripcion
        kilometros.text=mantenimiento.kilometraje.toString()
        importe.text=mantenimiento.importe.toString()
    }
}