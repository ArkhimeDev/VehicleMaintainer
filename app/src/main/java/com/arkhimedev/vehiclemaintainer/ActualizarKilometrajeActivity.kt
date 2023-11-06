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
                if(etKilometraje.text.toString().toInt() < vehiculo!!.kilometraje!!){
                    Toast.makeText(this, "El kilometraje introducido no puede ser menor al del vehiculo", Toast.LENGTH_LONG).show()
                }else{
                    miSqlHelper.actualizarKilometraje(matricula,etKilometraje.text.toString().toInt())
                    this.finish()
                    val intent= Intent(this,VehiculoActivity::class.java)
                    intent.putExtra("matricula",matricula)
                    startActivity(intent)
                    Toast.makeText(this, "El kilometraje ha sido actualizado", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    //Función que compara el kilometraje actual del vehículo con los kilometrajes de mantenimientos
    //pasando por parametro un kilometraje y una lista de mantenimientos
    fun compararKilometraje(kiometraje:Int,listaMantenimiento: List<Mantenimiento>):Int{
        val lista = mutableListOf<Mantenimiento>()
        for(mantenimiento in listaMantenimiento) {
            if (mantenimiento.kilometraje != null && kiometraje >= mantenimiento.kilometraje!!) {
                lista.add(mantenimiento)
            }
        }
        return  lista.size
    }
}