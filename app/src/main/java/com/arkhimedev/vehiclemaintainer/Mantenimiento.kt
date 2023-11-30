package com.arkhimedev.vehiclemaintainer

class Mantenimiento {
    //Constantes para utilizar como tipo
    companion object{
        const val  MANTENIMIENTO_PROGRAMADO = "programado"
        const val  MANTENIMIENTO_REALIZADO = "realizado"
    }

    //Propiedades, getters y setters
    var idMantenimiento:Int?=null
        get() = field
        set(value){field=value}
    var descripcion:String?=null
        get() = field
        set(value){field=value}
    var fecha:String?=null
        get() = field
        set(value){field=value}
    var kilometraje:Int?=null
        get() = field
        set(value){field=value}
    var importe:Float?=null
        get() = field
        set(value){field=value}
    var tipo:String?=null
        get() = field
        set(value){field=value}
    var matricula:String?=null
        get() = field
        set(value){field=value}

    //Constructores
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

    //Método toString
    override fun toString(): String {
        return "Mantenimiento(idMantenimiento=$idMantenimiento, descripcion=$descripcion, fecha=$fecha, kilometraje=$kilometraje, importe=$importe, tipo=$tipo, matricula=$matricula)"
    }


}