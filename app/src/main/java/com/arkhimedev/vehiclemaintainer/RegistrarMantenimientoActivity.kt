package com.arkhimedev.vehiclemaintainer

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.arkhimedev.vehiclemaintainer.Mantenimiento.Companion.MANTENIMIENTO_REALIZADO
import java.util.*

class RegistrarMantenimientoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_mantenimiento)
        
        val editTextDescripcion = findViewById<EditText>(R.id.editTextDescripcion)
        val editTextFechaRealizacion = findViewById<EditText>(R.id.editTextFechaRealizacion)
        val editTextKilometraje = findViewById<EditText>(R.id.editTextKilometraje)
        val editTextImporte = findViewById<EditText>(R.id.editTextImporte)
        val btnGaraje = findViewById<ImageButton>(R.id.btnGaraje)
        val btnCancelar = findViewById<ImageButton>(R.id.btnCancelar)
        val btnAceptar = findViewById<ImageButton>(R.id.btnAceptar)
        val miSqlHelper = MiSqlHelper(this)

        val bundle = intent.extras
        val matricula = bundle?.getString("matricula")

        btnGaraje.setOnClickListener(){
            val intent = Intent(this,GarajeActivity::class.java)
            startActivity(intent)
        }

        editTextFechaRealizacion.setOnClickListener(){
            val calendarioSeleccionado = Calendar.getInstance()
            val dia = calendarioSeleccionado.get(Calendar.DAY_OF_MONTH)
            val mes = calendarioSeleccionado.get(Calendar.MONTH)
            val anyo = calendarioSeleccionado.get(Calendar.YEAR)
            val listener = DatePickerDialog.OnDateSetListener{ datePicker, y, m, d ->
                editTextFechaRealizacion.setText("$d/${m+1}/$y")
            }
            DatePickerDialog(this,listener,anyo,mes,dia).show()
        }

        btnCancelar.setOnClickListener(){
            val intent = Intent(this,VehiculoActivity::class.java)
            intent.putExtra("matricula",matricula)
            startActivity(intent)
        }
        btnAceptar.setOnClickListener(){
            //Primero se comprueba que todos los campos tengan valor
            if (editTextDescripcion.text.isEmpty()||editTextFechaRealizacion.text.isEmpty()||editTextKilometraje.text.isEmpty()||
                editTextImporte.text.isEmpty()){
                //Si hay algun campo vacío, se lo hago saber al usuario mediante un Toast
                Toast.makeText(this,"Rellena todos los campos del mantenimiento", Toast.LENGTH_LONG).show()
            }
            //Si todos los campos tienen valor se instancia un mantenimiento y se inserta en la base de datos
            else{
                val mantenimiento = Mantenimiento(
                    editTextDescripcion.text.toString(),
                    editTextFechaRealizacion.text.toString(),
                    editTextKilometraje.text.toString().toInt(),
                    editTextImporte.text.toString().toFloat(),
                    MANTENIMIENTO_REALIZADO,
                    matricula
                )
                if (matricula!=null){
                    miSqlHelper.insertarMantenimiento(matricula,mantenimiento)
                    Toast.makeText(this,"Mantenimiento registrado correctamente", Toast.LENGTH_LONG).show()
                    //Después se resetean los editText
                    editTextDescripcion.setText("")
                    editTextFechaRealizacion.setText("")
                    editTextKilometraje.setText("")
                    editTextImporte.setText("")
                }
            }
        }

    }
}