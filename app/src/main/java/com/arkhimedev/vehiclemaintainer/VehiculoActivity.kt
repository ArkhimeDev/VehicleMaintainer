package com.arkhimedev.vehiclemaintainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class VehiculoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehiculo)

        val btnGaraje = findViewById<ImageButton>(R.id.btnGaraje)
        val btnProgramarMantenimiento = findViewById<ImageButton>(R.id.btnProgramarMantenimiento)
        val btnActualizarKilometraje = findViewById<ImageButton>(R.id.btnActualizarKilometraje)
        val btnListMantRealizados = findViewById<ImageButton>(R.id.btnListMantRealizados)
        val btnNuevoMantenimiento = findViewById<ImageButton>(R.id.btnNuevoMantenimiento)
        val tvMarca = findViewById<TextView>(R.id.tvMarca)
        val tvModelo = findViewById<TextView>(R.id.tvModelo)
        val tvMatricula = findViewById<TextView>(R.id.tvMatricula)
        val tvKilometraje = findViewById<TextView>(R.id.tvKilometraje)
        val tvFechaMatriculacion = findViewById<TextView>(R.id.tvFechaMatriculacion)
        val tvTipoCombustible = findViewById<TextView>(R.id.tvTipoCombustible)
        val tvNumeroBastidor = findViewById<TextView>(R.id.tvNumeroBastidor)

        val bundle = intent.extras
        val matricula = bundle?.getString("matricula")
        val miSqlHelper = MiSqlHelper(this)

        //Toast.makeText(this, matricula, Toast.LENGTH_SHORT).show()

        val vehiculo = miSqlHelper.seleccionarVehiculo(matricula!!)
        tvMarca.setText(vehiculo?.marca)
        tvModelo.setText(vehiculo?.modelo)
        tvMatricula.setText(vehiculo?.matricula)
        tvKilometraje.setText(vehiculo?.kilometraje.toString())
        tvFechaMatriculacion.setText(vehiculo?.fechaMatriculacion)
        tvTipoCombustible.setText(vehiculo?.tipoCombustible)
        tvNumeroBastidor.setText(vehiculo?.numeroBastidor)

        val listaMantenimiento = miSqlHelper.seleccionarListaMantenimientoVehiculo(matricula!!,
            Mantenimiento.MANTENIMIENTO_PROGRAMADO
        )
        var numeroMantenimientos = 0
        if (listaMantenimiento != null) {
            numeroMantenimientos = compararKilometraje(vehiculo!!.kilometraje!!,listaMantenimiento)
        }
        if(numeroMantenimientos>=1){
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

        //Botón para volver a la pantalla principal
        btnGaraje.setOnClickListener {
            val intent = Intent(this, GarajeActivity::class.java)
            startActivity(intent)
        }
        //Botón para actualizar el kilometraje
        btnActualizarKilometraje.setOnClickListener {
            val intent = Intent(this, ActualizarKilometrajeActivity::class.java)
            intent.putExtra("matricula",matricula)
            startActivity(intent)
        }
        //Botón para añadir un nuevo mantenimiento
        btnNuevoMantenimiento.setOnClickListener(){
            val intent = Intent(this,RegistrarMantenimientoActivity::class.java)
            intent.putExtra("matricula",matricula)
            startActivity(intent)
        }
        //Botón para ver todos los mantenimientos realizados
        btnListMantRealizados.setOnClickListener(){
            val intent = Intent(this,MantenimientosRealizadosActivity::class.java)
            intent.putExtra("matricula",matricula)
            startActivity(intent)
        }

        btnProgramarMantenimiento.setOnClickListener(){
            val intent = Intent(this,ProgramarMantenimientoActivity::class.java)
            intent.putExtra("matricula",matricula)
            startActivity(intent)
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