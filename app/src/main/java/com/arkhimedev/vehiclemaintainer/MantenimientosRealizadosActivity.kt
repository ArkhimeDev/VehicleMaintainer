package com.arkhimedev.vehiclemaintainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arkhimedev.vehiclemaintainer.Mantenimiento.Companion.MANTENIMIENTO_REALIZADO
import com.arkhimedev.vehiclemaintainer.R.*
import com.arkhimedev.vehiclemaintainer.adapter.MantenimientosRealizadosAdapter
import com.arkhimedev.vehiclemaintainer.adapter.VehiculoAdapter

var matricula:String?=null

class MantenimientosRealizadosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_mantenimientos_realizados)

        val btnGaraje = findViewById<ImageButton>(R.id.btnGaraje)
        val bundle = intent.extras
        matricula = bundle?.getString("matricula")

        iniciarRecyclerView()

        btnGaraje.setOnClickListener(){
            val intent = Intent(this, GarajeActivity::class.java)
            startActivity(intent)
        }
    }
    fun iniciarRecyclerView(){

        val miSqlHelper = MiSqlHelper(this)
        val listaMantenimientosRealizados = matricula?.let { miSqlHelper.seleccionarListaMantenimientoVehiculo(it, MANTENIMIENTO_REALIZADO) }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerMantenimientosRealizados)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = listaMantenimientosRealizados?.let { MantenimientosRealizadosAdapter(it) }
    }
}