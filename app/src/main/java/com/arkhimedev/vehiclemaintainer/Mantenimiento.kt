package com.arkhimedev.vehiclemaintainer

class Mantenimiento {
    companion object{
        const val  MANTENIMIENTO_PROGRAMADO = "programado"
        const val  MANTENIMIENTO_REALIZADO = "realizado"
    }

    var idMantenimiento:Int?=null
    var descripcion:String?=null
    var fecha:String?=null
    var kilometraje:Int?=null
    var importe:Float?=null
    var tipo:String?=null
    var matricula:String?=null

    constructor(
        descripcion: String?,
        fecha: String?,
        kilometraje: Int?,
        importe: Float?,
        tipo: String?,
        matricula: String?
    ) {
        this.descripcion = descripcion
        this.fecha = fecha
        this.kilometraje = kilometraje
        this.importe = importe
        this.tipo = tipo
        this.matricula = matricula
    }

    constructor(
        idMantenimiento: Int?,
        descripcion: String?,
        fecha: String?,
        kilometraje: Int?,
        importe: Float?,
        tipo: String?,
        matricula: String?
    ) {
        this.idMantenimiento = idMantenimiento
        this.descripcion = descripcion
        this.fecha = fecha
        this.kilometraje = kilometraje
        this.importe = importe
        this.tipo = tipo
        this.matricula = matricula
    }

}