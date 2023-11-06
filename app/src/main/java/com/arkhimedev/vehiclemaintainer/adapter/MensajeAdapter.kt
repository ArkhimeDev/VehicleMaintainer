package com.arkhimedev.vehiclemaintainer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arkhimedev.vehiclemaintainer.Mensaje
import com.arkhimedev.vehiclemaintainer.R

class MensajeAdapter(val listaMensaje: List<Mensaje>):RecyclerView.Adapter<MensajeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MensajeViewHolder(layoutInflater.inflate(R.layout.item_mensaje,parent,false))
    }

    override fun onBindViewHolder(holder: MensajeViewHolder, position: Int) {
        val item = listaMensaje.get(position)
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return listaMensaje.count()
    }
}