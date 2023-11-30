package com.arkhimedev.vehiclemaintainer

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import com.arkhimedev.vehiclemaintainer.Mantenimiento.Companion.MANTENIMIENTO_PROGRAMADO
import com.arkhimedev.vehiclemaintainer.Mensaje.Companion.NO_LEIDO
import com.arkhimedev.vehiclemaintainer.Notificacion.Companion.ID_NOTIFICACION
import java.util.Calendar

class ProgramarMantenimientoActivity : AppCompatActivity() {

    companion object{
        const val ID_CANAL = "Mi canal"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_programar_mantenimiento)

        //llamada que se realizara al pulsar el botón atras del dispositivo móvil donde se mostrará
        // enviará a la Activity GarajeActivity
        onBackPressedDispatcher.addCallback(this, object  : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent = Intent(this@ProgramarMantenimientoActivity, GarajeActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        //Primero se crea el canal para las notificaciones
        crearCanal()

        //Se recogen las referencias de los botones y los editTexts
        val btnGaraje = findViewById<ImageButton>(R.id.btnGaraje)
        val rbFecha = findViewById<RadioButton>(R.id.rbFecha)
        val rbKilometraje= findViewById<RadioButton>(R.id.rbKilometraje)
        val editTextFecha = findViewById<EditText>(R.id.editTextFecha)
        val editTextHora = findViewById<EditText>(R.id.editTextHora)
        val editTextKilometraje= findViewById<EditText>(R.id.editTextKilometraje)
        val editTextDescripcion = findViewById<EditText>(R.id.editTextDescripcion)
        val btnCancelar = findViewById<ImageButton>(R.id.btnCancelar)
        val btnAceptar = findViewById<ImageButton>(R.id.btnAceptar)

        //Creacion de las variables necesarias
        val calendarioSeleccionado = Calendar.getInstance()
        var diaSeleccionado = 0
        var mesSeleccionado = 0
        var anyoSeleccionado = 0
        var horaSeleccionada = 0
        var minutosSeleccionado = 0
        val miSqlHelper = MiSqlHelper(this)

        //Por defecto estara seleccionada la programación por fecha
        rbFecha.isChecked=true
        editTextFecha.isEnabled = true
        editTextHora.isEnabled = true
        editTextKilometraje.isEnabled = false
        editTextKilometraje.setBackgroundColor(Color.DKGRAY)
        editTextFecha.setBackgroundColor(getColor(R.color.gris))
        editTextHora.setBackgroundColor(getColor(R.color.gris))

        //Se recoge la matricula enviada por la Activity
        val bundle = intent.extras
        val matricula = bundle?.getString("matricula")

        //Botón para volver a la pantalla principal
        btnGaraje.setOnClickListener(){
            val intent = Intent(this,GarajeActivity::class.java)
            startActivity(intent)
            finish()
        }

        //Si se selecciona la programación por fecha se habilitan los campos necesarios
        //y se deshabilitan los innecesarios.
        rbFecha.setOnClickListener(){
            editTextFecha.isEnabled = true
            editTextHora.isEnabled = true
            editTextKilometraje.isEnabled = false
            editTextKilometraje.setBackgroundColor(Color.DKGRAY)
            editTextKilometraje.setText("")
            editTextFecha.setBackgroundColor(getColor(R.color.gris))
            editTextHora.setBackgroundColor(getColor(R.color.gris))
       }

        //Si se selecciona la programación por kilometraje se habilitan los campos necesarios
        //y se deshabilitan los innecesarios.
        rbKilometraje.setOnClickListener(){
            editTextFecha.isEnabled = false
            editTextHora.isEnabled = false
            editTextKilometraje.isEnabled = true
            editTextFecha.setBackgroundColor(Color.DKGRAY)
            editTextHora.setBackgroundColor(Color.DKGRAY)
            editTextFecha.setText("")
            editTextHora.setText("")
            editTextKilometraje.setBackgroundColor(getColor(R.color.gris))
        }

        //Al pulsar en el editTextFecha se muestra un calendario para poder seleccionar la fecha
        editTextFecha.setOnClickListener(){
            val calendario = Calendar.getInstance()
            val dia = calendario.get(Calendar.DAY_OF_MONTH)
            val mes = calendario.get(Calendar.MONTH)
            val anyo = calendario.get(Calendar.YEAR)
            calendario.setTimeInMillis(System.currentTimeMillis())
            val listener = DatePickerDialog.OnDateSetListener{ datePicker, y, m, d ->
                editTextFecha.setText("$d/${m+1}/$y")
                diaSeleccionado = d
                mesSeleccionado = m
                anyoSeleccionado = y
                //calendarioSeleccionado.set(y,m,d,13,22,0)
            }
            DatePickerDialog(this,listener,anyo,mes,dia).show()
        }

        //Al pulsar en el editTextHora se muestra un reloj para poder seleccionar la hora
        editTextHora.setOnClickListener(){
            val calendario = Calendar.getInstance()
            val hora = calendario.get(Calendar.HOUR_OF_DAY)
            val minutos = calendario.get(Calendar.MINUTE)
            calendario.setTimeInMillis(System.currentTimeMillis())
            val listener = TimePickerDialog.OnTimeSetListener{ timePicker, h, m ->
                if(m<10){
                    editTextHora.setText("$h:0$m")
                }
                else{
                    editTextHora.setText("$h:$m")
                }
                horaSeleccionada = h
                minutosSeleccionado = m
            }
            TimePickerDialog(this,listener,hora,minutos,true).show()
        }

        //Al pulsar en candelar retorna a VehiculoActivity
        btnCancelar.setOnClickListener(){
            val intent=Intent(this,VehiculoActivity::class.java)
            intent.putExtra("matricula",matricula)
            startActivity(intent)

        }

        //Botón para aceptar la programación de mantenimiento
        btnAceptar.setOnClickListener(){
            //Si el usuario selecciona la proramación por fecha
            if(rbFecha.isChecked){
                //Primero se comprueba que todos los campos tengan valor
                if(editTextFecha.text.isEmpty()||editTextHora.text.isEmpty()||editTextDescripcion.text.isEmpty()){
                    Toast.makeText(this,"Selecciona fecha, hora y añade una descripción",Toast.LENGTH_SHORT).show()
                }
                else{
                    //Si los campos tienen valor, se instancia un objeto Mantenimiento y un objeto Mensaje
                    //para insertarlos en la base de datos
                    val mantenimiento = Mantenimiento(
                        editTextDescripcion.text.toString(),
                        editTextFecha.text.toString(),
                        null,
                        null,
                        MANTENIMIENTO_PROGRAMADO,
                        matricula
                    )
                    val idMantenimiento = miSqlHelper.insertarMantenimiento(matricula!!,mantenimiento)
                    val mensaje = Mensaje(
                        editTextDescripcion.text.toString(),
                        editTextFecha.text.toString(),
                        null,
                        NO_LEIDO,
                        idMantenimiento.toInt()
                    )
                    miSqlHelper.insertarMensaje(mensaje)
                    Toast.makeText(this,"Programación realizada con exito",Toast.LENGTH_SHORT).show()

                    //Para finalizar se prorgama la notificación en la fecha y hora seleccionada por el usuario
                    calendarioSeleccionado.set(
                        anyoSeleccionado,
                        mesSeleccionado,
                        diaSeleccionado,
                        horaSeleccionada,
                        minutosSeleccionado,
                        0
                    )
                    notificacion(calendarioSeleccionado)
                    Toast.makeText(this,"Programación realizada con exito",Toast.LENGTH_SHORT).show()
                    editTextFecha.setText("")
                    editTextHora.setText("")
                    editTextKilometraje.setText("")
                    editTextDescripcion.setText("")
                }

            }
            //Si el usuario selecciona la proramación por kilometraje
            if(rbKilometraje.isChecked){
                //Primero se comprueba que todos los campos tengan valor
                if(editTextKilometraje.text.isEmpty()||editTextDescripcion.text.isEmpty()){
                    Toast.makeText(this,"Indica el Kilometraje y añade una descripción",Toast.LENGTH_SHORT).show()
                }
                else{
                    //Si los campos tienen valor, se instancia un objeto Mantenimiento y un objeto Mensaje
                    //para insertarlos en la base de datos
                    val mantenimiento = Mantenimiento(
                        editTextDescripcion.text.toString(),
                        null,
                        editTextKilometraje.text.toString().toInt(),
                        null,
                        MANTENIMIENTO_PROGRAMADO,
                        matricula
                    )
                    val idMantenimiento = miSqlHelper.insertarMantenimiento(matricula!!,mantenimiento)
                    val mensaje = Mensaje(
                        editTextDescripcion.text.toString(),
                        null,
                        editTextKilometraje.text.toString().toInt(),
                        NO_LEIDO,
                        idMantenimiento.toInt()
                    )
                    //Se muestra un mensaje al usuario y se resetean los campos
                    miSqlHelper.insertarMensaje(mensaje)
                    Toast.makeText(this,"Programación realizada con exito",Toast.LENGTH_SHORT).show()
                    editTextFecha.setText("")
                    editTextHora.setText("")
                    editTextKilometraje.setText("")
                    editTextDescripcion.setText("")

                }
            }
        }
    }

    //Función que genera una notificacion pasando por parámetro un objeto Calendario
    private fun notificacion(calendario:Calendar){
        val intent=Intent(applicationContext,Notificacion::class.java)
        val pendingIntent=PendingIntent.getBroadcast(
            applicationContext,
            ID_NOTIFICACION,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendario.getTimeInMillis(), pendingIntent)
    }

    //Función para crear el canal para las notificaciones dependiendo de la versión de Android
    private fun crearCanal(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val canal = NotificationChannel(
                ID_CANAL,
                "Mi canal",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(canal)
        }
    }

}