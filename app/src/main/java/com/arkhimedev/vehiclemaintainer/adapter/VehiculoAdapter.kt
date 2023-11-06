package com.arkhimedev.vehiclemaintainer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arkhimedev.vehiclemaintainer.R
import com.arkhimedev.vehiclemaintainer.Vehiculo

class VehiculoAdapter(val listaVehiculo:List<Vehiculo>) : RecyclerView.Adapter<VehiculoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiculoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VehiculoViewHolder(layoutInflater.inflate(R.layout.item_vehiculo,parent,false))
    }

    override fun onBindViewHolder(holder: VehiculoViewHolder, position: Int) {
        val item = listaVehiculo[position]
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return listaVehiculo.size
    }
}