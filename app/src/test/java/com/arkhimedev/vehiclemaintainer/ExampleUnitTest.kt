package com.arkhimedev.vehiclemaintainer

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun crearVehiculo(){
        val v1 = Vehiculo("1234",null,null)
        val v2 = Vehiculo("123",null,null)
        assertNotEquals(v1.matricula,v2.matricula)
    }
}