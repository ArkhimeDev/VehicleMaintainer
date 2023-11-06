package com.arkhimedev.vehiclemaintainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arkhimedev.vehiclemaintainer.adapter.MensajeAdapter
import com.arkhimedev.vehiclemaintainer.Mantenimiento.Companion.MANTENIMIENTO_PROGRAMADO

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
        var listaMensajesVisibles = listOf<Mensaje>()
        val listaMantenimiento = miSqlHelper.seleccionarListaMantenimientoTipo(MANTENIMIENTO_PROGRAMADO)
        val listaVehiculo = miSqlHelper.seleccionarListaVehiculos()
        if(listaMantenimiento!=null&&listaVehiculo!=null){
            val listaIdMantenimiento = seleccionarIdMantenimiento(listaVehiculo,listaMantenimiento)
            if(listaIdMantenimiento!=null){
                listaMensajesVisibles = listaMensajesVisibles(listaIdMantenimiento)
            }
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerMensaje)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = listaMensajesVisibles?.let { MensajeAdapter(it) }
    }
    fun seleccionarIdMantenimiento(listaVehiculo: List<Vehiculo>, listaMantenimiento: List<Mantenimiento>):List<Int>?{
        var listaIdMantenimiento = mutableListOf<Int>()
        for(vehiculo in listaVehiculo) {
            for(mantenimiento in listaMantenimiento){
                if(mantenimiento.kilometraje!=null&&(vehiculo.matricula==mantenimiento.matricula && vehiculo.kilometraje!!>=mantenimiento.kilometraje!!)){
                    listaIdMantenimiento.add(mantenimiento.idMantenimiento!!)
                }
            }

        }
        if(listaIdMantenimiento!=null){
            return listaIdMantenimiento
        }else{
            return null
        }
    }
    fun listaMensajesVisibles(listaIdMantenimiento: List<Int>): List<Mensaje> {
        val miSqlHelper = MiSqlHelper(this)
        var listaMensaje = mutableListOf<Mensaje>()
        for (id in listaIdMantenimiento){
            val mensaje = miSqlHelper.seleccionarMensaje(id)
            if (mensaje != null) {
                listaMensaje.add(mensaje)
            }
        }
        return listaMensaje
    }
}