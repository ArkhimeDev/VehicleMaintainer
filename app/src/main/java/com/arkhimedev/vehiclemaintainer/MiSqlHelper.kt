package com.arkhimedev.vehiclemaintainer

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MiSqlHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null,DATABASE_VERSION) {

    companion object{
        const val DATABASE_NAME = "VehicleMaintainer.db"
        var DATABASE_VERSION = 1

        const val SQL_CREATE_VEHICULO = "CREATE TABLE vehiculo (" +
                "matricula TEXT PRIMARY KEY," +
                "numero_bastidor TEXT," +
                "marca TEXT," +
                "modelo TEXT, " +
                "fecha_matriculacion TEXT," +
                "kilometraje INTEGER," +
                "tipo_combustible TEXT)"

        const val SQL_CREATE_MANTENIMIENTO="CREATE TABLE mantenimiento (" +
                "id_mantenimiento INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "descripcion TEXT, " +
                "fecha TEXT," +
                "kilometraje INTEGER," +
                "importe REAL," +
                "tipo TEXT," +
                "matricula TEXT," +
                "FOREIGN KEY(matricula) REFERENCES vehiculo(matricula)" +
                "ON DELETE CASCADE)"

        const val SQL_CREATE_MENSAJE="CREATE TABLE mensaje (" +
                "id_mensaje INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "mensaje TEXT, " +
                "fecha TEXT," +
                "kilometraje INTEGER," +
                "estado INTEGER," +
                "id_mantenimiento INTEGER," +
                "FOREIGN KEY(id_mantenimiento) REFERENCES mantenimiento(id_mantenimiento)" +
                "ON DELETE CASCADE)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(SQL_CREATE_VEHICULO)
        db.execSQL(SQL_CREATE_MANTENIMIENTO)
        db.execSQL(SQL_CREATE_MENSAJE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onCreate(db)
    }

    //Función para insertar un vehiculo en la base de datos pasando como parámetro un objeto tipo Vehiculo
    fun insertarVehiculo(vehiculo:Vehiculo){

        val db = this.writableDatabase

        val valores = ContentValues().apply {
            put("matricula",vehiculo.matricula)
            put("numero_bastidor",vehiculo.numeroBastidor)
            put("marca",vehiculo.marca)
            put("modelo",vehiculo.modelo)
            put("fecha_matriculacion",vehiculo.fechaMatriculacion)
            put("kilometraje",vehiculo.kilometraje)
            put("tipo_combustible",vehiculo.tipoCombustible)
        }
        db.insert("vehiculo", null, valores)
        db.close()
    }

    //Función para insertar un mantenimiento en la base de datos pasando como parámetro
    //una matricula y un objeto tipo Mantenimiento
    fun insertarMantenimiento(matricula:String,mantenimiento: Mantenimiento):Long{
        val db = this.writableDatabase

        val valores = ContentValues().apply {
            put("descripcion", mantenimiento.descripcion)
            put("fecha", mantenimiento.fecha)
            put("kilometraje", mantenimiento.kilometraje)
            put("importe", mantenimiento.importe)
            put("tipo", mantenimiento.tipo)
            put("matricula", matricula)
        }
        val rowId = db.insert("mantenimiento", null, valores)
        db.close()
        return rowId
    }

    //Función para insertar un mensaje en la base de datos pasando como parámetro
    //un idMantenimiento y un objeto tipo Mensaje
    fun insertarMensaje (mensaje:Mensaje){
        val db = this.writableDatabase

        val valores = ContentValues().apply {
            put("mensaje", mensaje.mensaje)
            put("fecha", mensaje.fecha)
            put("kilometraje", mensaje.kilometraje)
            put("estado",mensaje.estado)
            put("id_mantenimiento",mensaje.idMantenimiento)
        }
        db.insert("mensaje", null,valores)
        db.close()
    }

    //Función para borrar a un vehiculo pasando una matrícula como parámetro
    fun borrarVehiculo (matricula: String){
        val db = this.writableDatabase
        db.execSQL("PRAGMA foreign_keys=ON")
        db.delete("vehiculo", "matricula='$matricula'",null)
        db.close()
    }

    //Función para borrar a un mantenimiento pasando por parámetro un idMantenimiento
    fun borrarMantenimiento (idMantenimiento: Int){
        val db = this.writableDatabase
        db.execSQL("PRAGMA foreign_keys=ON")
        db.delete("mantenimiento", "id_mantenimiento='$idMantenimiento'",null)
        db.close()
    }

    //Función para actualizar el kilometraje pasando la matrícula y el nuevo kilometraje por parámetro
    fun actualizarKilometraje (matricula:String, kilometraje : Int){
        val db = this.writableDatabase
        val valores = ContentValues().apply { put("kilometraje", kilometraje) }
        db.update("vehiculo",valores,"matricula='$matricula'",null)
        db.close()
    }

    //Función que devuelve un objeto de tipo Vehiculo pasando por parámetro una matrícula
    fun seleccionarVehiculo (matricula:String):Vehiculo?{
        val db = this.readableDatabase
        val datos = db.rawQuery("SELECT * FROM vehiculo WHERE matricula='$matricula' ",null)

        if (datos.moveToFirst()){
            val vehiculo = Vehiculo(
                matricula = datos.getString(0),
                numeroBastidor = datos.getString(1),
                marca = datos.getString(2),
                modelo = datos.getString(3),
                fechaMatriculacion = datos.getString(4),
                kilometraje = datos.getInt(5),
                tipoCombustible = datos.getString(6)
            )
            datos.close()
            db.close()
            return vehiculo
        }
        else {
            datos.close()
            db.close()
            return null
        }
    }
    //Función que devuelve un objeto de tipo Vehiculo con matricula, marca y modelo pasando
    //por parámetro un idMantenimiento
    fun seleccionarVehiculoIdMantenimiento (idMantenimiento: Int):Vehiculo?{
        val db = this.readableDatabase
        val datos = db.rawQuery("SELECT v.matricula,v.marca,v.modelo " +
                "FROM vehiculo v, mantenimiento m " +
                "WHERE v.matricula = m.matricula " +
                "AND m.id_mantenimiento = '$idMantenimiento'",
            null
        )
        if (datos.moveToFirst()){
            val vehiculo = Vehiculo(
                matricula = datos.getString(0),
                marca = datos.getString(1),
                modelo = datos.getString(2)
            )
            datos.close()
            db.close()
            return vehiculo
        }
        else {
            datos.close()
            db.close()
            return null
        }
    }

    //Función que devuelve una lista de objetos de tipo Vehiculo
    fun seleccionarListaVehiculos ():List<Vehiculo>?{
        val db = this.readableDatabase
        val datos = db.rawQuery("SELECT * FROM vehiculo",null)
        val listaVehiculos = mutableListOf<Vehiculo>()

        if (datos.moveToFirst()) {
            var vehiculo = Vehiculo(
                matricula = datos.getString(0),
                numeroBastidor = datos.getString(1),
                marca = datos.getString(2),
                modelo = datos.getString(3),
                fechaMatriculacion = datos.getString(4),
                kilometraje = datos.getInt(5),
                tipoCombustible = datos.getString(6)
            )
            listaVehiculos.add(vehiculo)

            while (datos.moveToNext()){
                vehiculo = Vehiculo(
                    matricula = datos.getString(0),
                    numeroBastidor = datos.getString(1),
                    marca = datos.getString(2),
                    modelo = datos.getString(3),
                    fechaMatriculacion = datos.getString(4),
                    kilometraje = datos.getInt(5),
                    tipoCombustible = datos.getString(6)
                )
                listaVehiculos.add(vehiculo)
            }
            datos.close()
            db.close()
            return listaVehiculos
        }
        else{
            datos.close()
            db.close()
            return null
        }
    }

    //Función que devuelve un objeto de tipo Mantenimiento pasando por parametro el idMantenimiento
    fun seleccionarMantenimiento (idMantenimiento:Int):Mantenimiento?{
        val db = this.readableDatabase
        val datos = db.rawQuery("SELECT * FROM mantenimiento WHERE  id_mantenimiento= '$idMantenimiento'",null)

        if (datos.moveToFirst()) {
            val mantenimiento = Mantenimiento(
                idMantenimiento = datos.getInt(0),
                descripcion = datos.getString(1),
                fecha = datos.getString(2),
                kilometraje = datos.getInt(3),
                importe = datos.getFloat(4),
                tipo = datos.getString(5),
                matricula = datos.getString(6)
            )
            datos.close()
            db.close()
            return mantenimiento
        }
        else{datos.close()
            db.close()
            return null
        }
    }

    //Función que devuelve una lista de objetos de tipo Mantenimiento pasando por parametro el tipo
    fun seleccionarListaMantenimientoTipo (tipoMantenimiento:String):MutableList<Mantenimiento>?{
        val db = this.readableDatabase
        val datos = db.rawQuery("SELECT * FROM mantenimiento WHERE  tipo='$tipoMantenimiento'",null)
        val listaMantenimiento = mutableListOf<Mantenimiento>()

        if (datos.moveToFirst()) {
            var mantenimiento = Mantenimiento(
                idMantenimiento = datos.getInt(0),
                descripcion = datos.getString(1),
                fecha = datos.getString(2),
                kilometraje = datos.getInt(3),
                importe = datos.getFloat(4),
                tipo = datos.getString(5),
                matricula = datos.getString(6)
            )
            listaMantenimiento.add(mantenimiento)

            while (datos.moveToNext()){
                mantenimiento = Mantenimiento(
                    idMantenimiento = datos.getInt(0),
                    descripcion = datos.getString(1),
                    fecha = datos.getString(2),
                    kilometraje = datos.getInt(3),
                    importe = datos.getFloat(4),
                    tipo = datos.getString(5),
                    matricula = datos.getString(6)
                )
                listaMantenimiento.add(mantenimiento)
            }
            datos.close()
            db.close()
            return listaMantenimiento
        }
        else{
            datos.close()
            db.close()
            return null
        }
    }

    //Función que devuelve una lista de objetos de tipo Mantenimiento pasando por parametro la matricula y el tipo
    fun seleccionarListaMantenimientoVehiculo (matricula:String,tipoMantenimiento:String):MutableList<Mantenimiento>?{
        val db = this.readableDatabase
        val datos = db.rawQuery("SELECT * FROM mantenimiento WHERE tipo='$tipoMantenimiento'AND matricula = '$matricula'",null)
        val listaMantenimiento = mutableListOf<Mantenimiento>()

        if (datos.moveToFirst()) {
            var mantenimiento = Mantenimiento(
                idMantenimiento = datos.getInt(0),
                descripcion = datos.getString(1),
                fecha = datos.getString(2),
                kilometraje = datos.getInt(3),
                importe = datos.getFloat(4),
                tipo = datos.getString(5),
                matricula = datos.getString(6)
            )
            listaMantenimiento.add(mantenimiento)

            while (datos.moveToNext()){
                mantenimiento = Mantenimiento(
                    idMantenimiento = datos.getInt(0),
                    descripcion = datos.getString(1),
                    fecha = datos.getString(2),
                    kilometraje = datos.getInt(3),
                    importe = datos.getFloat(4),
                    tipo = datos.getString(5),
                    matricula = datos.getString(6)
                )
                listaMantenimiento.add(mantenimiento)
            }
            datos.close()
            db.close()
            return listaMantenimiento
        }
        else{
            datos.close()
            db.close()
            return null
        }
    }

    //Función que devuelve un Mensaje parando por parametro un idMantenimiento
    fun seleccionarMensaje (idMantenimiento: Int):Mensaje?{
        val db = this.readableDatabase
        val datos = db.rawQuery("SELECT * FROM mensaje WHERE id_mantenimiento = '$idMantenimiento'", null)
        if (datos.moveToFirst()){
            val mensaje = Mensaje(
                idMensaje = datos.getInt(0),
                mensaje = datos.getString(1),
                fecha = datos.getString(2),
                kilometraje = datos.getInt(3),
                estado = datos.getInt(4),
                idMantenimiento = datos.getInt(5)
            )
            datos.close()
            db.close()
            return mensaje
        }else{
            datos.close()
            db.close()
            return null
        }
    }

    //Función que devuelve una lista de objetos de tipo Mensaje
    fun seleccionarListaMensaje ():MutableList<Mensaje>?{
        val db = this.readableDatabase
        val datos = db.rawQuery("SELECT * FROM mensaje",null)
        val listaMensaje = mutableListOf<Mensaje>()

        if (datos.moveToFirst()) {
            var mensaje = Mensaje(
                idMensaje = datos.getInt(0),
                mensaje = datos.getString(1),
                fecha = datos.getString(2),
                kilometraje = datos.getInt(3),
                estado = datos.getInt(4),
                idMantenimiento = datos.getInt(5)
            )
            listaMensaje.add(mensaje)

            while (datos.moveToNext()){
                mensaje = Mensaje(
                    idMensaje = datos.getInt(0),
                    mensaje = datos.getString(1),
                    fecha = datos.getString(2),
                    kilometraje = datos.getInt(3),
                    estado = datos.getInt(4),
                    idMantenimiento = datos.getInt(5)
                )
                listaMensaje.add(mensaje)
            }
            datos.close()
            db.close()
            return listaMensaje
        }
        else{
            datos.close()
            db.close()
            return null
        }
    }

    //Función para actualizar el estado del Mensaje pasando el id_mensaje por parámetro
    fun actualizarEstadoMensaje (id_mensaje:Int, estado : Int){
        val db = this.writableDatabase
        val valores = ContentValues().apply { put("estado", estado) }
        db.update("mensaje",valores,"id_mensaje='$id_mensaje'",null)
        db.close()
    }

    //Función para borrar un mensaje pasando un idMensaje por parámetro
    fun borrarMensaje(idMensaje: Int){
        val db = this.writableDatabase
        db.execSQL("PRAGMA foreign_keys=ON")
        db.delete("mensaje", "id_mensaje='$idMensaje'",null)
        db.close()
    }
    //Función para borrar un mensaje pasando un idMantenimiento por parámetro
    fun borrarMensajeIdMantenimiento(idMantenimiento: Int){
        val db = this.writableDatabase
        db.execSQL("PRAGMA foreign_keys=ON")
        db.delete("mensaje", "id_mantenimiento='$idMantenimiento'",null)
        db.close()
    }
    fun borrarDatosTablasBD(){
        val db = this.writableDatabase
        db.execSQL("PRAGMA foreign_keys=ON")
        db.execSQL("DELETE FROM mensaje")
        db.execSQL("DELETE FROM mantenimiento")
        db.execSQL("DELETE FROM vehiculo")
        db.close()
    }
}