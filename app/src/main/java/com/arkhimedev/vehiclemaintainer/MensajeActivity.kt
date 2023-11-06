package com.arkhimedev.vehiclemaintainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arkhimedev.vehiclemaintainer.adapter.MensajeAdapter

class MensajeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mensaje)

        val btnGaraje = findViewById<ImageButton>(R.id.btnGaraje)

        iniciarRecyclerView()

        btnGaraje.setOnClickListener(){
            val intent = Intent(this, GarajeActivity::class.java)
            startActivity(intent)
        }
    }
    fun iniciarRecyclerView(){

        val miSqlHelper = MiSqlHelper(this)
        val listaMensaje = miSqlHelper.seleccionarListaMensaje()
        val listaVehiculo = miSqlHelper.seleccionarListaVehiculos()
        var listaMensajeNueva = listOf<Mensaje>()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerMensaje)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = listaMensaje?.let { MensajeAdapter(it) }
    }
}