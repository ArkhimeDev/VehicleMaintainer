package com.arkhimedev.vehiclemaintainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.arkhimedev.vehiclemaintainer.Mantenimiento.Companion.MANTENIMIENTO_PROGRAMADO
import com.arkhimedev.vehiclemaintainer.Mensaje.Companion.NO_LEIDO

class ActualizarKilometrajeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_kilometraje)

        val btnAceptar = findViewById<Button>(R.id.btnAceptar)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)
        val etKilometraje = findViewById<EditText>(R.id.etKilometraje)
        val bundle = intent.extras
        val matricula = bundle?.getString("matricula")
        val miSqlHelper=MiSqlHelper(this)
        val vehiculo = miSqlHelper.seleccionarVehiculo(matricula!!)

        btnCancelar.setOnClickListener(){
            this.finish()
        }
        btnAceptar.setOnClickListener(){
            if(etKilometraje.text.isEmpty()){
                Toast.makeText(this, "Introduce un kilometraje", Toast.LENGTH_LONG).show()
            }
            else{
                if(etKilometraje.text.toString().toInt() <= vehiculo!!.kilometraje!!){
                    Toast.makeText(this, "El kilometraje introducido no puede ser menor o igual al del vehiculo", Toast.LENGTH_LONG).show()
                }else{
                    miSqlHelper.actualizarKilometraje(matricula,etKilometraje.text.toString().toInt())
                    val vehiculoActualizado = miSqlHelper.seleccionarVehiculo(matricula!!)
                    Toast.makeText(this, "El kilometraje ha sido actualizado", Toast.LENGTH_LONG).show()
                    val listaMensajes = miSqlHelper.seleccionarListaMensaje()
                    var mensajeNuevo = false

                    if(listaMensajes!=null){
                        for (mensaje in listaMensajes) {
                            if(mensaje.kilometraje!! <= vehiculoActualizado!!.kilometraje!! && mensaje.kilometraje!=0 && mensaje.estado==NO_LEIDO){
                                mensajeNuevo = true
                            }
                        }
                        if(mensajeNuevo){
                            mostrarAlerta()
                        }

                        else{
                            val intent= Intent(this,VehiculoActivity::class.java)
                            intent.putExtra("matricula",matricula)
                            startActivity(intent)
                        }

                    }
                    if(listaMensajes==null){
                        val intent= Intent(this,VehiculoActivity::class.java)
                        intent.putExtra("matricula",matricula)
                        startActivity(intent)
                    }
                }
            }
        }
    }
    fun mostrarAlerta(){
        AlertDialog.Builder(this)
            .setTitle("Mantenimiento a realizar")
            .setMessage("Tienes un nuevo mantenimiento a realizar")
            .setPositiveButton(R.string.txt_aceptar){ dialog, which ->
                val intent = Intent(this,MensajeActivity::class.java)
                ContextCompat.startActivity(this, intent, null)
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
}