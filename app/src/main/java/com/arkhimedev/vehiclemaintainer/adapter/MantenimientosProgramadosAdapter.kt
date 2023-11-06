package com.arkhimedev.vehiclemaintainer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arkhimedev.vehiclemaintainer.Mantenimiento
import com.arkhimedev.vehiclemaintainer.R

class MantenimientosProgramadosAdapter(val listaMantenimientosProgramados:List<Mantenimiento>):RecyclerView.Adapter<MantenimientosProgramadosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MantenimientosProgramadosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MantenimientosProgramadosViewHolder(layoutInflater.inflate(R.layout.item_mantenimiento_programado,parent,false))
    }

    override fun onBindViewHolder(holder: MantenimientosProgramadosViewHolder, position: Int) {
        val item=listaMantenimientosProgramados[position]
        holder.renderizado(item)
    }

    override fun getItemCount(): Int {
        return listaMantenimientosProgramados.size
    }
}