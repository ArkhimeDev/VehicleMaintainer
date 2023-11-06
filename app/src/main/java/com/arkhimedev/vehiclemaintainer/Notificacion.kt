package com.arkhimedev.vehiclemaintainer

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class Notificacion:BroadcastReceiver(){

    companion object{
        const val ID_NOTIFICACION = 1
    }

    override fun onReceive(context: Context, intent: Intent?) {
        crearNotificacion(context)
    }
    //Función que crea la notificación
    private fun crearNotificacion(context: Context){

        val intent=Intent(context, GarajeActivity::class.java ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            PendingIntent.FLAG_IMMUTABLE
        }
        else{
            0
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context,0,intent,flag)

        var notificacion = NotificationCompat.Builder(context, ProgramarMantenimientoActivity.ID_CANAL)
            .setSmallIcon(android.R.drawable.sym_def_app_icon)
            .setContentTitle("Mantenimiento programado")
            .setContentText("Nuevo mantenimiernto a realizar")
            .setStyle(NotificationCompat.BigTextStyle().bigText(
                "Tienes que realizar un mantenimiento de uno de tus vehiculo"
            ))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(ID_NOTIFICACION,notificacion)
    }
}