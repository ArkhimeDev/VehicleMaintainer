package com.arkhimedev.vehiclemaintainer

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class MiSqlHelperInstrumentedTest {

    lateinit var context: Context
    lateinit var miSqlHelper: MiSqlHelper

    @Before
    fun inicializar(){
        context = InstrumentationRegistry.getInstrumentation().targetContext
        miSqlHelper = MiSqlHelper(context)
    }

    @Test
    fun insertarYSeleccionarVehiculo(){
        val v1 = Vehiculo("1234",null,null)
        miSqlHelper.insertarVehiculo(v1)
        assertEquals(miSqlHelper.seleccionarVehiculo("1234")?.matricula ?: String(), v1.matricula)
    }
    @Test
    fun eliminarVehiculo(){
        val v1 = Vehiculo("2345FRT","Seat","Ibiza")
        miSqlHelper.insertarVehiculo(v1)
        miSqlHelper.borrarVehiculo(v1.matricula!!)
        assertNull(miSqlHelper.seleccionarVehiculo(v1.matricula!!))
    }
    @Test
    fun actualizarKilometrajeVehiculo(){
        val v1 = Vehiculo("1234",
            "V1234567890",
            "Fiat",
            "Tipo",
            "01/12/1999",
            0,
            "Gasolina"
        )
        miSqlHelper.insertarVehiculo(v1)
        miSqlHelper.actualizarKilometraje("1234",100)
        assertEquals(100, miSqlHelper.seleccionarVehiculo("1234")?.kilometraje ?: Int)
    }
}
