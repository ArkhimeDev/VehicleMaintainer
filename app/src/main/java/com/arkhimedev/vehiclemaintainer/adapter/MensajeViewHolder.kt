package com.arkhimedev.vehiclemaintainer.adapter

import android.content.Intent
import android.view.View
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.arkhimedev.vehiclemaintainer.*
import com.arkhimedev.vehiclemaintainer.Mensaje.Companion.LEIDO
import com.arkhimedev.vehiclemaintainer.Mensaje.Companion.NO_LEIDO

class MensajeViewHolder(view: View):RecyclerView.ViewHolder(view) {

    val cbLeido = view.findViewById<CheckBox>(R.id.cbLeido)
    val tvDescripcion = view.findViewById<TextView>(R.id.tvDescripcion)
    val btnEliminar = view.findViewById<ImageButton>(R.id.btnEliminar)
    val miSqlHelper = MiSqlHelper(view.context)
    val inicioDescripcion = "Tienes que realizar un mantenimiento programado con descripción: "
    var vehiculoString = ""
    var finalDascripcion = ""

    fun render(mensaje: Mensaje){
        val vehiculo = miSqlHelper.seleccionarVehiculoIdMantenimiento(mensaje.idMantenimiento!!)
        vehiculoString = "${vehiculo!!.marca}${vehiculo!!.modelo}con matrícula ${vehiculo!!.matricula}"
        if(mensaje.fecha!=null){
            finalDascripcion = "\nFecha programada para el ${mensaje.fecha}."
        }
        else{
            finalDascripcion = "\nKilometraje programado a los ${mensaje.kilometraje} km."
        }
        tvDescripcion.setText(vehiculoString+"\n"+inicioDescripcion+mensaje.mensaje+finalDascripcion)
        if(mensaje.estado == LEIDO){
            cbLeido.isChecked = true
        }

        cbLeido.setOnClickListener(){
            if(cbLeido.isChecked){
                       miSqlHelper.actualizarEstadoMensaje(mensaje.idMensaje!!,LEIDO)
                       Toast.makeText(tvDescripcion.context,"Mensaje leído",Toast.LENGTH_LONG)
                   }
                   else{
                       miSqlHelper.actualizarEstadoMensaje(mensaje.idMensaje!!, NO_LEIDO)
                       Toast.makeText(tvDescripcion.context,"Mensaje no leído",Toast.LENGTH_LONG)
                   }
               }

               tvDescripcion.setOnClickListener(){
                   if(cbLeido.isChecked){
                       miSqlHelper.actualizarEstadoMensaje(mensaje.idMensaje!!,LEIDO)
                       Toast.makeText(tvDescripcion.context,"Mensaje leído",Toast.LENGTH_LONG)
                   }
                   else{
                       miSqlHelper.actualizarEstadoMensaje(mensaje.idMensaje!!, NO_LEIDO)
                       Toast.makeText(tvDescripcion.context,"Mensaje no leído",Toast.LENGTH_LONG)
                   }
               }

        btnEliminar.setOnClickListener(){
            AlertDialog.Builder(btnEliminar.context)
                .setTitle("Eliminar mensaje")
                .setMessage("¿Esatá seguro de que desea eliminar el mensaje?")
                .setPositiveButton(R.string.txt_aceptar){ dialog, which ->
                    miSqlHelper.borrarMensaje(mensaje.idMensaje!!)
                    miSqlHelper.borrarMantenimiento(mensaje.idMantenimiento!!)
                    Toast.makeText(btnEliminar.context,"Mensaje eliminado",Toast.LENGTH_SHORT).show()
                    val intent = Intent(btnEliminar.context, MensajeActivity::class.java)
                    startActivity(btnEliminar.context,intent,null)
                }
                .setNegativeButton(R.string.txt_cancelar,null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()

        }

    }
}