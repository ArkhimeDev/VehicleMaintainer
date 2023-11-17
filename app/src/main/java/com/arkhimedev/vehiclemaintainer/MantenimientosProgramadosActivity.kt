package com.arkhimedev.vehiclemaintainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arkhimedev.vehiclemaintainer.Mantenimiento.Companion.MANTENIMIENTO_PROGRAMADO
import com.arkhimedev.vehiclemaintainer.adapter.MantenimientosProgramadosAdapter

class MantenimientosProgramadosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mantenimientos_programados)

        //Se recogen la referencia del botón
        val btnGaraje = findViewById<ImageButton>(R.id.btnGaraje)

        //Se recoge la matricula enviada por la Activity
        val bundle = intent.extras
        matricula = bundle?.getString("matricula")

        //Se inicia el RecyclerView
        iniciarRecyclerView()

        //Al pulsar sobre el botón retorna a GarajeActivity
        btnGaraje.setOnClickListener(){
            val intent = Intent(this, GarajeActivity::class.java)
            startActivity(intent)
        }
    }
    //Función que inicia el RecyclerView correspondiente
    fun iniciarRecyclerView(){

        val miSqlHelper = MiSqlHelper(this)
        val listaMantenimientosProgramados =miSqlHelper.seleccionarListaMantenimientoTipo(MANTENIMIENTO_PROGRAMADO)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerMantenimientosProgramados)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = listaMantenimientosProgramados?.let { MantenimientosProgramadosAdapter(it) }
    }
}