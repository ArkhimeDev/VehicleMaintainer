package com.arkhimedev.vehiclemaintainer

import android.content.Context

class Vehiculo {

    var matricula:String?=null
        get() = field
        set(value){ //Si el valor no es null enimino los espacios en blanco y lo paso a mayusculas
            if (value != null) {
                field=value.replace("\\s+".toRegex(),"").uppercase()
            }
        }
    var numeroBastidor:String? = null
        get() = field
        set(value){ //Si el valor no es null enimino los espacios en blanco y lo paso a mayusculas
            if (value != null) {
                field=value.replace("\\s+".toRegex(),"").uppercase()
            }
        }
    var marca:String?=null
        get() = field
        set(value){field=value}
    var modelo:String?=null
        get() = field
        set(value){field=value}
    var fechaMatriculacion: String?=null
        get() = field
        set(value){field=value}
    var kilometraje:Int?=null
        get() = field
        set(value){field=value}
    var tipoCombustible:String?=null
        get() = field
        set(value){field=value}
    var mantenimientosRealizados = mutableListOf<Mantenimiento>()
        get() = field
        set(value){field=value}

    constructor(
        matricula: String?,
        numeroBastidor: String?,
        marca: String?,
        modelo: String?,
        fechaMatriculacion: String?,
        kilometraje: Int?,
        tipoCombustible: String?
    ) {
        this.matricula = matricula
        this.numeroBastidor = numeroBastidor
        this.marca = marca
        this.modelo = modelo
        this.fechaMatriculacion = fechaMatriculacion
        this.kilometraje = kilometraje
        this.tipoCombustible = tipoCombustible
    }

    constructor(matricula: String?, marca: String?, modelo: String?) {
        this.matricula = matricula
        this.marca = marca
        this.modelo = modelo
    }

    override fun toString(): String {
        return "Vehiculo(matricula=$matricula, numeroBastidor=$numeroBastidor, marca=$marca, modelo=$modelo, fechaMatriculacion=$fechaMatriculacion, kilometraje=$kilometraje, tipoCombustible=$tipoCombustible, mantenimientosRealizados=$mantenimientosRealizados)"
    }
}