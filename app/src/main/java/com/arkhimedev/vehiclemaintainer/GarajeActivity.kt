package com.arkhimedev.vehiclemaintainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arkhimedev.vehiclemaintainer.Mantenimiento.Companion.MANTENIMIENTO_PROGRAMADO
import com.arkhimedev.vehiclemaintainer.Mantenimiento.Companion.MANTENIMIENTO_REALIZADO
import com.arkhimedev.vehiclemaintainer.adapter.VehiculoAdapter

class GarajeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_garaje)



        val btnListMantProgramados = findViewById<ImageButton>(R.id.btnListMantProgramados)
        val btnNuevoVehiculo = findViewById<ImageButton>(R.id.btnNuevoVehiculo)
        val btnMensajes = findViewById<ImageButton>(R.id.btnMensajes)
        val tvNumeroMensajes = findViewById<TextView>(R.id.tvNumeroMensajes)

        tvNumeroMensajes.isVisible = false

        iniciarRecyclerView()

        btnListMantProgramados.setOnClickListener{
            val intent = Intent(this, MantenimientosProgramadosActivity::class.java)
            startActivity(intent)
        }

        btnNuevoVehiculo.setOnClickListener {
            val intent = Intent(this, RegistrarVehiculoActivity::class.java)
            startActivity(intent)
        }

        btnMensajes.setOnClickListener {
            val intent = Intent(this, MensajeActivity::class.java)
            startActivity(intent)
        }
    }
    fun iniciarRecyclerView(){

        val miSqlHelper = MiSqlHelper(this)
        val listaVehiculo = miSqlHelper.seleccionarListaVehiculos()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerVehiculo)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = listaVehiculo?.let { VehiculoAdapter(it) }
    }
}