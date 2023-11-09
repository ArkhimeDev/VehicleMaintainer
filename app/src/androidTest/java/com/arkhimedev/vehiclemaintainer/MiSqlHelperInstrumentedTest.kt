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
}
