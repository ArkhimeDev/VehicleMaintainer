package com.arkhimedev.vehiclemaintainer.adapter


import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.arkhimedev.vehiclemaintainer.*

class VehiculoViewHolder(view:View):RecyclerView.ViewHolder(view){

    val marca = view.findViewById<TextView>(R.id.tvMarca)
    val modelo = view.findViewById<TextView>(R.id.tvModelo)
    val matricula = view.findViewById<TextView>(R.id.tvMatricula)
    val btnEliminar = view.findViewById<ImageButton>(R.id.btnEliminar)
    val btnSeleccionarVehiculo = view.findViewById<ImageButton>(R.id.btnSeleccionarVehiculo)
    val miSqlHelper = MiSqlHelper(btnEliminar.context)

    fun render(vehiculo:Vehiculo){
        marca.text = vehiculo.marca
        modelo.text = vehiculo.modelo
        matricula.text = vehiculo.matricula
        btnEliminar.setOnClickListener {
            AlertDialog.Builder(btnEliminar.context)
                .setTitle("Eliminar vehículo")
                .setMessage("¿Esatá seguro de que desea eliminar el vehículo?")
                .setPositiveButton(R.string.txt_aceptar){ dialog, which ->
                    miSqlHelper.borrarVehiculo(vehiculo.matricula!!)
                    Toast.makeText(btnEliminar.context,"Vehículo eliminado", Toast.LENGTH_SHORT).show()
                    val intent = Intent(btnEliminar.context,GarajeActivity::class.java)
                    startActivity(btnEliminar.context,intent,null)
                }
                .setNegativeButton(R.string.txt_cancelar,null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()

        }
        btnSeleccionarVehiculo.setOnClickListener {
            val intent=  Intent(btnSeleccionarVehiculo.context,VehiculoActivity::class.java)
            intent.putExtra("matricula",vehiculo.matricula)
            startActivity(btnSeleccionarVehiculo.context,intent,null)

        }
    }

}