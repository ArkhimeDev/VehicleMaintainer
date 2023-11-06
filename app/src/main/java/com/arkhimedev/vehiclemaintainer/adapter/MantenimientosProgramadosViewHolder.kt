package com.arkhimedev.vehiclemaintainer.adapter

import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.arkhimedev.vehiclemaintainer.Mantenimiento
import com.arkhimedev.vehiclemaintainer.MantenimientosProgramadosActivity
import com.arkhimedev.vehiclemaintainer.MiSqlHelper
import com.arkhimedev.vehiclemaintainer.R

class MantenimientosProgramadosViewHolder(view: View): RecyclerView.ViewHolder(view)  {
    val matricula = view.findViewById<TextView>(R.id.tvMatricula)
    val tipoMantenimiento = view.findViewById<TextView>(R.id.tvTipoMantenimiento)
    val descripcion = view.findViewById<TextView>(R.id.tvDescripcion)
    val btnEliminar = view.findViewById<ImageButton>(R.id.btnEliminar)
    val miSqlHelper = MiSqlHelper(view.context)

    fun renderizado(mantenimiento: Mantenimiento){
        matricula.setText("Vehiculo con matrícula ${mantenimiento.matricula}")
        if(mantenimiento.fecha!=null){
            tipoMantenimiento.setText("Mantenimiento programado el ${mantenimiento.fecha}")
        }
        else{
            tipoMantenimiento.setText("Mantenimiento programado a los ${mantenimiento.kilometraje} km")
        }
        descripcion.text=mantenimiento.descripcion.toString()

        btnEliminar.setOnClickListener(){
            AlertDialog.Builder(btnEliminar.context)
                .setTitle("Eliminar mantenimiento programado")
                .setMessage("¿Esatá seguro de que desea eliminar el mantenimiento programado?")
                .setPositiveButton(R.string.txt_aceptar){ dialog, which ->
                    miSqlHelper.borrarMantenimiento(mantenimiento.idMantenimiento!!)
                    Toast.makeText(btnEliminar.context,"Mantenimiento eliminado",Toast.LENGTH_SHORT).show()
                    val intent = Intent(btnEliminar.context, MantenimientosProgramadosActivity::class.java)
                    startActivity(btnEliminar.context,intent,null)
                }
                .setNegativeButton(R.string.txt_cancelar,null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }
    }
}