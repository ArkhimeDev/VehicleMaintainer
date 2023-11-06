package com.arkhimedev.vehiclemaintainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class ActualizarKilometrajeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_kilometraje)

        val btnAceptar = findViewById<Button>(R.id.btnAceptar)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)
        val etKilometraje = findViewById<EditText>(R.id.etKilometraje)
        val bundle = intent.extras
        val matricula = bundle?.getString("matricula")
        val miSqlHelper=MiSqlHelper(this)

        btnCancelar.setOnClickListener(){
            this.finish()
        }
        btnAceptar.setOnClickListener(){
            if(matricula!=null){
                miSqlHelper.actualizarKilometraje(matricula,etKilometraje.text.toString().toInt())
            }
            this.finish()
            val intent= Intent(this,VehiculoActivity::class.java)
            intent.putExtra("matricula",matricula)
            startActivity(intent)
        }
    }
}