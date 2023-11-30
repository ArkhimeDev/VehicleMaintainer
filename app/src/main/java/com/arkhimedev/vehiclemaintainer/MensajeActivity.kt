package com.arkhimedev.vehiclemaintainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arkhimedev.vehiclemaintainer.adapter.MensajeAdapter
import com.arkhimedev.vehiclemaintainer.Mantenimiento.Companion.MANTENIMIENTO_PROGRAMADO
import com.arkhimedev.vehiclemaintainer.Mensaje.Companion.NO_LEIDO
import java.util.Calendar

class MensajeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mensaje)

        //llamada que se realizara al pulsar el botón atras del dispositivo móvil donde se mostrará
        // enviará a la Activity GarajeActivity
        onBackPressedDispatcher.addCallback(this, object  : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent = Intent(this@MensajeActivity, GarajeActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        //Se recoge la referencia del botón
        val btnGaraje = findViewById<ImageButton>(R.id.btnGaraje)

        //Se inicia el RecyclerView llamando a la función iniciarRecyclerView
        iniciarRecyclerView()

        //Botón que retorna a la GarajeActivity
        btnGaraje.setOnClickListener(){
            val intent = Intent(this, GarajeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    //Función que inicia el RecyclerView
    fun iniciarRecyclerView(){

        val miSqlHelper = MiSqlHelper(this)
        var listaMensajesVisibles = listOf<Mensaje>()
        val listaMantenimiento = miSqlHelper.seleccionarListaMantenimientoTipo(MANTENIMIENTO_PROGRAMADO)
        val listaVehiculo = miSqlHelper.seleccionarListaVehiculos()
        if(listaMantenimiento!=null&&listaVehiculo!=null){
            val listaIdMantenimiento = seleccionarIdMantenimiento(listaVehiculo,listaMantenimiento)
            Log.e("lista id", listaIdMantenimiento.toString())
            if(listaIdMantenimiento!=null){
                listaMensajesVisibles = listaMensajesVisibles(listaIdMantenimiento)
            }
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerMensaje)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = listaMensajesVisibles?.let { MensajeAdapter(it) }
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

                if(mantenimiento.kilometraje!!> 0 &&(vehiculo.matricula==mantenimiento.matricula &&
                            vehiculo.kilometraje!!>=mantenimiento.kilometraje!!)){
                    listaIdMantenimiento.add(mantenimiento.idMantenimiento!!)
                }
                if(mantenimiento.fecha!=null && vehiculo.matricula==mantenimiento.matricula){
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

    //Función que retorna una lista de Mensaje que tienen que ser visibles pasando por parametro
    //una lista de idMantenimiento
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