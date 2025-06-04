package com.cesar.infomatica_gestion_pedidos.util

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.cesar.infomatica_gestion_pedidos.R
import java.util.jar.Manifest

object Notification {

    fun createNotification( applicationContext: Context){
        var idNotificationCreate = System.currentTimeMillis().toInt()
        var builder = NotificationCompat.Builder(applicationContext, "232323")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Envio de Pedidos pendientes")
            .setContentText("Se envio los pedidos pedientes al servidor")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        val notificationManager: NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(idNotificationCreate, builder.build());


    }
}