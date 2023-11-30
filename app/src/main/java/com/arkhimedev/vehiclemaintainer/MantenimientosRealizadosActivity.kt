package com.arkhimedev.vehiclemaintainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
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

        //llamada que se realizara al pulsar el botón atras del dispositivo móvil donde se mostrará
        // enviará a la Activity GarajeActivity
        onBackPressedDispatcher.addCallback(this, object  : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent = Intent(this@MantenimientosRealizadosActivity, GarajeActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        //Se recoge la referencia del botón
        val btnGaraje = findViewById<ImageButton>(R.id.btnGaraje)

        //Se recoge la matricula enviada por la Activity
        val bundle = intent.extras
        matricula = bundle?.getString("matricula")

        //Se llama a la función para iniciar el RecyclerView
        iniciarRecyclerView()

        //Botón para volver a la pantalla principal
        btnGaraje.setOnClickListener(){
            val intent = Intent(this, GarajeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    //función que inicia el RecyclerView
    fun iniciarRecyclerView(){

        val miSqlHelper = MiSqlHelper(this)
        val listaMantenimientosRealizados = matricula?.let { miSqlHelper.seleccionarListaMantenimientoVehiculo(it, MANTENIMIENTO_REALIZADO) }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerMantenimientosRealizados)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = listaMantenimientosRealizados?.let { MantenimientosRealizadosAdapter(it) }
    }
}