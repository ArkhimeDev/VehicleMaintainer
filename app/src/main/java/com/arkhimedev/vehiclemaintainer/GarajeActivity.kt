package com.arkhimedev.vehiclemaintainer

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arkhimedev.vehiclemaintainer.Mantenimiento.Companion.MANTENIMIENTO_PROGRAMADO
import com.arkhimedev.vehiclemaintainer.Mensaje.Companion.NO_LEIDO
import com.arkhimedev.vehiclemaintainer.adapter.VehiculoAdapter
import java.util.*

class GarajeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_garaje)



        val btnListMantProgramados = findViewById<ImageButton>(R.id.btnListMantProgramados)
        val btnNuevoVehiculo = findViewById<ImageButton>(R.id.btnNuevoVehiculo)
        val btnMensajes = findViewById<ImageButton>(R.id.btnMensajes)
        val tvNumeroMensajes = findViewById<TextView>(R.id.tvNumeroMensajes)
        val miSqlHelper = MiSqlHelper(this)
        var listaMensajesVisibles = listOf<Mensaje>()

        tvNumeroMensajes.isVisible = false


        val listaMantenimiento = miSqlHelper.seleccionarListaMantenimientoTipo(MANTENIMIENTO_PROGRAMADO)
        val listaVehiculo = miSqlHelper.seleccionarListaVehiculos()
        if(listaMantenimiento!=null&&listaVehiculo!=null){
            val listaIdMantenimiento = seleccionarIdMantenimiento(listaVehiculo,listaMantenimiento)
            if(listaIdMantenimiento!=null){
                listaMensajesVisibles = seleccionarListaMensajesNoLeidos(listaIdMantenimiento)
            }
        }
        if(listaMensajesVisibles.size>=1){
            tvNumeroMensajes.isVisible =true
            tvNumeroMensajes.setText(listaMensajesVisibles.size.toString())
        }



        if (listaVehiculo != null) {
            iniciarRecyclerView(listaVehiculo)
        }

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
    fun iniciarRecyclerView(listaVehiculo: List<Vehiculo>){

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerVehiculo)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = listaVehiculo?.let { VehiculoAdapter(it) }
    }

    //Función que retorna una lista de idMantenimiento que se deben mostrar dependiendo de las
    //condiciones de filtrado pasando por parámetro una lista de Vehiculo y una liista Mantenimiento
    fun seleccionarIdMantenimiento(listaVehiculo: List<Vehiculo>, listaMantenimiento: List<Mantenimiento>):List<Int>?{
        var listaIdMantenimiento = mutableListOf<Int>()
        val delimitador = "/"
        val calendario = Calendar.getInstance()
        val dia = calendario.get(Calendar.DAY_OF_MONTH)
        val mes = calendario.get(Calendar.MONTH)+1
        val anyo = calendario.get(Calendar.YEAR)
        for(vehiculo in listaVehiculo) {
            for(mantenimiento in listaMantenimiento){

                if(mantenimiento.kilometraje!!> 0 &&(vehiculo.matricula==mantenimiento.matricula && vehiculo.kilometraje!!>=mantenimiento.kilometraje!!)){
                    listaIdMantenimiento.add(mantenimiento.idMantenimiento!!)
                }
                if(mantenimiento.fecha!=null){
                    val fecha = mantenimiento.fecha!!.split(delimitador)
                    if(dia >= fecha[0].toInt() && mes >= fecha[1].toInt() && anyo >= fecha[2].toInt()){
                        listaIdMantenimiento.add(mantenimiento.idMantenimiento!!)
                    }
                }
            }

        }
        if(listaIdMantenimiento!=null){
            return listaIdMantenimiento
        }else{
            return null
        }
    }

    //Función que retorna una lista de Mensaje no leidos pasando por parametro una lista de idMantenimiento
    fun seleccionarListaMensajesNoLeidos(listaIdMantenimiento: List<Int>): List<Mensaje> {
        val miSqlHelper = MiSqlHelper(this)
        var listaMensaje = mutableListOf<Mensaje>()
        for (id in listaIdMantenimiento){
            val mensaje = miSqlHelper.seleccionarMensaje(id)
            if (mensaje != null && mensaje.estado==NO_LEIDO) {
                listaMensaje.add(mensaje)
            }
        }
        return listaMensaje
    }
}