package com.arkhimedev.vehiclemaintainer


class Mensaje {
    //Constantes para utilizar como estado
    companion object{
        const val NO_LEIDO = 0
        const val LEIDO = 1
    }

    //Propiedades, getters y setters
    var idMensaje:Int?=null
        get() = field
        set(value){field=value}
    var mensaje:String?=null
        get() = field
        set(value){field=value}
    var fecha:String?=null
        get() = field
        set(value){field=value}
    var kilometraje:Int?=null
        get() = field
        set(value){field=value}
    var estado:Int?=null
        get() = field
        set(value){field=value}
    var idMantenimiento:Int?=null
        get() = field
        set(value){field=value}

    //Constructores
    constructor(mensaje: String?, fecha: String?, kilometraje: Int?, estado: Int?, idMantenimiento:Int?) {
        this.mensaje = mensaje
        this.fecha = fecha
        this.kilometraje = kilometraje
        this.estado = estado
        this.idMantenimiento = idMantenimiento
    }

    constructor(idMensaje: Int?, mensaje: String?, fecha: String?, kilometraje: Int?, estado: Int?, idMantenimiento:Int?) {
        this.idMensaje = idMensaje
        this.mensaje = mensaje
        this.fecha = fecha
        this.kilometraje = kilometraje
        this.estado = estado
        this.idMantenimiento = idMantenimiento
    }

    //Método toString
    override fun toString(): String {
        return "Mensaje(idMensaje=$idMensaje, mensaje=$mensaje, fecha=$fecha, kilometraje=$kilometraje, estado=$estado, idMantenimiento=$idMantenimiento)"
    }


}