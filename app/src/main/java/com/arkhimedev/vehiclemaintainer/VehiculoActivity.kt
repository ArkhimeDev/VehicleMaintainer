package com.arkhimedev.vehiclemaintainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

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

        Toast.makeText(this, matricula, Toast.LENGTH_SHORT).show()
        if (matricula != null) {
            if (miSqlHelper.seleccionarVehiculo(matricula)!=null){
                val vehiculo = miSqlHelper.seleccionarVehiculo(matricula)
                tvMarca.setText(vehiculo?.marca)
                tvModelo.setText(vehiculo?.modelo)
                tvMatricula.setText(vehiculo?.matricula)
                tvKilometraje.setText(vehiculo?.kilometraje.toString())
                tvFechaMatriculacion.setText(vehiculo?.fechaMatriculacion)
                tvTipoCombustible.setText(vehiculo?.tipoCombustible)
                tvNumeroBastidor.setText(vehiculo?.numeroBastidor)
            }
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

}