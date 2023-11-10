package com.arkhimedev.vehiclemaintainer

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.arkhimedev.vehiclemaintainer.Mantenimiento.Companion.MANTENIMIENTO_PROGRAMADO
import com.arkhimedev.vehiclemaintainer.Mantenimiento.Companion.MANTENIMIENTO_REALIZADO
import org.junit.After

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
    fun actualizarKilometrajeVehiculo() {
        val v1 = Vehiculo(
            "1234",
            "V1234567890",
            "Fiat",
            "Tipo",
            "01/12/1999",
            0,
            "Gasolina"
        )
        miSqlHelper.insertarVehiculo(v1)
        miSqlHelper.actualizarKilometraje("1234", 100)
        assertEquals(100, miSqlHelper.seleccionarVehiculo("1234")?.kilometraje ?: Int)
    }
    @Test
    fun insertarYSeleccionarListaMantenimientoProgramado(){
        val mant1 = Mantenimiento(
            "Descripción",
            "09/09/1999",
            null,
            null,
            MANTENIMIENTO_PROGRAMADO,
            "1234"
        )
        miSqlHelper.insertarMantenimiento(mant1.matricula!!,mant1)
        assertNotNull(miSqlHelper.seleccionarListaMantenimientoTipo(MANTENIMIENTO_PROGRAMADO))
    }
    @Test
    fun insertarYSeleccionarListaMantenimientoRealizado(){
        val mant1 = Mantenimiento(
            "Descripción",
            "09/09/1999",
            20,
            99.99f,
            MANTENIMIENTO_REALIZADO,
            "1234"
        )
        miSqlHelper.insertarMantenimiento(mant1.matricula!!,mant1)
        assertNotNull(miSqlHelper.seleccionarListaMantenimientoTipo(MANTENIMIENTO_REALIZADO))
    }
    @Test
    fun eliminarMantenimiento(){
        val mant1 = Mantenimiento(
            2,
            "Mantenimiento para eliminar",
            "09/09/1999",
            200,
            99.99f,
            MANTENIMIENTO_REALIZADO,
            "1234"
        )
        miSqlHelper.insertarMantenimiento(mant1.matricula!!,mant1)
        miSqlHelper.borrarMantenimiento(mant1.idMantenimiento!!)
        assertNull(miSqlHelper.seleccionarMantenimiento(mant1.idMantenimiento!!))
    }
    @Test
    fun insertarYSeleccionarMensaje(){
        val mnsj1 = Mensaje(
            "Mensaje para insertar",
            "09/09/1999",
            200,
            0,
            1
        )
        miSqlHelper.insertarMensaje(mnsj1)
        assertNotNull(miSqlHelper.seleccionarMensaje(1))
    }
    @Test
    fun eliminarMensaje(){
        val mnsj1 = Mensaje(
            "Mensaje para eliminar",
            "09/09/1999",
            200,
            0,
            2
        )
        miSqlHelper.insertarMensaje(mnsj1)
        miSqlHelper.borrarMensajeIdMantenimiento(mnsj1.idMantenimiento!!)
        assertNull(miSqlHelper.seleccionarMensaje(mnsj1.idMantenimiento!!))
    }
    @After
    fun eliminarDatos(){
        miSqlHelper.borrarDatosTablasBD()
    }
}
