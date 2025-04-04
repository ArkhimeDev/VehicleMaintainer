package com.arkhimedev.vehiclemaintainer

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.util.*

class RegistrarVehiculoActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)

    lateinit var miSqlHelper: MiSqlHelper

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_vehiculo)

        val editTextMarca = findViewById<EditText>(R.id.editTextMarca)
        val editTextModelo = findViewById<EditText>(R.id.editTextModelo)
        val editTextMatricula = findViewById<EditText>(R.id.editTextMatricula)
        val editTextKilometraje = findViewById<EditText>(R.id.editTextKilometraje)
        val editTextFechaMatriculacion = findViewById<EditText>(R.id.editTextFechaMatriculacion)
        val editTextTipoCombustible = findViewById<EditText>(R.id.editTextTipoCombustible)
        val editTextNumeroBastidor = findViewById<EditText>(R.id.editTextNumeroBastidor)
        val btnGaraje = findViewById<ImageButton>(R.id.btnGaraje)
        val btnAceptar = findViewById<ImageButton>(R.id.btnAceptar)
        val btnCancelar = findViewById<ImageButton>(R.id.btnCancelar)
        miSqlHelper = MiSqlHelper(this)

        //Botón para volver a la pantalla principal
        btnGaraje.setOnClickListener {
            val intent = Intent(this, GarajeActivity::class.java)
            startActivity(intent)
        }

        //Botón para registrar vehiculo en la base de datos
        btnAceptar.setOnClickListener {
            //Primero se comprueba que todos los campos tengan valor
            if (editTextMarca.text.isEmpty()||editTextModelo.text.isEmpty()||editTextMatricula.text.isEmpty()||
                editTextKilometraje.text.isEmpty()||editTextFechaMatriculacion.text.isEmpty()||
                editTextTipoCombustible.text.isEmpty()||editTextNumeroBastidor.text.isEmpty()){
                //Si hay algun campo vacío, se lo hago saber al usuario mediante un Toast
                Toast.makeText(this,"Rellena todos los campos del vehículo",Toast.LENGTH_LONG).show()
            }
            //Si todos los campos tienen valor se comprueba que la matrícula indicada no exista
            else{
                if(!editTextMatricula.text.isEmpty()&&miSqlHelper.seleccionarVehiculo(editTextMatricula.text.toString())!=null){
                    Toast.makeText(this,"Ya existe un vehiculo con la misma matricula",Toast.LENGTH_LONG).show()
                }
                else{
                    //Si la matrícula no existe se instancia un objeto Vehiculo y se inserta en la base de datos
                    val vehiculo = Vehiculo(
                        editTextMatricula.text.toString(),
                        editTextNumeroBastidor.text.toString(),
                        editTextMarca.text.toString(),
                        editTextModelo.text.toString(),
                        editTextFechaMatriculacion.text.toString(),
                        editTextKilometraje.text.toString().toInt(),
                        editTextTipoCombustible.text.toString()
                    )

                    miSqlHelper.insertarVehiculo(vehiculo)
                    Toast.makeText(this,"Vehículo registrado correctamente",Toast.LENGTH_LONG).show()
                    //Después se resetean los editText
                    editTextMatricula.setText("")
                    editTextNumeroBastidor.setText("")
                    editTextMarca.setText("")
                    editTextModelo.setText("")
                    editTextFechaMatriculacion.setText("")
                    editTextKilometraje.setText("")
                    editTextTipoCombustible.setText("")
                }
            }


        }

        editTextFechaMatriculacion.setOnClickListener(){
            val calendarioSeleccionado = Calendar.getInstance()
            val dia = calendarioSeleccionado.get(Calendar.DAY_OF_MONTH)
            val mes = calendarioSeleccionado.get(Calendar.MONTH)
            val anyo = calendarioSeleccionado.get(Calendar.YEAR)
            val listener = DatePickerDialog.OnDateSetListener{datePicker, y, m, d ->
                editTextFechaMatriculacion.setText("$d/${m+1}/$y")
            }
            val milisegundos = Calendar.getInstance().timeInMillis
            DatePickerDialog(this,listener,anyo,mes,dia).show()
        }

        btnCancelar.setOnClickListener(){
            val intent=Intent(this,GarajeActivity::class.java)
            startActivity(intent)
        }


    }

}