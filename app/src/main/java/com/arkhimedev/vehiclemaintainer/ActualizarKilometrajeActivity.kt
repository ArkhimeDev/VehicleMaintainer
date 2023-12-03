package com.arkhimedev.vehiclemaintainer

import android.content.Intent
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.arkhimedev.vehiclemaintainer.Mensaje.Companion.NO_LEIDO

class ActualizarKilometrajeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_kilometraje)

        //llamada que se realizara al pulsar el botón atras del dispositivo móvil donde se mostrará
        // enviará a la Activity VehiculoActivity
        onBackPressedDispatcher.addCallback(this, object  : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent= Intent(this@ActualizarKilometrajeActivity,VehiculoActivity::class.java)
                intent.putExtra("matricula",matricula)
                startActivity(intent)
                finish()
            }
        })

        //Se recogen las referencias de los botones y los editTexts
        val btnAceptar = findViewById<Button>(R.id.btnAceptar)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)
        val etKilometraje = findViewById<EditText>(R.id.etKilometraje)

        //Se recoge la matricula enviada por la Activity
        val bundle = intent.extras
        val matricula = bundle?.getString("matricula")

        //Creacion de las variables necesarias
        val miSqlHelper=MiSqlHelper(this)
        val vehiculo = miSqlHelper.seleccionarVehiculo(matricula!!)

        //Al pulsar el boton cancelar se finaliza la Activity
        btnCancelar.setOnClickListener(){
            this.finish()
        }
        //Acciones que se realizan al pulsar el botón aceptar
        btnAceptar.setOnClickListener(){
            //Si el EditText no tiene valor se le informa al usuario mediante un Toast
            if(etKilometraje.text.isEmpty()){
                Toast.makeText(this, "Introduce un kilometraje", Toast.LENGTH_LONG).show()
            }
            else{
                //Si el número introducido es menor o igual al kilometraje del vehiculo no se puede
                //realizar la actualización y se informa de la situación al usuario
                if(etKilometraje.text.toString().toInt() <= vehiculo!!.kilometraje!!){
                    Toast.makeText(this, "El kilometraje introducido no puede ser menor o igual al del vehiculo", Toast.LENGTH_LONG).show()
                }else{
                    //Si el número introducido es correcto se introduce en la BD y se informa al usuario
                    miSqlHelper.actualizarKilometraje(matricula,etKilometraje.text.toString().toInt())
                    val vehiculoActualizado = miSqlHelper.seleccionarVehiculo(matricula)
                    Toast.makeText(this, "El kilometraje ha sido actualizado", Toast.LENGTH_LONG).show()

                    //Tras actualizar el kilometraje se recoge una lista de mensajes del vehiculo
                    val listaMensajes = miSqlHelper.seleccionarListaMensajeVehiculo(matricula)
                    var mensajeNuevo = false

                    //Si existe un mensaje para mostrar que cumpla con las condiciones se muestra una alerta
                    //para informar al usuario
                    if(listaMensajes!=null){
                        for (mensaje in listaMensajes) {
                            if(mensaje.kilometraje!! <= vehiculoActualizado!!.kilometraje!! && mensaje.kilometraje!=0 && mensaje.estado==NO_LEIDO){
                                mensajeNuevo = true
                            }
                        }
                        if(mensajeNuevo){
                            mostrarAlerta()
                        }
                        //Si no existe mensaje a mostrar se actualiza la Activity VehiculoActivity
                        else{
                            val intent= Intent(this,VehiculoActivity::class.java)
                            intent.putExtra("matricula",matricula)
                            startActivity(intent)
                            finish()
                        }

                    }
                    //Si no existen mensajes en la lista se actualiza la Activity VehiculoActivity
                    if(listaMensajes==null){
                        val intent= Intent(this,VehiculoActivity::class.java)
                        intent.putExtra("matricula",matricula)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
    //Dialogo a mostrar en caso de que exista un mantenimiento a realizar tras la actualización del kilometraje
    fun mostrarAlerta(){
        AlertDialog.Builder(this)
            .setTitle("Mantenimiento a realizar")
            .setMessage("Tienes un nuevo mantenimiento a realizar")
            .setPositiveButton(R.string.txt_aceptar){ dialog, which ->
                val intent = Intent(this,MensajeActivity::class.java)
                ContextCompat.startActivity(this, intent, null)
                finish()
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
        val notificacion = RingtoneManager.getActualDefaultRingtoneUri(this,RingtoneManager.TYPE_NOTIFICATION)
        val sonido = RingtoneManager.getRingtone(this,notificacion)
        sonido.play()
    }
}