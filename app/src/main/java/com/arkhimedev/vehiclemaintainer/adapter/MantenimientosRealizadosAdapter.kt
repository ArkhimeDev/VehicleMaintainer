package com.arkhimedev.vehiclemaintainer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arkhimedev.vehiclemaintainer.Mantenimiento
import com.arkhimedev.vehiclemaintainer.R

class MantenimientosRealizadosAdapter(val listaMantenimientosRealizados:List<Mantenimiento>):RecyclerView.Adapter<MantenimientosRealizadosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MantenimientosRealizadosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MantenimientosRealizadosViewHolder(layoutInflater.inflate(R.layout.item_mantenimiento_realizado,parent,false))
    }

    override fun onBindViewHolder(holder: MantenimientosRealizadosViewHolder, position: Int) {
        val item=listaMantenimientosRealizados[position]
        holder.renderizado(item)
    }

    override fun getItemCount(): Int {
        return listaMantenimientosRealizados.size
    }
}