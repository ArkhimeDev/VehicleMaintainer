package com.arkhimedev.vehiclemaintainer


class Mensaje {
    companion object{
        const val NO_LEIDO = 0
        const val LEIDO = 1
    }

    var idMensaje:Int?=null
    var mensaje:String?=null
    var fecha:String?=null
    var kilometraje:Int?=null
    var estado:Int?=null
    var idMantenimiento:Int?=null



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

}